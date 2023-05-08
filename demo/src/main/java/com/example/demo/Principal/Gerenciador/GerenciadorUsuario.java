package com.example.demo.Principal.Gerenciador;

import com.example.demo.Principal.Entidade.Produto;
import com.example.demo.Principal.Entidade.Usuario;

public class GerenciadorUsuario {
    private static GerenciadorUsuario gerenciadorUsuario = null;
    private static GerenciadorMongoDB gerenciadorMongoDB = null;
    private static Usuario usuario;

    private GerenciadorUsuario(){
        usuario = null;
        gerenciadorMongoDB = GerenciadorMongoDB.getGerenciadorMongoDB();
    }

    public static GerenciadorUsuario getGerenciadorUsuario(){
        if(gerenciadorUsuario == null){
            gerenciadorUsuario = new GerenciadorUsuario();
        }
        return gerenciadorUsuario;
    }

    public void verificaUsuario(){
        if(usuario == null){
            System.out.println("Usuário eh nulo!");
            System.exit(0);
        }
    }

    public void setUsuario(Usuario usuario){
        if(usuario == null){
            System.out.println("Usuário eh nulo!");
            System.exit(0);
        }
        GerenciadorUsuario.usuario = usuario;
    }

    public Boolean ehAdministrador(){
        verificaUsuario();
        return usuario.getAdministrador();
    }

    public void monidificouProduto(Produto produto){
        verificaUsuario();
        if(produto == null){
            System.out.println("Produto eh nulo!");
            System.exit(0);
        }
        gerenciadorMongoDB.addMonitoramento(produto, usuario);
    }

}
