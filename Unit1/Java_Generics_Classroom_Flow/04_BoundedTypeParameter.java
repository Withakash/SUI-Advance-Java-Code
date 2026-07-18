// Step 4: Bounded Type Parameter
class Calculator<T extends Number>{
 T a,b; Calculator(T a,T b){this.a=a;this.b=b;}
 double add(){return a.doubleValue()+b.doubleValue();}
 public static void main(String[]args){Calculator<Integer> c=new Calculator<>(10,20);System.out.println(c.add());}
}
