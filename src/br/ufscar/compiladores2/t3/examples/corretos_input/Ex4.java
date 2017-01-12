class D{
    String x;
    double y;
    int z;

    D(String a, double b, int c){
        this.x = a;
        this.y = b;
        this.z = 42;
    }
}
class Ex4 {
    Ex4() {
    }

    public static void main(String args[]){
        D obj1 = new D("s", 4.0,3);
        D obj2 = new D("b",3.0,2);
        D obj3 = new D("c",8.0,2);
    }
}
