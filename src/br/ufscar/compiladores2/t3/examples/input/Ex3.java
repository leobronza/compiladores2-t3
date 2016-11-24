package br.ufscar.compiladores2.t3.examples.input;

class C{

    String x;
    double y;
    int z;

    C(){
        this.x = "a";
        this.y = 2.0;
        this.z = 1;
    }

    C(String a, double b, int c){
        this.x = a;
        this.y = b;
        this.z = c;
    }
}
public class Ex3 {
    public Ex3() {
    }
    public static void main(String args[]){
        C obj1 = new C();
        C obj2 = new C("b",3.0,2);
    }
}
