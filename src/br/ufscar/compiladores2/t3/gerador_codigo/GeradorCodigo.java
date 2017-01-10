package br.ufscar.compiladores2.t3.gerador_codigo;
import br.ufscar.compiladores2.t3.antlr.jaSONBaseVisitor;
import br.ufscar.compiladores2.t3.antlr.jaSONParser;
import br.ufscar.compiladores2.t3.semantic.util.EntradaTabelaDeSimbolos;
import br.ufscar.compiladores2.t3.semantic.util.PilhaDeTabelas;
import br.ufscar.compiladores2.t3.semantic.util.TabelaDeSimbolos;

import java.util.List;

/**
 * Created by User on 10/01/2017.
 */
public class GeradorCodigo extends jaSONBaseVisitor<String[]> {

    private PilhaDeTabelas pt;
    private TabelaDeSimbolos class_parameters;

    @Override
    public String[] visitProgram(jaSONParser.ProgramContext ctx) {

        System.out.println("{");
        pt = new PilhaDeTabelas();
        pt.empilhar(new TabelaDeSimbolos("global"));
        class_parameters = new TabelaDeSimbolos("ClassParameters");

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
            escopoAtual.adicionarSimbolo(ctx.classe.getText(), "classe",ctx.classe.getText(), ctx.classExtended.getText());
            class_parameters.adicionarSimbolo(ctx.classe.getText(), "classe",ctx.classe.getText(), ctx.classExtended.getText());

            List<EntradaTabelaDeSimbolos> listaDeVariaveis = escopoAtual.getTodasEntradaDaClasse(ctx.classExtended.getText());
            for (EntradaTabelaDeSimbolos e: listaDeVariaveis){
                escopoAtual.adicionarSimbolo(e.getNome(),e.getTipo(),ctx.classe.getText(),ctx.classExtended.getText());
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
        return super.visitClass_body(ctx);
    }

    @Override
    public String[] visitVariables(jaSONParser.VariablesContext ctx) {
        return super.visitVariables(ctx);
    }

    @Override
    public String[] visitConstructors(jaSONParser.ConstructorsContext ctx) {
        return super.visitConstructors(ctx);
    }

    @Override
    public String[] visitConstructor_body(jaSONParser.Constructor_bodyContext ctx) {
        return super.visitConstructor_body(ctx);
    }

    @Override
    public String[] visitAssignment(jaSONParser.AssignmentContext ctx) {
        return super.visitAssignment(ctx);
    }

    @Override
    public String[] visitAttribute(jaSONParser.AttributeContext ctx) {
        return super.visitAttribute(ctx);
    }

    @Override
    public String[] visitParameters(jaSONParser.ParametersContext ctx) {
        return super.visitParameters(ctx);
    }

    @Override
    public String[] visitType(jaSONParser.TypeContext ctx) {
        return super.visitType(ctx);
    }

    @Override
    public String[] visitFunction_main(jaSONParser.Function_mainContext ctx) {
        return super.visitFunction_main(ctx);
    }

    @Override
    public String[] visitFunction_body(jaSONParser.Function_bodyContext ctx) {
        return super.visitFunction_body(ctx);
    }

    @Override
    public String[] visitObject_declaration(jaSONParser.Object_declarationContext ctx) {
        return super.visitObject_declaration(ctx);
    }

    @Override
    public String[] visitArguments(jaSONParser.ArgumentsContext ctx) {
        return super.visitArguments(ctx);
    }

    @Override
    public String[] visitValue(jaSONParser.ValueContext ctx) {
        return super.visitValue(ctx);
    }
}
