import java.util.PriorityQueue;

class Student {
    int id;
    String name;
    int marks;

    Student(int id, String name, int marks) {
        this.id = id;
        this.name = name;
        this.marks = marks;
    }

    @Override
    public String toString() {
        return id + " " + name + " " + marks;
    }
}

public class Test{
    public static void main(String[] args){
        PriorityQueue<Student> pq =
new PriorityQueue<>(

(a,b)->a.name.compareTo(b.name));

pq.offer(new Student(1,"Akash",80));
pq.offer(new Student(2,"Rahul",60));
pq.offer(new Student(3,"Aman",95));

while(!pq.isEmpty())
{
    System.out.println(pq.poll());
}
    }
}