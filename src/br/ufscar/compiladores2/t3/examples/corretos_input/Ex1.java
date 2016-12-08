class A {
    String x;
    double y;
    int z;

    A(String x, double y, int z){
        this.x = x;
        this.y = y;
        this.z = z;
    }
}

class A1 extends A {
    double h;

    A1(String x, double y, int z, double h) {
        super(x, y, z);
        this.h = h;
    }
}

class Ex1 {
    Ex1() {
    }

    public static void main(String args[]){
        A1 obj1 = new A1("abc", 1.2, 5 ,2.2);
    }
}

