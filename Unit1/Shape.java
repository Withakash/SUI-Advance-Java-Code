import java.util.*;

class Shape{
    private String color;
    private int borderwidth;

    Shape(String c , int bw){
        color = c;
        borderwidth = bw;
    }

    public String getColor(){
       return color;
    }

    public int getBw(){
        return borderwidth;
    }

    // double area(){};
    void draw(){};

}
class Circle extends Shape{
    
    private int radius;

    Circle(int radius, String c , int bw){
        super(c, bw);
        this.radius = radius;
        
    }
    double area(){
       return Math.PI * radius * radius;
     }

     //override Dynamic Polymorphism
    void draw(){
        System.out.print("Drawing Circle");
    }
}

class Ractangle extends Shape {

    int l;
    int w;

    Ractangle(int l , int w , String c , int bw){
        super(c,bw);
       this.l = l;
       this. w = w;
    }


    double area() {
        return l*w;
    }

    void draw() {
        System.out.print("Drawing Ractangle");
    }
}

public class Main{
    public static void main(String[] args){
        Shape s = new Shape("blue",2);
        System.out.println(s.getColor());
        
    }
}
