package org.example.handmademarketplace.Account;


//TODO: use this instead of Account in the AccountsResource
public record AccountDto (
        Long id,
        String username,
        String email,
        String phone,
        Role role
){ }
