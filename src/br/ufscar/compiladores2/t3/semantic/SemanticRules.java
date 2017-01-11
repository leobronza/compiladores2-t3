package br.ufscar.compiladores2.t3.semantic;

/**
 * Created by lucas on 03/01/17.
 */

import br.ufscar.compiladores2.t3.antlr.*;
import br.ufscar.compiladores2.t3.semantic.util.EntradaTabelaDeSimbolos;
import br.ufscar.compiladores2.t3.semantic.util.PilhaDeTabelas;
import br.ufscar.compiladores2.t3.semantic.util.TabelaDeSimbolos;
//import com.sun.javafx.fxml.expression.Expression;
//import javafx.scene.control.Tab;
//import jdk.nashorn.internal.ir.Assignment;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.List;

public class SemanticRules extends jaSONBaseVisitor<String[]>  {

    private PilhaDeTabelas pt;
    private TabelaDeSimbolos class_parameters;

    @Override
    public String[] visitProgram(jaSONParser.ProgramContext ctx) {
        pt = new PilhaDeTabelas();
        pt.empilhar(new TabelaDeSimbolos("global"));
        class_parameters = new TabelaDeSimbolos("ClassParameters");

        for (jaSONParser.Class_definitionContext  cdc: ctx.class_definition()){
            visitClass_definition(cdc);
        }
        return null;
    }

    @Override
    public String[] visitClass_definition(jaSONParser.Class_definitionContext ctx) {
        TabelaDeSimbolos escopoAtual = pt.topo();

        if(ctx.classExtended != null){ //se for uma classe extendida
            if (escopoAtual.existeSimbolo(ctx.classExtended.getText())){
                escopoAtual.adicionarSimbolo(ctx.classe.getText(), "classe",ctx.classe.getText(), ctx.classExtended.getText());
                class_parameters.adicionarSimbolo(ctx.classe.getText(), "classe",ctx.classe.getText(), ctx.classExtended.getText());

                List<EntradaTabelaDeSimbolos> listaDeVariaveis = escopoAtual.getTodasEntradaDaClasse(ctx.classExtended.getText());
                for (EntradaTabelaDeSimbolos e: listaDeVariaveis){
                    escopoAtual.adicionarSimbolo(e.getNome(),e.getTipo(),ctx.classe.getText(),ctx.classExtended.getText());
                }
            }else{
                System.out.println("Linha "+ ctx.getStart().getLine() +": Classe herdada não existe");

            }

        }else {
            escopoAtual.adicionarSimbolo(ctx.classe.getText(), "classe");
            class_parameters.adicionarSimbolo(ctx.classe.getText(), "classe");
        }
        visitClass_body(ctx.class_body());

        return null;
    }

    @Override
    public String[] visitClass_body(jaSONParser.Class_bodyContext ctx) {
        for (jaSONParser.VariablesContext  variable: ctx.variables()){
            visitVariables(variable);
        }

        if(ctx.constructors() != null){
            visitConstructors(ctx.constructors());
        }

        if(ctx.function_main() !=  null){
            visitFunction_main(ctx.function_main());
        }

        //todo implementar construtor e main

        return null;
    }

    @Override
    public String[] visitVariables(jaSONParser.VariablesContext ctx) {
        TabelaDeSimbolos escopoAtual = pt.topo();
        String type = visitType(ctx.type())[1];
        String classe = escopoAtual.getUltimaClasseDeclarada();
        for(TerminalNode tn : ctx.IDENT() ){
            escopoAtual.adicionarSimbolo(tn.toString(), type, classe, null);
        }

        return null;
    }

    @Override
    public String[] visitConstructors(jaSONParser.ConstructorsContext ctx) {
        TabelaDeSimbolos escopoAtual = pt.topo();

        if(escopoAtual.getUltimaClasseDeclarada().equals(ctx.IDENT().toString()) ){
            if(ctx.parameters() != null){
                visitParameters(ctx.parameters());
            }
            visitConstructor_body(ctx.constructor_body());
        }else{
            System.out.println("Linha "+ ctx.getStart().getLine() +": Construtor nao compativel com a classe");
        }
            return null;
    }

    @Override
    public String[] visitConstructor_body(jaSONParser.Constructor_bodyContext ctx) {
        TabelaDeSimbolos escopoAtual = pt.topo();

        if(escopoAtual.getUltimaClasseDeclarada2().getClasseHerdada() != null) {
            if(ctx.arguments() != null){
                visitArguments(ctx.arguments());
            }else{
                System.out.println("Linha "+ ctx.getStart().getLine() +": Classe herdada sem metodo super");

            }
        }else{
            if(ctx.arguments() != null) {
                System.out.println("Linha "+ ctx.getStart().getLine() +":Classe nao herdada com metodo super");
            }
        }

        for (jaSONParser.AssignmentContext context: ctx.assignment()){
            visitAssignment(context);
        }
        //TODO verificar super
        return null;
    }

    @Override
    public String[] visitAssignment(jaSONParser.AssignmentContext ctx) {
        String type = null;
        String classe = pt.topo().getUltimaClasseDeclarada();

        //1. verificar se o parametro existe, se eh igual ao
        //   eh atribuição de variável para variável
        if(ctx.IDENT() != null){
                if(!class_parameters.existeSimboloComClasse(ctx.IDENT().toString(),classe)){
                    System.out.println("Linha "+ ctx.getStart().getLine() +": Variável não declarada como parametro");
                }else{
                    type = class_parameters.getTipoSimboloComClasse(ctx.IDENT().toString(),classe);
                }

        }else if(ctx.STRING() != null){
            type = "String";
        }else if(ctx.NUM_INT() != null) {
            type = "int";
        }else if(ctx.NUM_FLOAT() != null){
            type = "float";
        }else if(ctx.BOOLEAN() != null){
            type = "boolean";
        }

        //2. vefiricar se o atributo existe, se eh igual ao ident
        String ret[] = visitAttribute(ctx.attribute());
        if(ret[0].equalsIgnoreCase("true")){
            //3. verificar se o parametro e o atributo sao do mesmo tipo
            if(!ret[1].equalsIgnoreCase(type)){
                System.out.println("Linha "+ ctx.getStart().getLine() +": Tipo de atribuição não compatível");
            }
        }
        return null;
    }

    @Override
    public String[] visitAttribute(jaSONParser.AttributeContext ctx) {
        boolean existe = false;
        String[] s = {"false", null};
        TabelaDeSimbolos global = pt.getGlobalTable();

        //pega a ultima classe declarada, que é a classe do construtor
        String classe = global.getUltimaClasseDeclarada();
        //atributos da classe q eh do construtor
        List<EntradaTabelaDeSimbolos> list =  global.getTodasEntradaDaClasse(classe);

        for (EntradaTabelaDeSimbolos e: list){
            if(e.getNome().equals(ctx.IDENT().toString())){
                s[0] = "true";
                s[1] = e.getTipo();
                existe = true;
            }
        }

        if(!existe){
            System.out.println("Linha "+ ctx.getStart().getLine() +": Atributo não declarado na classe");
        }

        return s;
    }

    @Override
    public String[] visitParameters(jaSONParser.ParametersContext ctx) {
        String classe = pt.topo().getUltimaClasseDeclarada();

        //add os parametros na tabela
        String type = null;
        String id = null;
        for(int i =0;i<ctx.IDENT().size();i++){
            type = visitType(ctx.type(i))[1];
            id = ctx.IDENT(i).toString();
            class_parameters.adicionarSimbolo(id,type,classe);
        }

        return null;
    }

    @Override
    public String[] visitType(jaSONParser.TypeContext ctx) {
        String[] s = {"false", ctx.getText()};
        return s;
    }

    @Override
    public String[] visitFunction_main(jaSONParser.Function_mainContext ctx) {

        visitFunction_body(ctx.function_body());

        return null;
    }

    @Override
    public String[] visitFunction_body(jaSONParser.Function_bodyContext ctx) {
        TabelaDeSimbolos main = new TabelaDeSimbolos("main");
        pt.empilhar(main);

        for (jaSONParser.Object_declarationContext obj: ctx.object_declaration()){
            visitObject_declaration(obj);
        }

        pt.desempilhar();
        return null;
    }

    @Override
    public String[] visitObject_declaration(jaSONParser.Object_declarationContext ctx) {
        TabelaDeSimbolos escopoGlobal = pt.getGlobalTable();
        TabelaDeSimbolos main = pt.topo();

        //  verifica se existe a classe
        if(escopoGlobal.existeSimboloComTipo(ctx.t1.getText(),"classe")){
            main.adicionarSimbolo(ctx.t1.getText(),"classe");
            visitArguments(ctx.arguments());
        }else{
            System.out.println("Linha "+ ctx.getStart().getLine() +": Classe não existe");
        }

        return null;
    }

    @Override
    public String[] visitArguments(jaSONParser.ArgumentsContext ctx) {
        TabelaDeSimbolos main = pt.topo();
        String classe = main.getUltimaClasseDeclarada();
        String[] value;

        // pega todos os parametros do construtor da classe
        List<EntradaTabelaDeSimbolos> params = class_parameters.getTodasEntradaDaClasse(classe);

        for(int i =0;i<ctx.value().size();i++){
            value = visitValue(ctx.value(i));
            if(value != null){
                if(!value[1].equalsIgnoreCase("Ident")){
                    if(!params.get(i).getTipo().equalsIgnoreCase(value[1])){
                        System.out.println("Linha "+ ctx.getStart().getLine() +": Parametros não compatível com o construtor");
                        break;
                    }
                }else{
                    System.out.println("erro nao eh ident");
                }
            }
        }




        return null;
    }

    @Override
    public String[] visitValue(jaSONParser.ValueContext ctx) {
        String[] s = {"true",null};
        if (ctx.IDENT() != null) {
            s[1] = "ident";
            return s;
        }else if(ctx.STRING() != null){
            s[1] = "String";
            return s;
        }else if(ctx.NUM_FLOAT() != null){
            s[1] = "double";
            return s;
        }else if(ctx.NUM_INT() != null){
            s[1] = "int";
            return s;
        }

        return null;
    }
}
