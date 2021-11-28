package functions;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Iterator;

public class ArrayTabulatedFunction implements TabulatedFunction, Serializable, Cloneable{

    private FunctionPoint points[]; //массив с точками
    private int n; //количество точек

    public ArrayTabulatedFunction(FunctionPoint[] point){
        if (point.length <= 2)
            throw new IllegalArgumentException(); //исключение
        else{
            FunctionPoint StartPoint = point[0];
            for(int i = 1; i < point.length; i++){
                if(point[i].getX() < StartPoint.getX()) throw new IllegalArgumentException();
                else StartPoint = point[i];
            }
        }
        this.points = point;
        this.n = point.length;
    }

    public ArrayTabulatedFunction(double leftX, double rightX, int pointsCount){
        if (pointsCount <= 2 || leftX >= rightX)
            throw new IllegalArgumentException(); //исключение
        n = pointsCount;
        points = new FunctionPoint[pointsCount * 2];
        double interval = (rightX - leftX) / (pointsCount - 1);//расстояние между точками одинаковое
        for (int i = 0; i < pointsCount; i++)
            points[i] = new FunctionPoint(leftX + i * interval, 0);
    }
    public ArrayTabulatedFunction(double leftX, double rightX, double[] values){
        if (values.length <= 2 || leftX >= rightX)
            throw new IllegalArgumentException(); //исключение
        n = values.length;
        points = new FunctionPoint[values.length * 2];
        double interval = (rightX - leftX) / (values.length - 1);//расстояние между точками одинаковое
        for (int i = 0; i < values.length; i++)
            points[i] = new FunctionPoint(leftX + i * interval, values[i]);
    }
    public double getLeftDomainBorder(){
        return points[0].getX();
    }
    public double getRightDomainBorder(){
        return points[n - 1].getX();
    }
    public double getFunctionValue(double x){
        if (x < getLeftDomainBorder() || x > getRightDomainBorder()) {
            return Double.NaN;
        }
        else {
            int i = 0;
            while ((!(x >= points[i].getX() && x <= points[i + 1].getX())) || !(i < n)) ++i;
            if(points[i].getX() == x)
                return points[i].getY();
            if(points[i + 1].getX() == x)
                return points[i + 1].getY();
            //y=kx+b
            double ff, k, b;
            k = (points[i + 1].getY() - points[i].getY()) / (points[i + 1].getX() - points[i].getX());
            b = points[i].getY() - k * points[i].getX();
            return ff = k * x + b;
        }
    }
    public int getPointsCount(){
        return n;
    }
    public FunctionPoint getPoint(int index){
        if (index < 0 || index >= points.length)
            throw new FunctionPointIndexOutOfBoundsException();
        return points[index];
    }
    public double getPointX(int index){
        if (index < 0 || index >= points.length)
            throw new FunctionPointIndexOutOfBoundsException();
        return points[index].getX();
    }
    public double getPointY(int index){
        if (index < 0 || index >= points.length)
            throw new FunctionPointIndexOutOfBoundsException();
        return points[index].getY();
    }
    public void setPointX(int index, double x) throws InappropriateFunctionPointException{
        if ((x < points[index - 1].getX()) || (x > points[index + 1].getX()))
            throw new InappropriateFunctionPointException();

        if (index < 0 || index >= points.length)
            throw new FunctionPointIndexOutOfBoundsException();

        if((x > points[index - 1].getX()) && (x < points[index + 1].getX()) ){//проверка на интервал
            FunctionPoint pointToo = new FunctionPoint(x,points[index].getY());
            points[index] = pointToo;
        }
    }
    public void setPointY(int index, double y){
        if (index < 0 || index >= points.length)
            throw new FunctionPointIndexOutOfBoundsException();

        FunctionPoint pointToo = new FunctionPoint(points[index].getX(), y);
        points[index] = pointToo;
    }
    public void setPoint(int index, FunctionPoint point) throws InappropriateFunctionPointException{
        if ((point.getX() < points[index - 1].getX()) || (point.getX() > points[index + 1].getX()))
            throw new InappropriateFunctionPointException();

        if (index < 0 || index >= points.length)
            throw new FunctionPointIndexOutOfBoundsException();

        if((point.getX() > points[index - 1].getX()) && (point.getX() < points[index + 1].getX()))//проверка на принадлежность интервалу
            points[index] = point;
    }
    public void deletePoint(int index){
        if(n < 3)
            throw new IllegalStateException();
        if (index < 0 || index >= points.length)
            throw new FunctionPointIndexOutOfBoundsException();
        System.arraycopy(points, index + 1, points, index, n - index - 1);
        --n;
    }
    public void addPoint(FunctionPoint point) throws InappropriateFunctionPointException{

        if (!(n < points.length)) {
            FunctionPoint[] tempFpArray = new FunctionPoint[n * 2];
            System.arraycopy(points, 0, tempFpArray, 0, n);
            points = tempFpArray;
        }
        int i = 0;
        while ((i < n) && (points[i].getX() < point.getX()))
            ++i;
        if ((i < n) && (point.getX() == points[i].getX()))
            throw new InappropriateFunctionPointException();
         else {
            System.arraycopy(points, i, points, i + 1, n - i);
            points[i] = point;
            ++n;
        }
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{");
        for(int i = 0; i < getPointsCount(); i++){
            stringBuilder.append("( ").append(getPointX(i)).append("; ").append(this.getPointY(i)).append(")");
        }
        return stringBuilder + "}";
    }

    @Override
    public int hashCode() {
        return 31 * (31 + Arrays.deepHashCode(points)) + getPointsCount();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof TabulatedFunction)) return false;
        if (o instanceof ArrayTabulatedFunction) {
            ArrayTabulatedFunction temp1 = (ArrayTabulatedFunction) o;
            for (int i = 0; i < getPointsCount(); i++)
                if (!getPoint(i).equals(temp1.getPoint(i))) return false;
        }
        else {
            TabulatedFunction temp2 = (TabulatedFunction) o;
            for (int i = 0; i < getPointsCount(); i++)
                if (!getPoint(i).equals(temp2.getPoint(i))) return false;
        }
        return true;
    }

    @Override
    public Object clone() {
        try {
            ArrayTabulatedFunction temp = (ArrayTabulatedFunction)super.clone();
            temp.points = points.clone();
            for(int i = 0; i < getPointsCount(); i++)
                temp.points[i] = (FunctionPoint)points[i].clone();
            return temp;
        } catch (CloneNotSupportedException e) {
            throw new InternalError();
        }
    }

    @Override
    public Iterator<FunctionPoint> iterator() {
        return new getIterator();
    }

    private class getIterator implements Iterator<FunctionPoint> {
        int index;

        @Override
        public boolean hasNext() {
            return index < getPointsCount();
        }

        @Override
        public FunctionPoint next() {
            return points[index++];
        }
    }

    public static class ArrayTabulatedFunctionFactory implements TabulatedFunctionFactory{
        public TabulatedFunction createTabulatedFunction(double leftX, double rightX, double[] values){
            return new ArrayTabulatedFunction(leftX, rightX, values);
        }

        public TabulatedFunction createTabulatedFunction(double leftX, double rightX, int pointsCount){
            return new ArrayTabulatedFunction(leftX, rightX, pointsCount);
        }

        public TabulatedFunction createTabulatedFunction(FunctionPoint[] point){
            return new ArrayTabulatedFunction(point);
        }

    }
}
