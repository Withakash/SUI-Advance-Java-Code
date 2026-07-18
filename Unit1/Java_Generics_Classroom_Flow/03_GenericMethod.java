// Step 3: Generic Method
public class GenericFunction{
 static <T> void print(T s){System.out.println(s);} 
 public static void main(String[] args){print(100);print("Hello");print(new int[]{10,20});}
}
