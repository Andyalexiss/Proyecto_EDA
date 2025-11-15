package proyectoEda;
import java.util.Collections;
import java.util.List;

/**
 * =======================================================================
 * CLASE: On_logn_Quicksort (REEMPLAZO POR INSERTION SORT)
 * =======================================================================
 * Implementación del algoritmo de ordenamiento por Inserción (O(n²)).
 * 
 */
public class On2Insertion_sort{ 

    /**
     * Metodo de entrada publico llamado por el Main.
     * Inicia el proceso de ordenamiento O(n²).
     *
     * @param list La lista de objetos NormalizedUtility a ordenar.
     */
    public static void sort(List<NormalizedUtility> list) { 
        
       //Barajamos la lista con el util collections 
        // Esto garantiza que el Insertion Sort se ejecute en su PEOR CASO (O(n²))
        // y que el tiempo sea grande para tener unos resultados solidos para las tablas de comparacion
        Collections.shuffle(list);

        // Llama al metodo principal de Insertion Sort.
        insertionSort(list);
    }

    /**
     * Implementación manual del algoritmo de ordenamiento por Inserción (O(n²)).
     *
     * @param list La lista a ordenar.
     */
    private static void insertionSort(List<NormalizedUtility> list) {
        
        // Bucle principal: Recorre la lista de izquierda a derecha,
       
        for (int i = 1; i < list.size(); i++) {
            
            // Elemento actual que se intentará insertar
            NormalizedUtility actual = list.get(i);
            double valorActual = actual.getUtilidadNormalizada();
            int j = i - 1; 
            
            // Bucle interno: Desplaza los elementos en la sub-lista ordenada
            // que son mayores que el valorActual
            while (j >= 0 && list.get(j).getUtilidadNormalizada() > valorActual) {
                
                // Desplazar el elemento a la derecha
                list.set(j + 1, list.get(j)); 
                j--;
            }
            
            // Insertar el elemento 'actual' en la posición correcta 
            list.set(j + 1, actual);
        }
    }
}