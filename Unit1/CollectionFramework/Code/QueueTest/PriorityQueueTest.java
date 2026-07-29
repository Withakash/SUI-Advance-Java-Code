import java.util.Collections;
import java.util.PriorityQueue;
public class PriorityQueueTest{
    public static void main(String[] a){
       PriorityQueue<Integer> pq = new PriorityQueue<>();
       pq.add(10);
       pq.add(5);
       pq.add(7);
       pq.add(2);
       pq.add(1);
       pq.add(-10);

       while(!pq.isEmpty())
         System.out.println(pq.poll());
    }
}
