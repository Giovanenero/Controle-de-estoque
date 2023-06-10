package com.example.demo.Principal.Estruturas;

import com.example.demo.Principal.Ente;

public final class Modificacao extends Ente {
    private Long idProduto;
    private Long idUsuario;
    private String data;
    private String tipo;
    private int saldoAnterior;
    private int novoSaldo;

    public Modificacao(
        Long idProduto, 
        Long idUsuario, 
        int saldoAnterior,
        int novoSaldo,
        String tipo,
        String data
    ){
        super(Long.parseLong("123"), "modificacao");
        this.idProduto = idProduto;
        this.idUsuario = idUsuario;
        this.data = data;
        this.tipo = tipo;
        this.saldoAnterior = saldoAnterior;
        this.novoSaldo = novoSaldo;
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

    public int getSaldoAnterior(){
        return saldoAnterior;
    }

    public int getNovoSaldo(){
        return novoSaldo;
    }
    
}
