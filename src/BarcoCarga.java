
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.stream.IntStream;
import java.util.Random;

/**
 * @author Alejandro
 */
public class BarcoCarga {

    private static Semaphore semaphore = new Semaphore(10);//Semaforo con 10 permisos

//    
//    int ran = random.nextInt(10 - 1) + 1;
    public static void main(String[] args) {

        int random = numeroRandom();//Ponemos la variable desde el main para que no se inicialice multiples veces desde el metodo.

        ExecutorService executor = Executors.newFixedThreadPool(random);//los montacargas generados al azar
        System.out.println("El numero random es: "+random);//Para poder ver cuantos montacargas usaremos

        for (int i = 0; i < random; i++) {//veces al azar que repite el proceso.
            executor.submit(BarcoCarga::cargarBarco);//ejecuta el metodo el mismo numero de montacargas
        }

//        IntStream.range(1, 11)
//                .forEach(i -> executor.submit(BarcoCarga::cargarBarco));
        ConcurrentUtils.stop(executor);
    }

    private static void cargarBarco() {
        boolean permit = false;
        try {
            int toneladasRandom = toneladasRandom();
            permit = semaphore.tryAcquire(toneladasRandom);//solicita n(1-4) permisos o en este caso, las toneladas a subir al barco
            if (permit) {
                String puerto = new String[]{"Este", "Oeste"}[(int) (Math.random() * 2)];//Genera una seleccion entre "Este" y "Oeste"
                System.out.println("Se esta cargando " + toneladasRandom + " tonelada(s) desde el puerto " + puerto + "...");
                //System.out.println(numeroRandom());
                ConcurrentUtils.sleep(1);//espera 1 segundo
            } else {
                System.out.println("Ya no hay espacio disponible para los otros montacargas.");
                //System.out.println(numeroRandom());
            }
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
//        finally {
//            if (permit) {
//               //semaphore.release();
//            }
//        }
    }

    public static int numeroRandom() {//genera un valor entre 10 y 1
        Random random = new Random();

        int randomNum = random.nextInt((10 - 1) + 1) + 1;

        return randomNum;
    }

    public static int toneladasRandom() {//genera un valor entre 4 y 1
        Random random = new Random();

        int randomNum = random.nextInt((4 - 1) + 1) + 1;

        return randomNum;
    }

}
