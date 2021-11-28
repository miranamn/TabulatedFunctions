package functions;

import javax.xml.crypto.NoSuchMechanismException;
import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public abstract class TabulatedFunctions {
    private static TabulatedFunctionFactory factory = new ArrayTabulatedFunction.ArrayTabulatedFunctionFactory();

    public static void setTabulatedFunctionFactory(TabulatedFunctionFactory factory){
        TabulatedFunctions.factory = factory;
    }

    public TabulatedFunction createTabulatedFunction(double leftX, double rightX, double[] values){
        return factory.createTabulatedFunction(leftX, rightX, values);
    }

    public TabulatedFunction createTabulatedFunction(double leftX, double rightX, int pointsCount){
        return factory.createTabulatedFunction(leftX, rightX, pointsCount);
    }

    public TabulatedFunction createTabulatedFunction(FunctionPoint[] point){
        return factory.createTabulatedFunction(point);
    }

    public static TabulatedFunction createTabulatedFunction(double leftX, double rightX, double[] values,
                                                     Class<? extends TabulatedFunction> tclass){
        Constructor[] constructors = tclass.getConstructors();
        for (Constructor constructor : constructors) {
            Class[] classes = constructor.getParameterTypes();
            if (classes.length == 3 && classes[0].equals(Double.TYPE) && classes[1].equals(Double.TYPE) && classes[2].equals(values.getClass())) {
                try {
                    return (TabulatedFunction) constructor.newInstance(leftX, rightX, values);
                } catch (Exception e) {
                    throw new IllegalArgumentException(e);
                }
            }
        }
        throw new NoSuchMechanismException();
    }

    public static TabulatedFunction createTabulatedFunction(double leftX, double rightX, int pointsCount,
                                                            Class<? extends TabulatedFunction> tclass){
        Constructor[] constructors = tclass.getConstructors();
        for (Constructor constructor : constructors) {
            Class[] classes = constructor.getParameterTypes();
            if (classes.length == 3 && classes[0].equals(Double.TYPE) && classes[1].equals(Double.TYPE) && classes[2].equals(Integer.TYPE)) {
                try {
                    return (TabulatedFunction) constructor.newInstance(leftX, rightX, pointsCount);
                } catch (IllegalAccessException | IllegalArgumentException | InstantiationException | InvocationTargetException e) {
                    throw new IllegalArgumentException(e);
                }
            }
        }
        throw new NoSuchMechanismException();
    }

    public static TabulatedFunction createTabulatedFunction(FunctionPoint[] point, Class<? extends TabulatedFunction> tclass){
        Constructor[] constructors = tclass.getConstructors();
        for (Constructor constructor : constructors) {
            Class[] classes = constructor.getParameterTypes();
            if (classes[0].equals(point.getClass())) {
                try {
                    return (TabulatedFunction) constructor.newInstance(new Object[]{point});
                } catch (Exception e) {
                    throw new IllegalArgumentException(e);
                }
            }
        }
        throw new NoSuchMechanismException();
    }

    public static TabulatedFunction tabulate( Function function, double leftX, double rightX, int pointsCount,
                                             Class<? extends TabulatedFunction> tClass) {
        if (leftX < function.getLeftDomainBorder() || rightX > function.getRightDomainBorder()) throw new IllegalArgumentException();
        double[] values = new double[pointsCount];
        double TEMP = leftX;
        for (int i = 0; i < pointsCount; i++) {
            values[i] = function.getFunctionValue(TEMP);
            TEMP += (rightX - leftX) / (pointsCount - 1);
        }
        return createTabulatedFunction(leftX, rightX, values, tClass);
    }

    // метод получающий функцию и возвращающий её табулированный аналог на заданном отрезке с заданным количеством точек
    public static TabulatedFunction tabulate(Function function, double leftX, double rightX, int pointsCount){
        if (leftX < function.getLeftDomainBorder() || rightX > function.getRightDomainBorder())
            throw new IllegalArgumentException();
        FunctionPoint[] points = new FunctionPoint[pointsCount];
        points[0] = new FunctionPoint(leftX, function.getFunctionValue(leftX));
        double interval = (rightX - leftX) / (pointsCount - 1);
        for (int i = 1; i < pointsCount; i++)
            points[i] = new FunctionPoint(points[i - 1].getX() + interval, function.getFunctionValue(points[i - 1].getX() + interval));
        return factory.createTabulatedFunction(points);
    }

    public static void outputTabulatedFunction(TabulatedFunction function, OutputStream out) throws IOException{
    //Метод вывода табулированной функции в байтовый поток
        int num = function.getPointsCount();
        DataOutputStream of = new DataOutputStream(out);
        of.writeInt(num); // записываем количество точек
        for (int i = 0; i < num; i++){
            of.writeDouble(function.getPointX(i));
            of.writeDouble(function.getPointY(i));
        }
    }

    public static TabulatedFunction inputTabulatedFunction(InputStream in) throws IOException{
    //Метод ввода табулированной функции из байтового потока
        DataInputStream of = new DataInputStream(in);
        int num = of.readInt(); //считали количество точек
        FunctionPoint points[] = new FunctionPoint[num];
        for (int i = 0; i < num; i++)
            points[i] = new FunctionPoint(of.readDouble(), of.readDouble());
        return factory.createTabulatedFunction(points);
    }

    public static void writeTabulatedFunction(TabulatedFunction function, Writer out) throws IOException{
    //Метод записи табулированной функции в символьный поток
        int num = function.getPointsCount();
        PrintWriter writer = new PrintWriter(out);
        writer.println(num);
        for (int i = 0; i < num; i++) {
            writer.println(function.getPointX(i));
            writer.println(function.getPointY(i));
        }
    }

    public static TabulatedFunction readTabulatedFunction(Reader in) throws IOException{
    //Метод чтения табулированной функции из символьного потока
        StreamTokenizer of = new StreamTokenizer(in); //создали объект потока
        of.nextToken();
        int num = (int) of.nval;
        FunctionPoint[] points = new FunctionPoint[num];
        double x, y;
        for (int i = 0; i < num; i++) {
            of.nextToken();
            x = of.nval;
            of.nextToken();
            y = of.nval;
            points[i] = new FunctionPoint(x, y);
        }
        return factory.createTabulatedFunction(points);
    }
}
