
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.IntStream;

/**
 *
 * @author carlosandres.mendez
 */
class MontaCarga implements Runnable {

    public static int pesoBarco;
    public static Semaphore mutex;
    
    private final int pesoMontacarga;

    public MontaCarga(Semaphore mutex, int pesoBarco) {
        MontaCarga.mutex = mutex;
        MontaCarga.pesoBarco = pesoBarco;
        
        Random random = new Random();
        pesoMontacarga = random.nextInt(10) + 1;
    }

    @Override
    public void run() {
        try {
            while (true) {
                if(solicitarAcceso()){
                    /* hacer wait(mutex) o down(mutex) en Semaforo */
                    //entrar al barco
                    System.out.println("Thread "
                            + Thread.currentThread().getId()
                            + " is running "+ pesoMontacarga);
                }
                salirBarco();
                /* hacer signal(mutex) o up(mutex); en Semaforo */
            }

        } catch (Exception e) {
            // Throwing an exception 
            System.out.println("Exception is caught");
        }
    }

    private boolean solicitarAcceso() {
        boolean resultado = false;
        try {
            mutex.acquire();
            /* region crítica inicia aqui */
            if (10 >= (pesoBarco + pesoMontacarga)) {
                pesoBarco = pesoBarco + pesoMontacarga;
                resultado=true;
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(MontaCarga.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultado;
    }

    private void salirBarco() {
        /* region crítica termina aqui */
        mutex.release();
    }

}

public class BarcoSemaforoBinario {

    public static void main(String[] args) {
        Semaphore mutex = new Semaphore(1);
        int pesoBarco = 0;
        int n = 8; // Number of threads
        
     /*   MontaCarga [] object = new MontaCarga[n];
        
        for (int i = 0; i < n; i++) {
            object[i] = new MontaCarga(mutex, pesoBarco);
        }
        
        for (int i = 0; i < n; i++) {
            object[i].start();
        }
     */   
     
        ExecutorService executor = Executors.newFixedThreadPool(n);
        IntStream.range(0, n)
                .forEach(i -> executor.execute(new MontaCarga(mutex, pesoBarco))); 
        
    }
}
