import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

class Student{
    String name;
    String branch;
    double marks;

     Student(String name,String branch, double marks){
        this.name = name;
        this.branch = branch;
        this.marks = marks;
     }

     public String toString(){
        return "[ " + name + ", " + branch + ", " + marks + " ]";

         
     }



}




public class GroupByTest {
    public static void main(String[] arg) {
        List<Student> studes = new ArrayList<>();
         studes.add(new Student("Kareja", "IT", 50.7));
         studes.add(new Student("Akash", "CST", 15.17));
         studes.add(new Student("Raja", "CST", 22.7));
         studes.add(new Student("Prakash", "IT",65.7));
         studes.add(new Student("Amit", "Civil", 100));


        //  Map<String,List<Student>> res = studes.stream()
        //           .collect(Collectors.groupingBy(b -> b.branch));


        // Map<String,Double> details = studes.stream()
        //                                     .collect(Collectors.toMap(s -> s.name, m -> m.marks));
 
        //         System.out.println(details);



        Map<String,Long> res = studes.stream()
        .collect(Collectors.groupingBy(b -> b.branch,Collectors.counting()));


        System.out.print(res);

                  
        
    }
}





// {
//     Civil=[[ Amit, Civil, 100.0 ]],
//      CST=[[ Akash, CST, 15.17 ], [ Raja, CST, 22.7 ]], 
//      IT=[[ Kareja, IT, 50.7 ], [ Prakash, IT, 65.7 ]]
// }