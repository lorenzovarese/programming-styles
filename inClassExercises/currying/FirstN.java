import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class FirstN {

    public static void main(String[] args) {
        List<String> strings = List.of("apple", "banana", "cherry", "date", "elderberry");
        int n = 3;

        //Without currying
        //System.out.println(firstN(strings, n));

        System.out.println(curriedFirstN(n).apply(strings));
    }

    public static Function<List<String>, List<String>> curriedFirstN(int n){
        return (List<String> list) -> { return list.stream().limit(n).collect(Collectors.toList()); };
    }

    public static List<String> firstN(List<String> list, int n) {
        return list.stream().limit(n).collect(Collectors.toList());
    }
}
