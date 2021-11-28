package threads;
import functions.Functions;

public class Integrator extends Thread{
    private final Task tasks;
    private final Semaphore semaphore;
    private boolean run = false;

    public Integrator(Task task1, Semaphore semaphore1) {
        tasks = task1;
        semaphore = semaphore1;
    }

    public void run() {
        run = true;
        for (int i = 0; i < tasks.getCount() && run; ++i) {
            try {
                semaphore.beginRead();
                System.out.println("новый Результат leftX = " + tasks.getLeftX() + " rightX = " + tasks.getRightX()
                        + " step = " + tasks.getStep() + " integral = "
                        + Functions.integral(tasks.getFunc(), tasks.getLeftX(), tasks.getRightX(), tasks.getStep()));
                semaphore.endRead();
            } catch (InterruptedException e) {
                System.out.println("Интегратор спал. Зачем разбудили");
            }
        }
    }

    public void interrupt() {
        super.interrupt();
        run = false;
    }
}
