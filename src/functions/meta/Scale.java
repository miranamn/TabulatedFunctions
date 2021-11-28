package functions.meta;

import functions.Function;

public class Scale implements Function {

    private Function func;
    private double ax;
    private double ay;

    public Scale(Function one, double a, double b){
        this.func = one;
        this.ax = a;
        this.ay = b;
    }
    public double getLeftDomainBorder(){
        if (ax >= 0)
            return ax * func.getLeftDomainBorder();
        else
            return func.getLeftDomainBorder() / ax;
    }
    public double getRightDomainBorder(){
        if (ax >= 0)
            return ax * func.getRightDomainBorder();
        else
            return func.getRightDomainBorder() / ax;
    }
    public double getFunctionValue(double x){
        if (ay >= 0)
            return ay * func.getFunctionValue(x/ax);
        else
            return func.getFunctionValue(x/ax) / ay;
    }
}
