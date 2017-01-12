class E{
    String x;
    double y;
    int z;

    E(String a, double b, int c){
        this.x = a;
        this.y = b;
        this.z = c;
    }
}
class F {
    boolean d;
    float o;

    F(boolean d, float o){
        this.d = d;
        this.o = o;
    }
}

class Ex5 {
    Ex5() {
    }

    public static void main(String args[]){
        E obj1 = new E("a",4.0,4);
        E obj2 = new E("b",3.0,2);
        F f1 = new F(true, 2.3);
        F f2 = new F(false, 5.6);
    }
}
