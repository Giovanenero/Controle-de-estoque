package com.example.demo.Principal;
import com.example.demo.Principal.Gerenciador.*;

public class Principal {
    //atributos
    private static GerenciadorGrafico grafico = null;
    private static GerenciadorEstado estado = null;

    //métodos
    public Principal(){

    }
    public void executar(){
        grafico = GerenciadorGrafico.getGerenciadorGrafico();
        estado = GerenciadorEstado.getGerenciadorEstado();
        grafico.novoJanela();
        estado.criarEstados();
    }
}