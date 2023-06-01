package com.example.demo.Principal.Estruturas;

public final class Modificacao {
    public String nomeProduto;
    public Long idProduto;
    public String nomeUsuario;
    public Long idUsuario;
    public String data;
    public String tipo;
    public int saldoProduto;

    public Modificacao(
        String nomeProduto, 
        String nomeUsuario, 
        Long idProduto, 
        Long idUsuario, 
        String data,
        int saldoProduto, 
        String tipo
    ){
        this.nomeProduto = nomeProduto;
        this.nomeUsuario = nomeUsuario;
        this.idProduto = idProduto;
        this.idUsuario = idUsuario;
        this.data = data;
        this.tipo = tipo;
        this.saldoProduto = saldoProduto;
    }

    public String getNomeProduto(){
        return nomeProduto;
    }

    public String nomeUsuario(){
        return nomeUsuario;
    }

    public Long getIdProduto(){
        return idProduto;
    }

    public Long getIdUsuario(){
        return idUsuario;
    }

    public String getData(){
        return data;
    }

    public String getTipo(){
        return tipo;
    }

    public int getSaldoProduto(){
        return saldoProduto;
    }
    
}
