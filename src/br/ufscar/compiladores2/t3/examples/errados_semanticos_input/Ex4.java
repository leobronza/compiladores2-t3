class D{
    String x;
    double y;
    int z;

    D(String a, double b, int c){
        this.x = a;
        this.y = b;
        this.z = 2147;
    }
}
class Ex4 {
    int a, b;
    boolean c;
    String d;

    Ex4(int a, int b, boolean c, String d) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
    }

    public static void main(String args[]){
        C obj1 = new C("s", 4.0,3);
        D obj2 = new D("b",3.0,2);
    }
}
