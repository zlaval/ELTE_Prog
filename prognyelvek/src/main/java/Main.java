public class Main {

    public static void main(String[] args) {
        var x = new A() {
            @Override
            public void print() {
                System.out.println("zalán");
            }
        };

        x.print();
    }
}
