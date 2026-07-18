// Step 1: Why Generics?
public class WhyGenerics{
 public static void main(String[] args){
  Object[] arr={10,20.0f,'c',"Satyam"};
  // Need casting because Object can store any type.
  int x=(Integer)arr[0];
  System.out.println(x);
 }
}
