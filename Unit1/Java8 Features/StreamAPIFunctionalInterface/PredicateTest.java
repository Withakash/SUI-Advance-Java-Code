import java.util.function.Predicate;


public class PredicateTest {
    public static void main(String[] str){
        // Predicate<Integer> p1 =  num -> num % 2 == 0;

        // System.out.println(p1.test(20));

        // Predicate<String> isAPresent = name -> name.contains("A");

        // boolean a = isAPresent.test("Akash");
        // System.out.println(a);

        // Predicate<String> isValidAt = mail -> mail.contains("@");
        // System.out.println(isValidAt.test("abc123"));
        // Predicate<String> isValidDot = mail -> mail.contains(".");

        // Predicate<String> check = isValidAt.and(isValidDot);
        // System.out.println(check.test("abc@123.com"));

        // System.out.print(isValidAt.test("Abc@123") && isValidDot.test("Abc@123"));



        Predicate<Integer> even = n -> n % 2 == 0;

        Predicate<Integer> odd = even.negate();

        
        System.out.println(odd.test(20));

}
}
