import React, { useState, useEffect } from "react";
import axios from "axios";
import CheckoutPopup from "./CheckoutPopup";
import { Button } from "react-bootstrap";
import { ToastContainer, toast } from "react-toastify";

const Cart = () => {
  const [cartId, setCartId] = useState(null);
  const [cartItems, setCartItems] = useState([]);
  const [totalPrice, setTotalPrice] = useState(0);
  const [showModal, setShowModal] = useState(false);

  // ✅ Get userId from localStorage
  const user = JSON.parse(localStorage.getItem("user"));
  const userId = user ? user.user_id : null;
  const auth = localStorage.getItem("auth");
  console.log("Auth header", auth);
  console.log(user);
  // ✅ Get cartId for user
  useEffect(() => {
    const fetchCartId = async () => {
      console.log("user id", userId);
      if (userId) {
        try {
          const res = await axios.get(
            `http://localhost:8080/api/cart/${userId}`,
            {
              headers: { Authorization: `Basic ${auth}` },
            }
          );
          console.log("cart details", res.data);
          setCartId(res.data.cartId); // store cartId
        } catch (error) {
          console.error("Error fetching cart:", error);
        }
      }
    };
    fetchCartId();
  }, [userId]);

  // ✅ Fetch cart items using cartId
  useEffect(() => {
    const fetchCartItems = async () => {
      console.log("cart id", cartId);
      if (cartId) {
        try {
          const res = await axios.get(
            `http://localhost:8080/api/cart/${cartId}/items`,
            {
              headers: { Authorization: `Basic ${auth}` },
            }
          );
          const itemsWithProduct = await Promise.all(
            res.data.map(async (item) => {
              const prodRes = await axios.get(
                `http://localhost:8080/api/product/${item.productId}`,
                { headers: { Authorization: `Basic ${auth}` } }
              );
              return { ...item, product: prodRes.data };
            })
          );
          console.log("product object ", itemsWithProduct);
          setCartItems(itemsWithProduct);
        } catch (error) {
          console.error("Error fetching cart items:", error);
        }
      }
    };
    fetchCartItems();
  }, [cartId]);

  // ✅ Calculate total
  useEffect(() => {
    const total = cartItems.reduce(
      (acc, item) => acc + item.product.price * item.quantity,
      0
    );
    setTotalPrice(total);
  }, [cartItems]);

  // ✅ Increase / Decrease quantity
  const handleIncreaseQuantity = async (cartItemId, currentQty, stockQty) => {
    if (currentQty < stockQty) {
      try {
        await axios.put(
          `http://localhost:8080/api/cart/item/${cartItemId}?quantity=${
            currentQty + 1
          }`,
          {},
          {
            headers: { Authorization: `Basic ${auth}` },
          }
        );
        setCartItems(
          cartItems.map((item) =>
            item.cartItemId === cartItemId
              ? { ...item, quantity: item.quantity + 1 }
              : item
          )
        );
      } catch (error) {
        console.error("Error increasing quantity:", error);
      }
    } else {
      alert("Cannot add more than available stock");
    }
  };

  const handleDecreaseQuantity = async (cartItemId, currentQty) => {
    if (currentQty > 1) {
      try {
        await axios.put(
          `http://localhost:8080/api/cart/item/${cartItemId}?quantity=${
            currentQty - 1
          }`,
          {},
          {
            headers: { Authorization: `Basic ${auth}` },
          }
        );
        setCartItems(
          cartItems.map((item) =>
            item.cartItemId === cartItemId
              ? { ...item, quantity: item.quantity - 1 }
              : item
          )
        );
      } catch (error) {
        console.error("Error decreasing quantity:", error);
      }
    }
  };

  // ✅ Remove item
  const handleRemoveFromCart = async (cartItemId) => {
    try {
      console.log(cartItemId);
      await axios.delete(`http://localhost:8080/api/cart/item/${cartItemId}`, {
        headers: { Authorization: `Basic ${auth}` },
      });
      setCartItems(cartItems.filter((item) => item.cartItemId !== cartItemId));
    } catch (error) {
      console.error("Error removing item:", error);
    }
  };

  // ✅ Clear cart
  const handleClearCart = async () => {
    try {
      await axios.delete(`http://localhost:8080/api/cart/${cartId}/clear`, {
        headers: { Authorization: `Basic ${auth}` },
      });
      setCartItems([]);
    } catch (error) {
      console.error("Error clearing cart:", error);
    }
  };

  // ✅ Checkout
  const handleCheckout = async () => {
    try {
      await handleClearCart();
      toast.success("Order Placed Successfully")
      setShowModal(false);
    } catch (error) {
      console.error("Error clearing cart:", error);
    }
  };
  
  return (
    <div className="cart-container">
      <div className="shopping-cart">
        <div className="title">Shopping Bag</div>
        {cartItems.length === 0 ? (
          <div className="empty" style={{ textAlign: "left", padding: "2rem" }}>
            <h4>Your cart is empty</h4>
          </div>
        ) : (
          <>
            {cartItems.map((item) => (
              <li key={item.cartItemId} className="cart-item">
                <div
                  className="item"
                  style={{ display: "flex", alignContent: "center" }}
                  key={item.cartId}
                >
                  <div>
                    <img
                      src={`http://localhost:8080/api/product/${item.product.id}/image`}
                      alt={item.product.name}
                      className="cart-item-image"
                    />
                  </div>
                  <div className="description">
                    <span>{item.product.brand}</span>
                    <span>{item.product.name}</span>
                  </div>

                  <div className="quantity">
                    <button
                      className="plus-btn"
                      type="button"
                      name="button"
                      onClick={() =>
                        handleIncreaseQuantity(
                          item.cartItemId,
                          item.quantity,
                          item.product.stockQuantity
                        )
                      }
                    >
                      <i className="bi bi-plus-square-fill"></i>
                    </button>
                    <input
                      type="button"
                      name="name"
                      value={item.quantity}
                      readOnly
                    />
                    <button
                      className="minus-btn"
                      type="button"
                      name="button"
                      onClick={() =>
                        handleDecreaseQuantity(item.cartItemId, item.quantity)
                      }
                    >
                      <i className="bi bi-dash-square-fill"></i>
                    </button>
                  </div>

                  <div className="total-price " style={{ textAlign: "center" }}>
                    ${item.product.price * item.quantity}
                  </div>
                  <button
                    className="remove-btn"
                    onClick={() => handleRemoveFromCart(item.cartItemId)}
                  >
                    <i className="bi bi-trash3-fill"></i>
                  </button>
                </div>
              </li>
            ))}
            <div className="total">Total: ${totalPrice}</div>
            <Button
              className="btn btn-primary"
              style={{ width: "100%" }}
              onClick={() => setShowModal(true)}
            >
              Checkout
            </Button>
          </>
        )}
      </div>
      <CheckoutPopup
        show={showModal}
        handleClose={() => setShowModal(false)}
        cartItems={cartItems}
        totalPrice={totalPrice}
        handleCheckout={handleCheckout}
      />
      <ToastContainer
              position="top-right"
              autoClose={3000}
              hideProgressBar={false}
              newestOnTop={false}
              closeOnClick
              pauseOnHover
              draggable
            />
    </div>
  );
};

export default Cart;
