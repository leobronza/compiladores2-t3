package br.ufscar.compiladores2.t3.semantic;

/**
 * Created by lucas on 03/01/17.
 */

import br.ufscar.compiladores2.t3.antlr.*;

public class SemanticRules extends jaSONBaseVisitor<String[]>  {

    @Override
    public String[] visitProgram(jaSONParser.ProgramContext ctx) {
        System.out.println("foi");
        return null;
    }

    @Override
    public String[] visitClass_definition(jaSONParser.Class_definitionContext ctx) {
        return super.visitClass_definition(ctx);
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
    public String[] visitArguments(jaSONParser.ArgumentsContext ctx) {
        return super.visitArguments(ctx);
    }

    @Override
    public String[] visitValue(jaSONParser.ValueContext ctx) {
        return super.visitValue(ctx);
    }
}
