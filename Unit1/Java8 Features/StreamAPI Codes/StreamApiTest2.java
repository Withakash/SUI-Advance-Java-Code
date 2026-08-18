import java.util.Arrays;

public class StreamApiTest2 {
    public static void main(String[] arg){
        int[] numbers = { 10, 20, 3, 40 };
          Arrays.stream(numbers).filter(x -> x % 2 == 0).forEach(x -> System.out.print(x + " "));

        //   System.out.println("Ak");
    }
}
