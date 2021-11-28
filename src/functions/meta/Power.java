package functions.meta;

import functions.Function;

public class Power implements Function {
    private Function first;
    private double pow;

    public Power(Function one, double p){
        this.first = one;
        this.pow = p;
    }
    public double getLeftDomainBorder(){
        return first.getLeftDomainBorder();
    }
    public double getRightDomainBorder(){
        return first.getRightDomainBorder();
    }
    public double getFunctionValue(double x){
        return Math.pow(first.getFunctionValue(x), pow);
    }
}
