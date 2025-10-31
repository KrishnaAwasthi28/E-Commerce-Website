import { useState } from "react";
import axios from "axios";
import { Link, useNavigate } from "react-router-dom";
import { ToastContainer, toast } from "react-toastify";

export default function Login() {
  const [formData, setFormData] = useState({
    mobileNumber: "",
    password: "",
  });
  const navigate = useNavigate();

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      console.log("Sending payload:", formData);
      const res = await axios.post(
        "https://e-commerce-website-1-xzpr.onrender.com/api/auth/login",
        formData,
        {
          headers: { "Content-Type": "application/json" },
          withCredentials: true,
        }
      );
      //   alert("Login successful!");
      const userData = {
        ...res.data,
        rawPassword: formData.password,
      };

      localStorage.setItem("user", JSON.stringify(userData));
      localStorage.setItem("auth", btoa(`${res.data.mobileNumber}:${formData.password}`));

      console.log("Auth stored:", localStorage.getItem("auth"));
      console.log(userData);
      toast.success("Login successful!");
      navigate("/");
    } catch (err) {
      console.error(err);
      toast.error("Invalid credentials");
    }
  };
  
  return (
    <div className="login-container">
      <form className="login-form" onSubmit={handleSubmit}>
        <h2>Login into your Account</h2>
        <input
          name="mobileNumber"
          placeholder="Mobile Number"
          onChange={handleChange}
          required
        />
        <input
          name="password"
          type="password"
          placeholder="Password"
          onChange={handleChange}
          required
        />
        <button type="submit">Login</button>
        <div className="newUser">
          <span>New User ? </span>
          <Link to={"/register"}>Register</Link>
        </div>
      </form>
      {/* Toast container */}
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
}
