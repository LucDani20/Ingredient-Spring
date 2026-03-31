package com.daniel.ingredient_td5.erreur;

public class RessourceNotFoundException extends RuntimeException{
    public RessourceNotFoundException(String message) {
        super(message);
    }
}
