package br.ufscar.compiladores2.t3.examples.input;


class B{
    String x;
    double y;
    int z;

    B(){
        this.x = "a";
        this.y = 2.0;
        this.z = 1;
    }
}

public class Ex2 {
    public Ex2() {
    }
    public static void main(String args[]){
        B obj1 = new B();
        B obj2 = new B();
    }
}
