package threads;

import functions.*;

public class Task {
    private Function func;
    private double leftX;
    private double rightX;
    private double step;
    private int count;


    public Task(int task) {
        if (task <= 0) throw new IllegalArgumentException();
        count = task;
    }


    public int getCount() {
        return count;
    }

    public Function getFunc() {
        return func;
    }

    public void setFunc(Function func) {
        this.func = func;
    }

    public double getLeftX() {
        return leftX;
    }

    public void setLeftX(double leftX) {
        this.leftX = leftX;
    }

    public double getRightX() {
        return rightX;
    }

    public void setRightX(double rightX) {
        this.rightX = rightX;
    }

    public double getStep() {
        return step;
    }

    public void setStep(double step) {
        this.step = step;
    }

}
