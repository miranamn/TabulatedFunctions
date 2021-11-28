package functions.meta;

import functions.Function;

public class Shift implements Function {
    private Function func;
    private double ax;
    private double ay;

    public Shift(Function one, double a, double b){
        this.func = one;
        this.ax = a;
        this.ay = b;
    }
    public double getLeftDomainBorder(){
        return ax + func.getLeftDomainBorder();
    }
    public double getRightDomainBorder(){
        return ax + func.getRightDomainBorder();
    }
    public double getFunctionValue(double x){
        return ay + func.getFunctionValue(x - ax);
    }
}
