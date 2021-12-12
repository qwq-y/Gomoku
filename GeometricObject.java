import java.util.Date;

abstract class GeometricObject {
    private String color;
    private boolean filled;
    private Date dateCreated;

    protected GeometricObject() {
        color = "red";
        filled = true;
    }
    protected GeometricObject(String color, boolean filled) {
        this.color = color;
        this.filled = filled;
    }

    public String getColor() {
        return color;
    }
    public void setColor(String color) {
        this.color = color;
    }
    public boolean isFilled() {
        return filled;
    }
    public void setFilled(boolean filled) {
        this.filled = filled;
    }

    public Date getDateCreated() {
        return getDateCreated();
    }

    public String toString() {
        return "Color is " + color + " and " + filled; // 这里没有return我帮你补上了
    }

    public abstract double getArea();

    public abstract double getPerimeter();
}

//import java.util.Date;
//
//public abstract class GeometricObject {
//    private String color;
//    private boolean filled;
//    private Date dateCreated;
//
//    protected GeometricObject() {
//        color = "red";
//        filled = true;
//    }
//    protected GeometricObject(String color, boolean filled) {
//        this.color = color;
//        this.filled = filled;
//    }
//
//    public String getColor() {
//        return color;
//    }
//    public void setColor(String color) {
//        this.color = color;
//    }
//    public boolean isFilled() {
//        return filled;
//    }
//    public void setFilled(boolean filled) {
//        this.filled = filled;
//    }
//
//    public Date getDateCreated() {
//        return getDateCreated();
//    }
//
//    public String toString() {
//
//    }
//
//    public abstract double getArea() {
//        double area = 0;
//        if (this instanceof Circle) {
//            area = Math.PI * Math.pow(((Circle) this).getRadius(), 2);
//        }
//        if (this instanceof Rectangle) {
//            area = ((Rectangle) this).getHeight() * ((Rectangle) this).getHeight();
//        }
//        return area;
//    }
//
//    public abstract double getPerimeter() {
//        double perimeter = 0;
//        if (this instanceof Circle) {
//            perimeter = Math.PI * ((Circle) this).getDiameter();
//        }
//        if (this instanceof Rectangle) {
//            perimeter = 2 * (((Rectangle) this).getHeight() + ((Rectangle) this).getHeight());
//        }
//        return perimeter;
//    }
//}
