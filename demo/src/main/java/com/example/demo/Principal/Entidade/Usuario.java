package com.example.demo.Principal.Entidade;

import com.example.demo.Principal.Ente;

public class Usuario extends Ente {
    //atributos
    private boolean ehAdministrador;
    private String senha;
    
    //métodos
    public Usuario(int id, String nome, String senha, boolean ehAdministrador){
        super(id, nome);
        this.ehAdministrador = ehAdministrador;
        this.senha = senha;
    }

    public String getSenha(){
        return senha;
    }

    public boolean getAdministrador(){
        return ehAdministrador;
    }
}
