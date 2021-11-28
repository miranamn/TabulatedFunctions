package functions;
import java.io.Serializable;
public class FunctionPoint implements Serializable, Cloneable {
    private double x; //значение точки по абцисс
    private double y; //значение точки по ординате
    public FunctionPoint(double x, double y){ //создает координаты со значениями
        this.x = x;
        this.y = y;
    }
    public FunctionPoint(FunctionPoint point){ //создание точки со значениями
        this.x = point.x;
        this.y = point.y;
    }
    public FunctionPoint(){ //создание точки (0;0)
        this.x = 0;
        this.y = 0;
    }
    public double getX() {
        return x;
    }
    public double getY() {
        return y;
    }
    public void setX(double x) {
        this.x = x;
    }
    public void setY(double y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "(" + x + "; " + y + ")";
    }

    @Override
    public boolean equals(Object o){
        FunctionPoint temp = (FunctionPoint) o;
        if(!(o instanceof FunctionPoint) || x != temp.x || y != temp.y ) return false;
        else return true;
    }

    @Override
    public int hashCode() {
        int result = 31 + (int) (Double.doubleToLongBits(this.x) ^ (Double.doubleToLongBits(this.x) >> 32));
        return 31 * result + (int) (Double.doubleToLongBits(this.y) ^ (Double.doubleToLongBits(this.y) >> 32));
    }

    @Override
    public Object clone() {
        Object o;
        try {
            o = super.clone();
            return o;
        } catch (CloneNotSupportedException e) {
            throw new InternalError();
        }
    }
}