
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class FilosofosMain {

    public static int tenedoresEnLaMesa;

    private static Semaphore semaphore = new Semaphore(1);

    public static void main(String[] args) {
        tenedoresEnLaMesa = 5;
        test1();
        test2();
        test3();
        test4();
        test5();
    }

    private static void test5() {
        Runnable runnable = () -> {
            try {
                System.out.println("Foo " + Thread.currentThread().getName());
                //TimeUnit.SECONDS.sleep(1);
                System.out.println("Bar " + Thread.currentThread().getName());
                filosofo(5);
            } catch (Exception e) {
                e.printStackTrace();
            }
        };

        Thread thread = new Thread(runnable);
        thread.start();
    }

    private static void test4() {
        Runnable runnable = () -> {
            try {
                System.out.println("Foo " + Thread.currentThread().getName());
                //TimeUnit.SECONDS.sleep(1);
                System.out.println("Bar " + Thread.currentThread().getName());
                filosofo(4);
            } catch (Exception e) {
                e.printStackTrace();
            }
        };

        Thread thread = new Thread(runnable);
        thread.start();
    }

    private static void test3() {
        Runnable runnable = () -> {
            try {
                System.out.println("Foo " + Thread.currentThread().getName());
                //TimeUnit.SECONDS.sleep(1);
                filosofo(3);
            } catch (Exception e) {
                e.printStackTrace();
            }
        };

        Thread thread = new Thread(runnable);
        thread.start();
    }

    private static void test2() {
        Runnable runnable = () -> {
            try {
                System.out.println("Foo " + Thread.currentThread().getName());
                Thread.sleep(1000);
                System.out.println("Bar " + Thread.currentThread().getName());
                filosofo(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        Thread thread = new Thread(runnable);
        thread.start();
    }

    private static void test1() {
        Runnable runnable = () -> {
            String threadName = Thread.currentThread().getName();
            filosofo(1);
        };

        //runnable.run();

        Thread thread = new Thread(runnable);
        thread.start();

        System.out.println("Done!");

    }

    private static void filosofo(int i) /* i: número de filósofo, de 0 a 4 */ {
        while (true) {

            boolean permit = false;
            try {
                permit = semaphore.tryAcquire(5, TimeUnit.SECONDS);

                if (permit) {
                    System.out.println("Filosofo " + i + " esta pensando ");
                    System.out.println("Filosofo " + i + " esta tomando el tenedor izquierdo");
                    /* toma tenedor izquierdo */
                    System.out.println("Filosofo " + ((i + 1) % 5) + " esta tomando el tenedor derecho");
                    /* toma tenedor derecho; % es el operador módulo */
                    System.out.println("Filosofo " + i + " esta comiendo ");
                    /* come espagueti */
                      System.out.println("Filosofo " + i + " esta poniendo el tenedor izquierdo de vuelta en la mesa ");
                    /* pone tenedor izquierdo de vuelta en la mesa */
                     System.out.println("Filosofo " + ((i + 1) % 5) + " esta poniendo el tenedor derecho de vuelta en la mesa");
                }

            } catch (InterruptedException e) {
                throw new RuntimeException("could not increment");
            } finally {
                if (permit) {
                    semaphore.release();
                }
            }

        }
    }

}
