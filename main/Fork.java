import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Fork {

    private final Lock lock = new ReentrantLock();
    private boolean isAvailable = true;
    private int holderPriority = Integer.MAX_VALUE;

    public boolean pickUp(int priority) {
        if (lock.tryLock()) {
            if (isAvailable || priority < holderPriority) {
                isAvailable = false;
                holderPriority = priority;
                lock.unlock();
                return true;
            } else {
                lock.unlock();
                return false;
            }
        }
        return false;
    }

    public void putDown() {
        lock.lock();
        isAvailable = true;
        holderPriority = Integer.MAX_VALUE;
        lock.unlock();
    }

}
