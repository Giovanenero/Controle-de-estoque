package com.example.demo.Principal.Gerenciador;

//import com.example.demo.Principal.Entidade.Produto;
import com.example.demo.Principal.Entidade.Usuario;

public class GerenciadorUsuario {
    private static GerenciadorUsuario gerenciadorUsuario;
    private static Usuario usuario;

    private GerenciadorUsuario(){
        gerenciadorUsuario = null;
        usuario = null;
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
            System.exit(1);
        }
    }

    public void setUsuario(Usuario usuario){
        if(usuario == null){
            System.out.println("Usuário eh nulo!");
            System.exit(1);
        }
        GerenciadorUsuario.usuario = usuario;
    }

    public Usuario getUsuario(){
        verificaUsuario();
        return usuario;
    }

    public Boolean ehAdministrador(){
        verificaUsuario();
        return usuario.getAdministrador();
    }
}
