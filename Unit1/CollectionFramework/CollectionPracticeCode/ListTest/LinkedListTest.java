import java.util.*;
public class LinkedListTest {
    public static void main(String[] args){
        // ArrayList<Integer> al = new ArrayList<>();
        // al.add(5);
        // al.add(2);
        // al.add(7);
        // ArrayList<Integer> ref  = new ArrayList<>();

        // ref.add(20);
        // ref.add(30);
        // ref.add(2);
        // ref.add(5);
        // boolean x = ref.containsAll(al);

        // System.out.print(x);


        LinkedList<String> n = new LinkedList<>();
        n.add("Aka");
        n.add("Vka");
        n.add("Kri");

        // LinkedList<String> n1 = new LinkedList<>();
        // n1.add("Aka");
        // n1.add("Vka");

        // // n.addAll();
        // n.retainAll(n1);
        // System.out.println(n);

        n.add("Vkk");
        n.add("Nikk");


        // for(String x : n){
        //     System.out.println(x);
        // }

        // Iterator<String> ite = n.iterator();
        // while(ite.hasNext()){
        //     System.out.print(ite.next());
        // }

        // ListIterator<String> ite = n.listIterator();

        // while(ite.hasNext()){
        // System.out.println(ite.next());
        // }

        // System.out.println("Reverse wala");

        // while (ite.hasPrevious()) {
        //     System.out.println(ite.previous());
        // }


        n.forEach(ele -> System.out.println(ele));

    }
}












