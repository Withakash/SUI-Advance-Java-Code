// Step 1: Two separate classes (code duplication)
class Circle {
    String color = "Red";
    int borderWidth = 2;
    double radius = 5;

    void draw() {
        System.out.println("Drawing Circle");
    }

    double area() {
        return Math.PI * radius * radius;
    }
}

class Rectangle {
    String color = "Blue";
    int borderWidth = 3;
    double length = 10, width = 5;

    void draw() {
        System.out.println("Drawing Rectangle");
    }

    double area() {
        return length * width;
    }
}
