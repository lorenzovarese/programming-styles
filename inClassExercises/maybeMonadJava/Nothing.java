package inClassExercises.maybeMonadJava;
import java.util.NoSuchElementException;

// The Nothing class represents a Maybe with no value
public class Nothing<T> extends Maybe<T> {

    @Override
    public boolean isPresent() {
        return false;
    }

    @Override
    public T get() {
        throw new NoSuchElementException("No value present");
    }

    @Override
    public <U> Maybe<U> map(java.util.function.Function<? super T, ? extends U> mapper) {
        return new Nothing<>();
    }
}
