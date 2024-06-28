package org.example.handmademarketplace.Product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductsService {

    private ProductsRepository productsRepository;

    @Autowired
    public ProductsService(ProductsRepository productsRepository) {
        this.productsRepository = productsRepository;
    }

    public List<Product> findAll(){
        return productsRepository.findAll();
    }

    public Product findByName(String name) {
        return productsRepository.findByName(name);
    }

    public Product findById(Long id) {
        return productsRepository.findById(id).orElse(null);
    }

    public Product create(Product product){
        return productsRepository.save(product);
    }

    public boolean deleteById(Long id) {
        if(productsRepository.existsById(id)) {
            productsRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public boolean update(Long id,Product product){
        Product productToUpdate = findById(id);
        if(productToUpdate == null)
        {
            return false;
        }

        productToUpdate.setName(product.getName());
        productToUpdate.setDescription(product.getDescription());
        productToUpdate.setPrice(product.getPrice());
        productToUpdate.setAmount(product.getAmount());
        productsRepository.save(productToUpdate);

        return true;
    }



}
