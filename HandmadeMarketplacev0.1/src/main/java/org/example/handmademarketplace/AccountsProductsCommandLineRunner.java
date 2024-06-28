package org.example.handmademarketplace;

import org.example.handmademarketplace.Account.Account;
import org.example.handmademarketplace.Account.AccountsService;
import org.example.handmademarketplace.Account.Role;
import org.example.handmademarketplace.Product.Product;
import org.example.handmademarketplace.Product.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class AccountsProductsCommandLineRunner implements CommandLineRunner {

    private AccountsService accountsService;
    private ProductsService productsService;

    @Autowired
    public AccountsProductsCommandLineRunner(AccountsService accountsService, ProductsService productsService) {
        this.accountsService = accountsService;
        this.productsService = productsService;
    }


    @Override
    public void run(String... args) throws Exception {
    }
}
