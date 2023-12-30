import java.util.function.Consumer;

public class DropConsumer implements Consumer<Integer> {

    @Override
    public void accept(Integer t) {
        System.out.println("Named class call");
    }

    public static void main(String[] args) {
        // Named classes
        Consumer<Integer> ncF1 = new DropConsumer();

        // Anonymous class
        Consumer<Integer> acF1 = new Consumer<Integer>() {
            @Override
            public void accept(Integer t) {
                System.out.println("Anonymous class call");
            }
        };

        // Lambda
        Consumer<Integer> lF1 = (x) -> { System.out.println("Lambda call");};

        ncF1.accept(45);
        acF1.accept(44);
        lF1.accept(24);
    }
}
