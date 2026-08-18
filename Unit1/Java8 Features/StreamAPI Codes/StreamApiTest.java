import java.util.Arrays;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

public class StreamApiTest{
    public static void main(String[] arg){
        Set<Integer> numbers =  new HashSet<>(Arrays.asList(10, 20, 30, 40, 50));
        // numbers.addAll();

        numbers.stream()
                .filter(x -> x > 25)
                .forEach(x -> System.out.print(x + " "));

    }
}