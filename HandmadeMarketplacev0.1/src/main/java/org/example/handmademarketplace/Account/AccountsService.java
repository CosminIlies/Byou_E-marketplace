package org.example.handmademarketplace.Account;

import org.example.handmademarketplace.Product.Product;
import org.example.handmademarketplace.Product.ProductsService;
import org.example.handmademarketplace.security.EncryptionService;
import org.example.handmademarketplace.security.JWTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountsService {
    private final AccountsRepository accountsRepository;
    private EncryptionService encryptionService;
    private ProductsService productsService;
    private JWTService jwtService;

    @Autowired
    public AccountsService(AccountsRepository accountRepository, ProductsService productsService,
                           EncryptionService encryptionService, JWTService jwtService) {
        this.accountsRepository = accountRepository;
        this.productsService = productsService;
        this.encryptionService = encryptionService;
        this.jwtService = jwtService;
    }

    public Account createAccount(Account account) {

        account.setPassword(encryptionService.encryptPassword(account.getPassword()));
        System.out.println(account.getPassword());
        return accountsRepository.save(account);
    }
    public List<Account> findAll(){
        return accountsRepository.findAll();
    }
    public Account findById(Long id) {
        return accountsRepository.findById(id).orElse(null);
    }
    public Account findByUsername(String username) {
        return accountsRepository.findByUsername(username);
    }
    public void deleteAccount(Long id) {
        accountsRepository.deleteById(id);
    }
    public boolean update(Long account_id, Account account){

        Account accountToUpdate = findById(account_id);
        if(accountToUpdate == null)
        {
            return false;
        }

        accountToUpdate.setUsername(account.getUsername());
        accountToUpdate.setPassword(account.getPassword());
        accountToUpdate.setEmail(account.getEmail());
        accountToUpdate.setPhone(account.getPhone());
        accountToUpdate.setRole(account.getRole());

        accountsRepository.save(accountToUpdate);
        return true;
    }
    public boolean addNewPost(Long id, Product product){

        Account acc = findById(id);

        if(acc == null)
            return false;
        productsService.create(product);

        acc.addProduct(product);
        product.setSeller(acc);

        accountsRepository.save(acc);
        productsService.update(product.getId(), product);
        return true;
    }

    public String loginUser(String username, String password){
        Account account = findByUsername(username);
        if(account != null){
            if(encryptionService.verifyPassword(password, account.getPassword()))
                return jwtService.generateJWT(account);
        }
        return null;
    }


    public Account getLoggedInUser(){
        return findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
    }

    public boolean isAuthorized(Long id){
        Account loggedIn = getLoggedInUser();
        return loggedIn.getId().compareTo(id) == 0;
    }


}
