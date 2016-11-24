package br.ufscar.compiladores2.t3.examples.input;

/**
 * Created by leo on 24/11/16.
 */


class A{
    String x;
    double y;
    int z;

    A(){
        this.x = "a";
        this.y = 2.0;
        this.z = 1;
    }
}

class Ex1 {
    public Ex1() {
    }

    public static void main(String args[]){
        A obj1 = new A();
    }
}

