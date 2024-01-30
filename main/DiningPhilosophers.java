import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DiningPhilosophers {
    public static void main(String[] args) {

        int numPhilosophers = 5;
        ExecutorService executorService = Executors.newFixedThreadPool(numPhilosophers);
        Fork[] forks = new Fork[numPhilosophers];
        Philosopher[] philosophers = new Philosopher[numPhilosophers];

        for (int i = 0; i < numPhilosophers; i++) {
            forks[i] = new Fork();
        }

        // Create philosophers with different priorities
        for (int i = 0; i < numPhilosophers; i++) {
            philosophers[i] = new Philosopher(i + 1, (i + 1) * 10, forks[i], forks[(i + 1) % numPhilosophers]);
            executorService.execute(philosophers[i]);
        }

        executorService.shutdown();
    }

}
