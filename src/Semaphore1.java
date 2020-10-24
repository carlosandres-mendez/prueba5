import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * @author Benjamin Winterberg
 */
public class Semaphore1 {

    private static final int NUM_INCREMENTS = 10000;

    private static Semaphore semaphore = new Semaphore(10);

    private static int count = 0;

    public static void main(String[] args) {
        testIncrement();
    }

    private static void testIncrement() {
        ExecutorService executor = Executors.newCachedThreadPool();
        
   //     for(int i=0;i<NUM_INCREMENTS;i++){
   //         executor.submit(Semaphore1::increment);
   //     }
        
        IntStream.range(0, NUM_INCREMENTS)
                .forEach(i -> executor.submit(Semaphore1::increment));

        ConcurrentUtils.stop(executor);

        System.out.println("Increment: " + count);
    }

    private static void increment() {
        boolean permit = false;
        try {
            System.out.println(Thread.currentThread().getName());
            permit = semaphore.tryAcquire(2);
            count++;
        }
        catch (Exception e) {
            throw new RuntimeException("could not increment");
        }
        finally {
            if (permit) {
                semaphore.release();
            }
        }
    }

}