import java.util.*;

class ArrayListTest{

    
    public static void main(String[] args){
         LinkedList<String> songs = new LinkedList<>();
        //  List<String> test = new ArrayList<>();
        //  test.add("Kesariya");
         songs.addFirst("Kesariya");
         songs.addFirst("Tum mere");
        //  songs.remove("Kesariya");
        //  boolean contain = songs.containsAll(test);
        // System.out.print(songs + " " + contain);

        // songs.set(1, "Baarisen");
        songs.get(2);
        System.out.print(songs);
    }
}