
import java.util.Stack;
public class StackTest {
    public static void main(String[] str){
        Stack<Integer> st = new Stack<>();
        st.add(20);
        st.push(30);
        // st.pop();
        // st.remove(st.size()-1);
        int q = st.peek();
        int p = st.get(st.size()-1);
        st.set(2, 100);

        System.out.print(st);


    }
}
