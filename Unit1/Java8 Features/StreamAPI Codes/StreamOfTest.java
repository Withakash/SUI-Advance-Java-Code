import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Stream;

public class StreamOfTest {
    public static void main(String[] arg){
        Stream<Integer> st = Stream.of(1,2,3,4,5,6);
        st.forEach(System.out::print);


        Stream<String> stream = Stream.empty();
        stream = Stream.of("Akash","Vikash","jay");
    }
}
