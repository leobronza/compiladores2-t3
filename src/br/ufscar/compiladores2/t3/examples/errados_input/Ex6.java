class G{
    String x;
    float y;
    int z;

    G(String a, float b,int c){
        this.x = a;
        this.y = b;
        this.z = c;
    }
}
class H extends G{
    boolean d;

    H(String a, float b, int c, boolean d){
        super(a,b,c);
        this.d = d;
    }
}
class Ex6 {
    Ex6() {
    }

    public static void main(String args[]){
        G obj1 = new G("a",2,1);
        G obj2 = new G("b",3,2);
        H obj3 = new H("c",3,2,true);
        G obj4 = new H("d",3,2,false);
    }

}
