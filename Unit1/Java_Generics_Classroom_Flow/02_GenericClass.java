// Step 2: Generic Class
class Box<K,V>{K k;V v;void set(K key,V value){k=key;v=value;}void get(){System.out.println("Key: "+k+", Value: "+v);}}
public class GenericClass{public static void main(String[] args){Box<Integer,String>b=new Box<>();b.set(1,"Aka");b.get();}}
