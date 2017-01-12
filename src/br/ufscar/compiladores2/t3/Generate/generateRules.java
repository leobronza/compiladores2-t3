package br.ufscar.compiladores2.t3.Generate;

import br.ufscar.compiladores2.t3.Generate.util.EntradaTabelaDeSimbolos;
import br.ufscar.compiladores2.t3.Generate.util.PilhaDeTabelas;
import br.ufscar.compiladores2.t3.Generate.util.TabelaDeSimbolos;
import br.ufscar.compiladores2.t3.antlr.jaSONBaseVisitor;
import br.ufscar.compiladores2.t3.antlr.jaSONParser;
import org.antlr.v4.codegen.model.decl.StructDecl;
import org.antlr.v4.runtime.misc.IntegerList;
import org.antlr.v4.runtime.tree.TerminalNode;


import java.util.List;

/**
 * Created by leo on 10/01/17.
 */

public class generateRules extends jaSONBaseVisitor<String[]> {
    private PilhaDeTabelas pt;

    @Override
    public String[] visitProgram(jaSONParser.ProgramContext ctx) {
        pt = new PilhaDeTabelas();
        pt.empilhar(new TabelaDeSimbolos("global"));
        System.out.println("{");

        for (jaSONParser.Class_definitionContext  cdc: ctx.class_definition()){
            visitClass_definition(cdc);
        }
        System.out.println("}");
        return null;
    }

    @Override
    public String[] visitClass_definition(jaSONParser.Class_definitionContext ctx) {
        TabelaDeSimbolos escopoAtual = pt.topo();

        if(ctx.classExtended != null){ //se for uma classe extendida
            EntradaTabelaDeSimbolos etds =
                    new EntradaTabelaDeSimbolos(ctx.classe.getText(), "classe", ctx.classe.getText(), ctx.classExtended.getText(), "[", null, null);
            escopoAtual.adicionarSimbolo(etds);

            List<EntradaTabelaDeSimbolos> listaDeVariaveis = escopoAtual.getTodasEntradaDaClasse(ctx.classExtended.getText());
            for (EntradaTabelaDeSimbolos e: listaDeVariaveis){
                EntradaTabelaDeSimbolos newEt =
                        new EntradaTabelaDeSimbolos(e.getNome(), e.getTipo(), ctx.classe.getText(),ctx.classExtended.getText(),e.getToken(),
                                                            e.getConstrutor_param(),e.getValor());
                escopoAtual.adicionarSimbolo(newEt);
            }

        // se nao for herança
        }else {
            if (ctx.class_body().constructors().parameters() != null) {
                EntradaTabelaDeSimbolos etds =
                        new EntradaTabelaDeSimbolos(ctx.classe.getText(), "classe", ctx.classe.getText(), null, "[", null, null);
                escopoAtual.adicionarSimbolo(etds);
            }
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

        return null;
    }

    @Override
    public String[] visitVariables(jaSONParser.VariablesContext ctx) {
        TabelaDeSimbolos escopoAtual = pt.topo();

        String type = visitType(ctx.type())[1];
        String classe = escopoAtual.getUltimaClasseDeclarada();

        for(TerminalNode tn : ctx.IDENT() ){
            EntradaTabelaDeSimbolos etds =
                    new EntradaTabelaDeSimbolos(tn.toString(), type, classe, null, "\""+tn.toString()+"\":", null, null);
            escopoAtual.adicionarSimbolo(etds);
        }
        return null;
    }

    @Override
    public String[] visitConstructors(jaSONParser.ConstructorsContext ctx) {
        if(ctx.parameters() != null){
            visitParameters(ctx.parameters());
            visitConstructor_body(ctx.constructor_body());
            pt.desempilhar();
        }else{
            visitConstructor_body(ctx.constructor_body());
        }

        return null;
    }

    @Override
    public String[] visitConstructor_body(jaSONParser.Constructor_bodyContext ctx) {
        TabelaDeSimbolos params = pt.topo();
        TabelaDeSimbolos glboal = pt.getGlobalTable();


        for (jaSONParser.AssignmentContext context: ctx.assignment()){
            visitAssignment(context);
        }
        return null;
    }

    @Override
    public String[] visitAssignment(jaSONParser.AssignmentContext ctx) {
        TabelaDeSimbolos params = pt.topo();
        TabelaDeSimbolos global = pt.getGlobalTable();

        String attribute = visitAttribute(ctx.attribute())[1];

        //ultima classe declarada
        String classe = global.getUltimaClasseDeclarada();
        for(EntradaTabelaDeSimbolos ent: global.getTodasEntradaDaClasse(classe)){
            if(ent.getNome().equalsIgnoreCase(attribute)){
                if(ctx.IDENT() != null) {
                    ent.setConstrutor_param(String.valueOf(params.getPosicaoSimbolo(ctx.IDENT().toString())));
                }else if(ctx.NUM_INT() != null){
                    ent.setConstrutor_param(String.valueOf(-1));
                    ent.setValor(ctx.NUM_INT().toString());
                }else if(ctx.NUM_FLOAT() != null) {
                    ent.setConstrutor_param(String.valueOf(-1));
                    ent.setValor(ctx.NUM_FLOAT().toString());
                }if(ctx.STRING() != null) {
                    ent.setConstrutor_param(String.valueOf(-1));
                    ent.setValor(ctx.STRING().toString());
                }
            }
        }
        return null;
    }

    @Override
    public String[] visitAttribute(jaSONParser.AttributeContext ctx) {
        String[] s = {"true",ctx.IDENT().toString()};
        return s;
    }

    @Override
    public String[] visitParameters(jaSONParser.ParametersContext ctx) {
        String classe = pt.topo().getUltimaClasseDeclarada();
        TabelaDeSimbolos params = new TabelaDeSimbolos("params");

        //add os parametros na tabela
        String type = null;
        String id = null;

        for(int i =0;i<ctx.IDENT().size();i++){
            type = visitType(ctx.type(i))[1];
            id = ctx.IDENT(i).toString();
            params.adicionarSimbolo(id,type,classe);
        }

        pt.empilhar(params);

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

        for (int i = 0;i< ctx.object_declaration().size();i++){
            jaSONParser.Object_declarationContext obj = ctx.object_declaration(i);

            visitObject_declaration(obj);

            if(i < ctx.object_declaration().size()-1)
                System.out.println(",");
            else
                System.out.println("");
        }

        pt.desempilhar();
        return null;
    }

    @Override
    public String[] visitObject_declaration(jaSONParser.Object_declarationContext ctx) {
        TabelaDeSimbolos main = pt.topo();

        System.out.println("\t\""+ctx.name.getText()+"\": [");

        if(ctx.arguments() != null){
            main.adicionarSimbolo(ctx.t1.getText(),"classe");
            visitArguments(ctx.arguments());
        }
        System.out.print("\t]");
        return null;
    }

    @Override
    public String[] visitArguments(jaSONParser.ArgumentsContext ctx) {
        TabelaDeSimbolos global = pt.getGlobalTable();
        TabelaDeSimbolos main = pt.topo();
        String classe = main.getUltimaClasseDeclarada();
        List<EntradaTabelaDeSimbolos> todasEntradas = global.getTodasEntradaDaClasse(classe);
        EntradaTabelaDeSimbolos etds = null;

        for (int i =0;i<todasEntradas.size();i++){
            etds = todasEntradas.get(i);

            if(Integer.parseInt(etds.getConstrutor_param()) != -1){
                String valor = visitValue(ctx.value(Integer.parseInt(etds.getConstrutor_param())))[1];
                System.out.print("\t\t"+etds.getToken()+" "+valor);
            }else{
                System.out.print("\t\t"+etds.getToken()+" "+etds.getValor());
            }
            if(i < todasEntradas.size()-1){
                System.out.println(",");
            }else{
                System.out.println();
            }
        }
        return null;
    }

    @Override
    public String[] visitValue(jaSONParser.ValueContext ctx) {
        String[] s = {"true", ctx.getText()};
        return s;
    }
}
