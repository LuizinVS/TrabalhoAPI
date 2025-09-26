package com.Aula5.ProjetoZoo.ApiZoologico.exceptions;

public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException(String message){
        super(message);
    }
}