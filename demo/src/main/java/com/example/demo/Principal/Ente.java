package com.example.demo.Principal;

public class Ente {
    //atributos
    protected Long id;
    protected String nome;

    //métodos
    public Ente(Long id, String nome){
        this.id = id;
        this.nome = nome;
    }
    public Long getId(){
        return id;
    }
    public String getNome(){
        return nome;
    }
}
