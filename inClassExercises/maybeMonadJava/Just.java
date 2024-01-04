package inClassExercises.maybeMonadJava;

// The Just class represents a Maybe with a value
public class Just<T> extends Maybe<T> {
    private final T value;

    public Just(T value) {
        this.value = value;
    }

    @Override
    public boolean isPresent() {
        return true;
    }

    @Override
    public T get() {
        return value;
    }

    @Override
    public <U> Maybe<U> map(java.util.function.Function<? super T, ? extends U> mapper) {
        return new Just<>(mapper.apply(value));
    }
}
