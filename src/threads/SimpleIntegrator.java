package threads;

import functions.Functions;

public class SimpleIntegrator implements Runnable{
    private final Task task;

    public SimpleIntegrator(Task task1) {
        task = task1;
    }
    @Override
    public void run() {
        for (int i = 0; i < task.getCount(); ++i) {
            if (task.getFunc() == null) continue;
            synchronized (task) {
                System.out.println("Результат leftX = " + task.getLeftX() + " rightX = " + task.getRightX() +
                        " step = " + task.getStep() + " integral = "
                        + Functions.integral(task.getFunc(), task.getLeftX(), task.getRightX(), task.getStep()));
            }
        }
    }
}
