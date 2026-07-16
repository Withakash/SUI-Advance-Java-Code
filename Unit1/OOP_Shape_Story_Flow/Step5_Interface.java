// Step 5: Optional abilities using interface
interface Rotatable {
    void rotate();
}

abstract class Shape {
    abstract void draw();
}

class Circle extends Shape implements Rotatable {
    void draw() {
        System.out.println("Drawing Circle");
    }

    public void rotate() {
        System.out.println("Rotating Circle");
    }
}
