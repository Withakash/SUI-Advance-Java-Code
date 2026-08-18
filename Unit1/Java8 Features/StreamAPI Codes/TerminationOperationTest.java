import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class TerminationOperationTest {
    public static void main(String[] arg) {
             List<String> names = Arrays.asList( "Vijay", "Aftab", "Raja", "Zabu", "Kanchan", "Satyam");
            // List<Integer> e = Arrays.asList();

            //  int c = (int) names.stream().filter(s -> s.contains("j") || s.contains("J")).count();
            //  System.out.print(c);

            // Optional<Integer> test = e.stream().max((a,b) -> a.compareTo(b));

            // List<Integer> num = Arrays.asList(1,2,3,4,5,6,7,8,9);
            // boolean check = num.stream().anyMatch(n -> n == 12);
            // // res.ifPresent(System.out::print);
            // System.out.print(check);


            // List<Integer> num = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
            // boolean check = num.stream().allMatch(n -> n > 0);
            // System.out.print(check);


            // List<Integer> num = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
            // boolean check = num.stream().noneMatch(n -> n < 0);
            // System.out.print(check);

            // List<Integer> num = Arrays.asList(1, 3, 5, 9);
            // boolean check = num.stream().allMatch(n -> n % 2 != 0);
            // System.out.print(check);

            List<Integer> ls = Arrays.asList(1,2,3,4,5,6,7,8,9);

            // List<Integer> res = ls.stream()
            //                         .filter(x -> x % 2 == 0)
            //                         .map(x -> x*x)
            //                         .collect(Collectors.toList());


            //                         System.out.print(res);


            // Set<Integer> res = ls.stream()
            //         .filter(x -> x % 2 == 0)
            //         .map(x -> x * x)
            //         .collect(Collectors.toSet());

            // System.out.print(res);



            // String s = names.stream()
            //         .collect(
            //             Collectors.joining
            //             ("---",
            //              "{",
            //              "}"
            //         ));


            //         System.out.println(s);



            
    }
}