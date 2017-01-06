/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufscar.compiladores2.t3.semantic.util;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author daniel
 */
public class PilhaDeTabelas {

    private LinkedList<TabelaDeSimbolos> pilha;

    public PilhaDeTabelas() {
        pilha = new LinkedList<TabelaDeSimbolos>();
    }

    public void empilhar(TabelaDeSimbolos ts) {
        pilha.push(ts);
    }

    public TabelaDeSimbolos topo() {
        return pilha.peek();
    }

    public  TabelaDeSimbolos getGlobalTable(int i){
        return pilha.peekLast();
    }

    public boolean existeSimbolo(String nome) {
        for (TabelaDeSimbolos ts : pilha) {
            if (ts.existeSimbolo(nome)) {
                return true;
            }
        }
        return false;
    }

    public void desempilhar() {
        TabelaDeSimbolos ret = pilha.pop();
//        Saida.println(ret.toString());
    }

    public List getTodasTabelas() {
        return pilha;
    }
}
