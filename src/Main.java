import org.antlr.v4.runtime.*;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import  br.ufscar.compiladores2.t3.antlr.*;

public class Main {
    private final static String CAMINHO_CASOS_TESTE =
            "/home/lucas/Dropbox/bcc/2016/2sem/cc2/compiladores2-t3/src/br/ufscar/compiladores2/t3/examples/errados_sintaticos_input";

    public static void main(String[] args) throws IOException, RecognitionException, NullPointerException{
        File diretorioCasosTeste = new File(CAMINHO_CASOS_TESTE);
        File[] casosTeste = diretorioCasosTeste.listFiles();

        if(casosTeste != null){
            Arrays.sort(casosTeste);
            for (File casoTeste : casosTeste) {

                System.out.println(casoTeste.getName());
                System.out.println("--------------------------------------------------------------------------------");

                ANTLRInputStream input = new ANTLRInputStream(new FileInputStream(casoTeste));
                jaSONLexer lexer = new jaSONLexer(input);
                CommonTokenStream tokens = new CommonTokenStream(lexer);
                jaSONParser parser = new jaSONParser(tokens);
                parser.program();
                //AnalisadorSemantico semantico = new AnalisadorSemantico();
                //semantico.visitPrograma(context);
                System.out.println("\n");
            }
        }
    }
}
