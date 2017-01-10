import br.ufscar.compiladores2.t3.antlr.jaSONLexer;
import br.ufscar.compiladores2.t3.antlr.jaSONParser;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.RecognitionException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;

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
