package functions.meta;

import functions.Function;

public class Sum implements Function {
    private Function first;
    private Function second;

    public Sum(Function one, Function two){
        this.first = one;
        this.second = two;
    }
    public double getLeftDomainBorder(){
        return Math.max(first.getLeftDomainBorder(), second.getLeftDomainBorder());
    }
    public double getRightDomainBorder(){
        return Math.min(first.getRightDomainBorder(), second.getRightDomainBorder());
    }
    public double getFunctionValue(double x){
        return first.getFunctionValue(x) + second.getFunctionValue(x);
    }
}
