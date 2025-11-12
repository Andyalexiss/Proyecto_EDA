package proyectoEda;
import java.util.List;
import java.util.Random;

/**
 * =======================================================================
 * CLASE: On_logn_Quicksort 
 * =======================================================================
 * Implementación del Quicksort de Tres Vías, optimizado para manejar
 * grandes volúmenes de valores duplicados (utilidadNormalizada) sin
 * causar un StackOverflowError.
 */
public class On_logn_Quicksort { 

    private static final Random GENERADOR_ALEATORIO = new Random();

    /**
     * Método principal llamado por el Main. Inicia el ordenamiento.
     * @param lista La lista de objetos NormalizedUtility a ordenar.
     */
    public static void sort(List<NormalizedUtility> lista) { 
        ordenar(lista, 0, lista.size() - 1);
    }

    /**
     * El método recursivo principal del Quicksort de Tres Vías.
     * Divide la lista en tres zonas: Menores, Iguales y Mayores al pivote.
     */
    private static void ordenar(List<NormalizedUtility> lista, int indiceInicio, int indiceFin) {
        
        // Condición base: si ya no hay elementos o solo hay uno, se detiene.
        if (indiceInicio >= indiceFin) return; 

        // 1. Pivote Aleatorio: Se elige un pivote al azar para estabilidad.
        int indiceAleatorio = GENERADOR_ALEATORIO.nextInt(indiceFin - indiceInicio + 1) + indiceInicio;
        // Se coloca el pivote en el índice de inicio
        intercambiar(lista, indiceAleatorio, indiceInicio); 

        // El pivote se selecciona del índice 'indiceInicio'
        double valorPivote = lista.get(indiceInicio).getUtilidadNormalizada();
        
        // 2. TRES PUNTEROS:
        int indiceMenor = indiceInicio;    // lt (Límite superior de la zona <)
        int indiceMayor = indiceFin;       // gt (Límite inferior de la zona >)
        int i = indiceInicio + 1;          // Puntero de escaneo

        // 3. Partición de Tres Vías
        while (i <= indiceMayor) {
            double valorActual = lista.get(i).getUtilidadNormalizada();
            
            if (valorActual < valorPivote) {
                // El valor es menor: Se mueve a la zona de "menores" (lt)
                intercambiar(lista, indiceMenor++, i++);
            } else if (valorActual > valorPivote) {
                // El valor es mayor: Se mueve a la zona de "mayores" (gt)
                intercambiar(lista, i, indiceMayor--); 
            } else {
                // El valor es igual: Se queda en la zona central. Solo avanza el escáner.
                i++;
            }
        }
        
        // 4. Llamada Recursiva
        // Se llama recursivamente SOLO a las zonas < y > (excluyendo la zona =)
        ordenar(lista, indiceInicio, indiceMenor - 1);  // Zona < valorPivote
        ordenar(lista, indiceMayor + 1, indiceFin);     // Zona > valorPivote
    }

    /**
     * Método de utilidad para intercambiar dos elementos en la lista (in-place).
     */
    private static void intercambiar(List<NormalizedUtility> lista, int i, int j) {
        NormalizedUtility temporal = lista.get(i);
        lista.set(i, lista.get(j));
        lista.set(j, temporal);
    }
}