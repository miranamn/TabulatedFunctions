package threads;

public class Semaphore {
    private boolean write = true;

    public synchronized void beginRead() throws InterruptedException {
        while (write) wait();
    }

    public synchronized void endRead() {
        write = true;
        notifyAll();
    }

    public synchronized void beginWrite() throws InterruptedException {
        while (!write) wait();
    }

    public synchronized void endWrite() {
        write = false;
        notifyAll();
    }
}
