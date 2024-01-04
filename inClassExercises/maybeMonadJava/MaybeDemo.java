package inClassExercises.maybeMonadJava;

import java.util.NoSuchElementException;

public class MaybeDemo {

    public static void main(String[] args) {
        // Create a Maybe object that contains a value
        Maybe<Integer> justValue = new Just<>(5);

        // Create a Maybe object that contains no value
        Maybe<Integer> nothingValue = new Nothing<>();

        // Example of using isPresent() method
        System.out.println("Just value is present: " + justValue.isPresent()); // Should output true
        System.out.println("Nothing value is present: " + nothingValue.isPresent()); // Should output false

        // Example of using get() method
        try {
            System.out.println("The value in Just is: " + justValue.get()); // Should output 5
            System.out.println("The value in Nothing is: " + nothingValue.get()); // Should throw NoSuchElementException
        } catch (NoSuchElementException e) {
            System.out.println("Caught exception: " + e.getMessage());
        }

        // Example of using map() method
        Maybe<Double> squaredJustValue = justValue.map(x -> Math.pow(x, 2));
        Maybe<Double> squaredNothingValue = nothingValue.map(x -> Math.pow(x, 2));

        System.out.println("Squared Just value is present: " + squaredJustValue.isPresent()); // Should output true
        System.out.println("Squared Nothing value is present: " + squaredNothingValue.isPresent()); // Should output false

     }
}

