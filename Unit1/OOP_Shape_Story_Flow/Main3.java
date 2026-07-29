import java.util.*;


interface rotateble{
    void rotate();
}

abstract class Shape {
    private String color;
    private int borderwidth;

    Shape(String c, int bw) {
        color = c;
        borderwidth = bw;
    }

    public String getColor() {
        return color;
    }

    public int getBw() {
        return borderwidth;
    }

    // double area(){};
    // abstract void draw(char c) ;

    abstract void draw();

}

class Circle extends Shape implements rotateble {

    private int radius;

    Circle(int radius, String c, int bw) {
        super(c, bw);
        this.radius = radius;

    }

    double area() {
        return Math.PI * radius * radius;
    }

    // override runtime Polymorphism
    void draw() {
        System.out.print("Drawing Circle");
    }

    // overload
    void draw(char cl) {
        System.out.println("Circle with color " + cl);
    }

    void rotate(){
        System.out.println("ye roatte hota hai");
    }
}

class Ractangle extends Shape {

    int l;
    int w;

    Ractangle(int l, int w, String c, int bw) {
        super(c, bw);
        this.l = l;
        this.w = w;
    }

    double area() {
        return l * w;
    }

    void draw() {
        System.out.print("Drawing Ractangle");
    }
}

public class Main3 {
    public static void main(String[] args) {
        // Shape s = new Shape("blue",2);
        Shape s = new Circle(5, "red", 1);
        // System.out.println(s.draw());
        // s.draw('c');

    }
}
