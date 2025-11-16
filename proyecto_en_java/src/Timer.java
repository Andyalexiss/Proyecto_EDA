package proyectoEda;
/**
 * Clase para la medición precisa de tiempo en nanosegundos (Uniproceso).
 * Implementado por Andy.
 */
public class Timer {

    private long startTime;
    private long endTime;

    /**
     * Inicia el contador de tiempo.
     */
    public void start() {
        this.startTime = System.nanoTime();
    }

    /**
     * Detiene el contador de tiempo.
     */
    public void stop() {
        this.endTime = System.nanoTime();
    }

    /**
     * Devuelve el tiempo transcurrido en SEGUNDOS.
     * @return Duración de la ejecución en segundos.
     */
    public double getDurationSeconds() {
        // Convierte nanosegundos a segundos (1,000,000,000 ns = 1 s)
        return (double) (this.endTime - this.startTime) / 1_000_000_000.0;
    }
}