package br.ufscar.compiladores2.t3.examples.input;

class G{
    String x;
    float y;
    int z;

    G(){
        this.x = "a";
        this.y = 2;
    }

    G(String a, float b,int c){
        this.x = a;
        this.y = b;
        this.z = c;
    }
}
class H extends G{
    boolean k;

    H(){
        super();
        this.k = false;
    }

    H(String a, float b, int c, boolean d){
        super(a,b,c);
        this.k = d;
    }
}
public class Ex6 {
    public static void main(String args[]){
        G obj1 = new G();
        G obj2 = new G("b",3,2);
        H obj3 = new H();
        G obj4 = new H();
    }

}
