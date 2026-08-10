
import java.util.function.Consumer;

public class ConsumerTest {
    public static void main(String[] var0) {
         Consumer<Integer> print = (num) -> System.out.print(num);  // System.out::prinnt
         print.accept(10); 
    }
}
