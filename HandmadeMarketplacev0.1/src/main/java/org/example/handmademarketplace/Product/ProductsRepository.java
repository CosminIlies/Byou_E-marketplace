package org.example.handmademarketplace.Product;

import org.example.handmademarketplace.Account.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductsRepository extends JpaRepository<Product, Long> {
    Product findByName(String name);
    Product findByDescription(String description);
    Product findByPrice(Double price);
    Product findByAmount(Integer amount);
    List<Product> findBySeller(Account seller);
}
