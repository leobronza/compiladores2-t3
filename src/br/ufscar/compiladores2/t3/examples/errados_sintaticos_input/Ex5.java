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
class F extends E{
    boolean d;

    F(String a, double b, int c, boolean d){
        super(a,b,c);
        this.d = d;
    }
}

class Ex5 {
    Ex5() {
    }

    public static void main(String args[]){
        E obj1 = new E("a",4.0,4);
        E obj2 = new E("b",3.0,2);
        F obj3 = new F("c",3.0,2,true);
    }
}
