import java.util.function.Function;

/**
 * Represents an identity function that returns the input value as the output value.
 */
public class IdFunction implements Function<Integer, Integer> {

    /**
     * Applies the identity function to the given input value.
     *
     * @param t the input value
     * @return the input value itself
     */
    @Override
    public Integer apply(Integer t) {
        return t;
    }

    /**
     * Main method to test the identity function.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        Function<Integer, Integer> ncF = new IdFunction();
        Function<Integer, Integer> acF = new Function<Integer,Integer>() {
            @Override
            public Integer apply(Integer t) {
                return t;
            }
        };
        Function<Integer,Integer> lF = x -> x;

        Integer value = 5;

        System.out.println(String.format("Named Class value {%d}: %d", value, ncF.apply(value)));
        System.out.println(String.format("Anonymous Class value {%d}: %d", value, acF.apply(value)));
        System.out.println(String.format("Lambda value {%d}: %d", value, lF.apply(value)));
    }
}
