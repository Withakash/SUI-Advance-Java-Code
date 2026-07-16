// Step 4: Shape should not be instantiated
abstract class Shape {
    abstract void draw();

    abstract double area();
}

class Circle extends Shape {
    double r = 5;

    void draw() {
        System.out.println("Drawing Circle");
    }

    double area() {
        return Math.PI * r * r;
    }
}
