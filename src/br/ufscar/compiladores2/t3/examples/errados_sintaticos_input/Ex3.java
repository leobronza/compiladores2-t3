class C{

    String x;
    double y;
    int z;

    C(String a, double b, int c){
        this.x = a;
        this.y = b;
        this.z = c;
    }
}
class Ex3 {

    Ex3() {
    }

    public static void main(String args[]){
        C obj1 = new C("a", 4.0, 3);
        C obj2 = new C("b",3.0,2);
    }
}
