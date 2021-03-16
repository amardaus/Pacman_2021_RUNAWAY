package sample;

public class Position {
    private double x;
    private double y;
    Position(double x, double y){
        this.x = x;
        this.y = y;
    }

    void setX(double newX){
        this.x = newX;
    }
    void setY(double newY){
        this.y = newY;
    }
    double getX(){
        return x;
    }
    double getY(){
        return y;
    }
}
