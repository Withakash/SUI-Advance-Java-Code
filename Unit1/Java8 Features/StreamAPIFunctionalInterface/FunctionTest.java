import java.util.function.Function;
public class FunctionTest {
    public static void main(String[] str){

        // Function<Integer,Boolean> sqr = num ->  num == 0;
        // System.out.println(sqr.apply(0));

        Function<Integer,Integer> pf = sal -> sal + 3600;

        Function<Integer,Integer> bonus = sal -> sal + 50000;

        System.out.println(
            pf.andThen(bonus).apply(100000)
        );


    }

}
