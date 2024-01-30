import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Philosopher implements Runnable{

    private final int id;
    private final int priority;
    private final Fork leftFork;
    private final Fork rightFork;
    private final Lock pickUpLock = new ReentrantLock();

    public Philosopher(int id, int priority, Fork leftFork, Fork rightFork) {
        this.id = id;
        this.priority = priority;
        this.leftFork = leftFork;
        this.rightFork = rightFork;
    }

    @Override
    public void run() {
        try {
            while (true) {
                think();
                pickUpForks();
                eat();
                putDownForks();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void think() throws InterruptedException {
        System.out.println("Philosopher " + id + " is thinking.");
        Thread.sleep((long) (Math.random() * 1000));
    }

    private void pickUpForks() throws InterruptedException {
        pickUpLock.lock();
        try {
            while (!leftFork.pickUp(priority) || !rightFork.pickUp(priority)) {
                // If couldn't pick up both forks, release the lock and try again
                pickUpLock.unlock();
                Thread.sleep((long) (Math.random() * 1000));
                pickUpLock.lock();
            }
            System.out.println("Philosopher " + id + " is eating.");
        } finally {
            pickUpLock.unlock();
        }
    }

    private void eat() throws InterruptedException {
        Thread.sleep((long) (Math.random() * 1000));
    }

    private void putDownForks() {
        leftFork.putDown();
        rightFork.putDown();
        System.out.println("Philosopher " + id + " has finished eating and put down forks.");
    }

}
