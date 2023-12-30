import java.util.function.Function;

public class functionComposition {
    private static double neg(double x) {
        return -x;
    }

    private static double sqr(double x) {
        return x * x;
    }

    /**
     * Composes two functions together.
     *
     * @param <A> the type of the input to the first function
     * @param <B> the type of the output of the first function and the input to the second function
     * @param <C> the type of the output of the second function
     * @param g the first function to apply
     * @param f the second function to apply
     * @return a composed function that applies the first function and then the second function
     */
    private static <A,B,C> Function<A,C> compose(Function<A,B> g, Function<B,C> f){
        return (A x) -> f.apply(g.apply(x)); //f(g(x))  f o g
    }

    public static void main(String[] args) {
        Function<Double, Double> negOfSqr = compose(functionComposition::sqr, functionComposition::neg);
        System.out.println(negOfSqr.apply(2.0));
    }
}
