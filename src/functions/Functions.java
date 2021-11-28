package functions;

import functions.meta.*;

public abstract class Functions {

    public static Function shift(Function f, double shiftX, double shiftY){
        return new Shift(f, shiftX, shiftY);
    }
    public static Function scale(Function f, double scaleX, double scaleY){
        return new Scale(f, scaleX, scaleY);
    }
    public static Function power(Function f, double power){
        return new Power(f, power);
    }
    public static Function sum(Function f1, Function f2){
        return new Sum(f1, f2);
    }
    public static Function mult(Function f1, Function f2){
        return new Mult(f1, f2);
    }
    public static Function composition(Function f1, Function f2){
        return new Composition(f1, f2);
    }

    public static double integral(Function func, double leftX, double rightX, double step) {
        if (leftX < func.getLeftDomainBorder() || rightX > func.getRightDomainBorder()) throw new IllegalArgumentException();
        double res = 0;
        double temp = leftX;
        while (temp < rightX) {
            res += (func.getFunctionValue(temp) + func.getFunctionValue(temp + step)) * step / 2;
            temp += step;
        }
        res += (func.getFunctionValue(rightX) + func.getFunctionValue(temp - step)) * (rightX - temp + step) / 2;
        return res;
    }


}
