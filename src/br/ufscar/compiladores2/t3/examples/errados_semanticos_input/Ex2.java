class B{
    String x;
    double y;
    int z;

    B(String x, double y, int z){
        this.x = x;
        this.y = y;
        this.z = z;
    }
}

class Ex2 {
    Ex2() {
    }
    public static void main(String args[]){
        C obj1 = new B("ob1", 2.0, "ob1");
        B obj2 = new B("ob2", 3.0, 4);

    }
}
