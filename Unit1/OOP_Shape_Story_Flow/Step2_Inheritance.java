// Step 2: Remove duplication using inheritance
class Shape {
    String color;
    int borderWidth;

    Shape(String c, int bw) {
        color = c;
        borderWidth = bw;
    }

    void showDetails() {
        System.out.println(color + " " + borderWidth);
    }
}

class Circle extends Shape {
    double radius;

    Circle(double r, String c, int bw) {
        super(c, bw);
        radius = r;
    }

    void draw() {
        System.out.println("Drawing Circle");
    }
}

class Rectangle extends Shape {
    double l, w;

    Rectangle(double l, double w, String c, int bw) {
        super(c, bw);
        this.l = l;
        this.w = w;
    }

    void draw() {
        System.out.println("Drawing Rectangle");
    }
}
