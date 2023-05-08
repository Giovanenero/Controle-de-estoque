package com.example.demo.Principal.Gerenciador;
import java.util.Vector;

import com.example.demo.Principal.Menu.MenuEntrada;
import com.example.demo.Principal.Menu.MenuHome;
import com.example.demo.Principal.Menu.MenuLogin;
import com.example.demo.Principal.Menu.MenuSaida;

public class GerenciadorEstado {
    //atributos
    private static GerenciadorEstado gerenciadorEstado = null;
    private static GerenciadorUsuario gerenciadorUsuario = null;
    private Vector<Estado> estados = null;
    private int posEstadoAtual;

    //métodos
    private GerenciadorEstado() {
        //Criar todos os estados
        gerenciadorUsuario = GerenciadorUsuario.getGerenciadorUsuario();
        criarEstados();
    }

    public static GerenciadorEstado getGerenciadorEstado(){
        if(gerenciadorEstado == null){
            gerenciadorEstado = new GerenciadorEstado();
        }
        return gerenciadorEstado;
    }

    public void criarEstados(){
        estados = new Vector<>();
        MenuLogin menuLogin = new MenuLogin();
        MenuHome menuHome = new MenuHome();
        MenuEntrada menuEntrada = new MenuEntrada();
        MenuSaida menuSaida = new MenuSaida();
        menuLogin.renderizarComponentes();
        //menuHome.renderizarComponentes();
        estados.add(menuLogin);
        estados.add(menuHome);
        estados.add(menuEntrada);
        estados.add(menuSaida);
        int i = 0;
        boolean naoEncontrou = true;
        while(i < estados.size() && naoEncontrou){
            if(estados.get(i).getNome() == "menuLogin"){
                posEstadoAtual = i;
                naoEncontrou = !naoEncontrou;
            }
            i++;
        }
    }

    public Boolean alterarEstado(String nome){
        Boolean administrador = gerenciadorUsuario.ehAdministrador();
        if(nome == "menuHome" || nome == "menuLogin" || administrador){
            int i = 0;
            boolean naoEcontrou = true;
            estados.get(posEstadoAtual).removerComponentes();
            while(i < estados.size() && naoEcontrou){
                Estado estado = estados.get(i);
                if(estado.getNome() == nome){
                    posEstadoAtual = i;
                    naoEcontrou = false;
                    estado.renderizarComponentes();
                }
                i++;
            }
            if(naoEcontrou){
                System.out.println("Nome de estado invalido");
                System.exit(0);
            }
            return true;
        } 
        return false;
    }
}
