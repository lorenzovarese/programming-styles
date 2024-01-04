package inClassExercises.maybeMonadJava;

// The Maybe abstract class
public abstract class Maybe<T> {

    // The method to determine if the Maybe contains a value
    public abstract boolean isPresent();

    // The method to get the value. It should throw an exception if there is no value.
    public abstract T get();

    // The method to perform an operation on the value if it is present.
    public abstract <U> Maybe<U> map(java.util.function.Function<? super T, ? extends U> mapper);
}
