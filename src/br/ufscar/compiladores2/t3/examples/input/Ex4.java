package br.ufscar.compiladores2.t3.examples.input;

class D{
    String x;
    double y;
    int z;

    D(){
        this.x = "a";
        this.y = 2.0;
    }

    D(String a, double b, int c){
        this.x = a;
        this.y = b;
        this.z = c;
    }
}
public class Ex4 {
    int a, b;
    boolean c;
    String d;

    Ex4() {
        this.a = 5;
        this.b = 2;
        this.c = true;
        this.d = false;
    }

    public static void main(String args[]){
        D obj1 = new D();
        D obj2 = new D("b",3.0,2);
    }
}
