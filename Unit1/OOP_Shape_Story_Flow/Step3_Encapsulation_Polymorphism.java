// Step 3: Encapsulation + Overloading + Overriding
class Shape {
    private String color;
    private int borderWidth;

    Shape(String c, int bw) {
        color = c;
        borderWidth = bw;
    }

    public String getColor() {
        return color;
    }

    void draw() {
        System.out.println("Drawing Shape");
    }

    void draw(String c) {
        System.out.println("Drawing " + c);
    }
}

class Circle extends Shape {
    Circle(String c, int bw) {
        super(c, bw);
    }

    @Override
    void draw() {
        System.out.println("Drawing Circle");
    }
}

class Main {
    public static void main(String[] args) {
        Shape s = new Circle("Red", 2);
        s.draw(); // runtime polymorphism
    }
}
