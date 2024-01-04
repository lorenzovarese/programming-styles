import java.util.function.Supplier;

public class OneSupplier implements Supplier<Integer>{

    @Override
    public Integer get() {
        return 1;
    }

    public static void main(String[] args) {
        //Named classes
        Supplier<Integer> ncF1 = new OneSupplier();

        //Anonymous class
        Supplier<Integer> acF1 = new Supplier<Integer>() {
            @Override
            public Integer get(){
                return 1;
            }
        };

        //Lambda
        Supplier <Integer> lF1 = () -> 1;

        System.out.println("Named class: " + ncF1.get());
        System.out.println("Anonymous class: " + acF1.get());
        System.out.println("Lambda: " + lF1.get());
    }

}

