package functions.meta;
import functions.Function;

public class Composition implements Function {
    private Function first;
    private Function second;

    public Composition(Function one, Function two){
        this.first = one;
        this.second = two;
    }
    public double getLeftDomainBorder(){
        return first.getLeftDomainBorder();
    }
    public double getRightDomainBorder(){
        return first.getRightDomainBorder();
    }
    public double getFunctionValue(double x){
        return first.getFunctionValue(second.getFunctionValue(x));
    }
}
