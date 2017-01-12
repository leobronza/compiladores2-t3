class B{
    String x;
    double y;
    int z;

    B(String x, double y, int z){
        this.x = z;
        this.y = y;
        this.z = x;
    }
}

class Ex2 {
    Ex2() {
    }
    public static void main(String args[]){
        B obj1 = new B("ob1", 2.0, 3);
        B obj2 = new B("ob2", 3.0, 4);
    }
}
