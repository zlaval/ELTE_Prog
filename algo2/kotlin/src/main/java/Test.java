
class Test {

     long value = 0;

    public static void main(String[] args) {
        Test test = new Test();
        long st = System.currentTimeMillis();
        test.increment();
        long nd = System.currentTimeMillis();

        System.out.println(test.value);
        System.out.println((nd - st));
    }

    public  void increment() {
        for (int i = 0; i < 500_000_000; ++i) {
            value++;
        }

    }

}
