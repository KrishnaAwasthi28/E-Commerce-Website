package com.project.SpringEcom.controller;
import javax.xml.bind.DatatypeConverter;
import com.project.SpringEcom.model.Product;
import com.project.SpringEcom.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "https://e-commerce-website-final.onrender.com")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getProducts(){
        return new ResponseEntity<>(productService.getAllProducts(), HttpStatus.OK);
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable("id") int id){
        Product product=productService.getProductById(id);
        if(product.getId()>0){
            return new ResponseEntity<>(product,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("product/{productId}/image")
    public ResponseEntity<byte[]> getImageByProductId(@PathVariable int productId){
        Product product = productService.getProductById(productId);
        if (product == null || product.getImageData() == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        byte[] actualBytes;

        // detect if it's hex text instead of raw bytes
        if (product.getImageData().length > 0 && product.getImageData()[0] == '0' && product.getImageData()[1] == 'x') {
            String hex = new String(product.getImageData());
            hex = hex.replace("0x", "").replaceAll("[^0-9A-Fa-f]", "");
            actualBytes = DatatypeConverter.parseHexBinary(hex);
        } else {
            actualBytes = product.getImageData();
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(
                product.getImageType() != null
                        ? MediaType.parseMediaType(product.getImageType())
                        : MediaType.IMAGE_JPEG
        );
        headers.setContentLength(actualBytes.length);

        return new ResponseEntity<>(actualBytes, headers, HttpStatus.OK);
    }

    @PostMapping("/product")
    public ResponseEntity<?> addProduct(@RequestPart Product product, @RequestPart MultipartFile imageFile){
        Product savedProduct= null;
        try {
            savedProduct = productService.addOrUpdateProduct(product,imageFile);
            return new ResponseEntity<>(savedProduct,HttpStatus.CREATED);
        } catch (IOException e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PutMapping("/product/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable int id,@RequestPart Product product,@RequestPart MultipartFile imageFile){
        Product updatedProduct=null;
        try{
            updatedProduct=productService.addOrUpdateProduct(product,imageFile);
            return new ResponseEntity<>("Updated",HttpStatus.OK);
        }catch(IOException e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable int id){
        Product product=productService.getProductById(id);
        if(product!=null){
            productService.deleteProduct(id);
            return new ResponseEntity<>("Deleted Successfully",HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Deleted Successfully",HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/products/search")
    public ResponseEntity<List<Product>> searchProduct(@RequestParam String keyword){
        List<Product> products=productService.searchProduct(keyword);
        System.out.println("search with keyword "+ keyword);
        return new ResponseEntity<>(products,HttpStatus.OK);
    }

}
