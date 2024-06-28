package org.example.handmademarketplace.Product;

import com.sendgrid.helpers.mail.objects.Personalization;
import org.example.handmademarketplace.Account.Account;
import org.example.handmademarketplace.Account.AccountsService;
import org.example.handmademarketplace.Email.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductsResource {

//    private ProductsRepository productsRepository;
    private ProductsService productsService;
    private AccountsService accountsService;
    private EmailService emailService;

    @Autowired
    public ProductsResource(ProductsService productsService, AccountsService accountsService, EmailService emailService) {
        this.productsService = productsService;
        this.accountsService = accountsService;
        this.emailService = emailService;
    }

    @CrossOrigin(origins = "http://localhost:5173")
    @GetMapping("/products")
    public List<Product> retrieveAllProducts() {
        return productsService.findAll();
    }

    @CrossOrigin(origins = "http://localhost:5173")
    @GetMapping("/products/{product_id}")
    public Product retrieveProductById(@PathVariable Long product_id) {
        return productsService.findById(product_id);
    }

    @CrossOrigin(origins = "http://localhost:5173")
    @GetMapping("/products/{product_id}/seller_id")
    public Long retrieveProductSellerId(@PathVariable Long product_id) {
        return productsService.findById(product_id).getSeller().getId();
    }

    @CrossOrigin(origins = "http://localhost:5173")
    @PostMapping("/products/{product_id}/buy")
    public ResponseEntity<String> buyProduct(@PathVariable Long product_id, @RequestBody Long buyer_id) {

        Product prod = productsService.findById(product_id);
        Account buyer = accountsService.findById(buyer_id);
        System.out.println(prod.getSeller().getEmail());
        System.out.println(buyer.getUsername() + " wants to buy your product. Please contact him at his email address:" + buyer.getEmail() + " or via his phone: " + buyer.getPhone());

        Personalization personalization = new Personalization();
        personalization.addDynamicTemplateData("buyerName", buyer.getUsername());
        personalization.addDynamicTemplateData("buyerEmail", buyer.getEmail());
        personalization.addDynamicTemplateData("buyerPhone", buyer.getPhone());
        personalization.addDynamicTemplateData("productName", prod.getName());


        emailService.sendDynamicEmail(prod.getSeller().getEmail(), "Your product, '" + prod.getName() + "', is very popular :)", "d-361cfd765baf407cbf5e66d65fe4f379", personalization);

        return new ResponseEntity<String>(HttpStatus.OK);
    }


    @CrossOrigin(origins = "http://localhost:5173")
    @PostMapping("/products")
    public ResponseEntity<String> createProduct(@RequestBody Product product) {
        productsService.create(product);
        return ResponseEntity.status(HttpStatus.CREATED).body("Product created successfully");
    }

    @CrossOrigin(origins = "http://localhost:5173")
    @PutMapping("/products/{product_id}")
    public ResponseEntity<String> updateProduct(@PathVariable Long product_id, @RequestBody Product product) {

        if(!productsService.update(product_id, product))
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account not found");
        }

        return ResponseEntity.status(HttpStatus.OK).body("Product was updated successfully");

    }

    @CrossOrigin(origins = "http://localhost:5173")
    @DeleteMapping("/products/{product_id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long product_id) {
        if(!productsService.deleteById(product_id)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product was not found");

        }
        return ResponseEntity.status(HttpStatus.OK).body("Product was deleted successfully");

    }
}
