package threads;
import functions.basic.Log;

public class Generator extends Thread{
    private final Task task;
    private final Semaphore semaphore;
    private boolean run = false;

    public Generator(Task task1, Semaphore semaphore1) {
        task = task1;
        semaphore = semaphore1;
    }

    public void run() {
        run = true;
        for (int i = 0; i < task.getCount() && run; i++) {
            try {
                task.setFunc(new Log(1 + (Math.random() * 9)));
                semaphore.beginWrite();
                task.setLeftX(Math.random() * 100);
                task.setRightX(Math.random() * 100 + 100);
                task.setStep(Math.random());
                System.out.println("новое Условие leftX = " + task.getLeftX() + " rightX = "
                        + task.getRightX() + " step = " + task.getStep());
                semaphore.endWrite();
            } catch (InterruptedException e) {
                System.out.println("Генератор спал. Зачем разбудили");
            }
        }
    }

    public void interrupt() {
        super.interrupt();
        run = false;
    }
}
