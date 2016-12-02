package br.ufscar.compiladores2.t3.examples.input;

class E{
    String x;
    double y;
    int z;

    E(){
        this.x = "a";
        this.y = 2.0;
    }

    E(String a, double b, int c){
        this.x = a;
        this.y = b;
        this.z = c;
    }
}
class F extends E{
    boolean k;

    F(){
        super();
        this.k = false;
    }

    F(String a, double b, int c, boolean d){
        super(a,b,c);
        this.k = d;
    }
}
public class Ex5 {
    public Ex5() {
    }

    public static void main(String args[]){
        E obj1 = new E();
        E obj2 = new E("b",3.0,2);
        F obj3 = new F();
    }
}
