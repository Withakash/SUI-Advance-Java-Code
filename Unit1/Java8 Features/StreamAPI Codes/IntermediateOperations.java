import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

public class IntermediateOperations {
    public static void main(String[] arg) {

        // List<String> names =
        // Arrays.asList("Kay","Kijay","Aftab","Raja","Babu","Kanchan");
        // names.stream()
        // .filter(s -> s.startsWith("K"))
        // .map(s -> s.toUpperCase())
        // .forEach(s -> System.out.print(s + " "));

        // List<Integer> salary = Arrays.asList(100000,18000,12000,55000,27000);
        // salary.stream()
        // .filter(x -> x > 50000)
        // .map(x -> x * .18)
        // .forEach(System.out::println);

        // List<List<Integer>> matrix = Arrays.asList(
        // Arrays.asList(1, 2, 3),
        // Arrays.asList(4, 5, 6),
        // Arrays.asList(7, 8, 9));

        // matrix.stream()
        // .flatMap(list ->list.stream())
        // .forEach(System.out::println);

        List<String> names = Arrays.asList("Jay", "Jay", "Vijay", "Aftab", "Raja", "Babu", "Kanchan", "Satyam");

        // names.stream().distinct().forEach(x -> System.out.println(x));
        // names.stream().sorted().forEach(x -> System.out.println(x));
        // names.stream().distinct().sorted(Comparator.reverseOrder()).filter(s ->
        // s.contains("j")).forEach(x -> System.out.println(x));

        // names.stream()
        //         .distinct()
        //         .sorted((a,b) -> b.compareTo(a))
        //         .filter(s -> s.contains("j"))
        //         .forEach(x -> System.out.println(x));



        // names.stream()
        // .limit(3)
        // .skip(3)
        // .forEach(x -> System.out.print(x + " "));


        List<Integer> num = Arrays.asList(1,2,3,4,5,6,7,8,9);

        // num.stream()
        //          .peek(n-> System.out.println(n + " Peek 1"))
        //          .filter(x -> x % 2 == 0)
        //          .peek(n -> System.out.println(n + " Peek 2"));
                //  .forEach(x -> System.out.println(x + " Even"));


        num.stream()

                  .limit(1)
                  .peek(x -> System.out.println(x + " peek"))
                  .forEach(x -> System.out.println());

        

    }
}

// filter()map()flatMap()distinct()sorted()limit()skip()peek()
