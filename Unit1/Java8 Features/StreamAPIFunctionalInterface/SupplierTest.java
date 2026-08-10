import java.util.function.Supplier;

public class SupplierTest {
    public static void main(String[] str) {

      Supplier<Integer> random = () -> (int) (Math.random()*1000);


      System.out.println(random.get());


    }

}
