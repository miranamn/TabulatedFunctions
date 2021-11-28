package threads;

import functions.basic.Log;

public class SimpleGenerator implements Runnable{
    private final Task task;

    public SimpleGenerator(Task task1) {
        task = task1;
    }

    @Override
    public void run() {
        for (int i = 0; i < task.getCount(); i++) {
            synchronized (task) {
                task.setFunc(new Log(1 + (Math.random() * 9)));
                task.setLeftX(Math.random() * 100);
                task.setRightX(Math.random() * 100 + 100);
                task.setStep(Math.random());
                System.out.println("Условие leftX = " + task.getLeftX() + " rightX = "
                        + task.getRightX() + " step = " + task.getStep());
            }
        }
    }
}
