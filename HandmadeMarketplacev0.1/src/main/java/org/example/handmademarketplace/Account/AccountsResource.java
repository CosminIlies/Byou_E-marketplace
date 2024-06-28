package org.example.handmademarketplace.Account;

import org.example.handmademarketplace.Product.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@RestController
public class AccountsResource
{
    private AccountsService accountsService;

    @Autowired
    public AccountsResource(AccountsService accountsService)
    {
        this.accountsService = accountsService;
    }

    @CrossOrigin(origins = "http://localhost:5173")
    @GetMapping("/accounts")
    public List<Account> retrieveAllAccounts()
    {
        return accountsService.findAll();
    }

    @CrossOrigin(origins = "http://localhost:5173")
    @GetMapping("/accounts/{account_id}")
    public Account retrieveAccountById(@PathVariable Long account_id)
    {
        return accountsService.findById(account_id);
    }

    @CrossOrigin(origins = "http://localhost:5173")
    @PutMapping("/accounts/{account_id}")
    public ResponseEntity<String> updateAccount(@PathVariable Long account_id, @RequestBody Account account_updated, @AuthenticationPrincipal Account account)
    {


        if (!Objects.equals(account.getId(), account_id)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You are not authorized to create a product for this account");
        }

        if(!accountsService.update(account_id, account_updated))
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account not found");
        }

        return ResponseEntity.status(HttpStatus.OK).body("Account updated successfully");
    }


    @CrossOrigin(origins = "http://localhost:5173")
    @PostMapping("/register")
    public ResponseEntity<String> createAccount(@RequestParam("email") String email, @RequestParam("username") String username, @RequestParam("phone") String phone, @RequestParam("password") String password, @RequestParam("r_password") String r_password)
    {

        if(phone.length() != 10 || phone.contains("[a-zA-Z]"))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Phone number is invalid!");


        if(!(email.endsWith("@gmail.com") || email.endsWith("@yahoo.com") || email.endsWith("@hotmail.com") || email.endsWith("@outlook.com")))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Email is invalid!");

        if(username.length() < 4)
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Username is too small!");

        if(accountsService.findByUsername(username) != null)
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("An account with this username already exists!");

        if(password.length() < 4)
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Passwords is too small!");

        if(username.length() > 20)
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Username is too large!");

        if(password.length() > 20)
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Passwords is too large!");

        if(!password.equals(r_password))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Passwords don't match!");

        Account account = new Account(username, password, email, phone, Role.USER);
        accountsService.createAccount(account);
        return ResponseEntity.status(HttpStatus.CREATED).body("Account created successfully");
    }

    @CrossOrigin(origins = "http://localhost:5173")
    @DeleteMapping("/accounts/{account_id}")
    public ResponseEntity<String> deleteAccount(@PathVariable Long account_id,
                                                @AuthenticationPrincipal Account account)
    {
        if (!Objects.equals(account.getId(), account_id)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You are not authorized to create a product for this account");
        }

        accountsService.deleteAccount(account_id);
        return ResponseEntity.status(HttpStatus.OK).body("Account deleted successfully");
    }


    @CrossOrigin(origins = "http://localhost:5173")
    @GetMapping("/accounts/{account_id}/products")
    public ResponseEntity<List<Product>> retrieveAllProductsByAccount(@PathVariable Long account_id)
    {
        Account account = accountsService.findById(account_id);

        if(account == null)
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        return ResponseEntity.status(HttpStatus.OK).body(account.getProducts());
    }

    @CrossOrigin(origins = "http://localhost:5173")
    @PostMapping("/accounts/{account_id}/products")
    public ResponseEntity<String> createNewProduct(@PathVariable Long account_id,
                                                               @RequestParam("name") String name,
                                                               @RequestParam("description") String description,
                                                               @RequestParam("price") double price,
                                                               @RequestParam("amount") int amount,
                                                               @RequestParam("photo") MultipartFile photo,
                                                               @AuthenticationPrincipal Account account) throws IOException {

        if (!Objects.equals(account.getId(), account_id)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You are not authorized to create a product for this account");
        }

        Product product = new Product(name, description, price, amount);
        product.setPhoto(photo.getBytes());
        if (!accountsService.addNewPost(account_id, product)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account doesn't exist");
        }


        return ResponseEntity.status(HttpStatus.CREATED).body("Product created successfully");
    }


    @CrossOrigin(origins = "http://localhost:5173")
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginBody loginBody)
    {
        String username = loginBody.getUsername();
        String password = loginBody.getPassword();

        String jwt = accountsService.loginUser(username, password);

        if(jwt == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password!");
        }else{
            LoginResponse response = new LoginResponse(jwt);

            return ResponseEntity.ok(response);
        }

    }

    @CrossOrigin(origins = "http://localhost:5173")
    @GetMapping("/me")
    public Account getLoggedInUserProfile(@AuthenticationPrincipal Account account)
    {
        return account;
    }


}
