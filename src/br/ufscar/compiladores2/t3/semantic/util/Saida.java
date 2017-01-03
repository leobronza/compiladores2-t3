/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufscar.compiladores2.t3.semantic.util;

/**
 *
 * @author daniel
 */
public class Saida {
    private static StringBuffer texto = new StringBuffer();
    
    public static void println(String txt) {
        texto.append(txt).append("\n");
    }
    
    public static void clear() {
        texto = new StringBuffer();
    }
    
    public static String getTexto() {
        return texto.toString();
    }
}
