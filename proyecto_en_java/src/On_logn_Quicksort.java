import java.util.Collections;
import java.util.List;

/**
 * 
 * CLASE: On_logn_Quicksort 
 * =======================================================================
 * Implementación "manual" del algoritmo Quicksort (O(n log n)).
 * * Objetivo: Ordenar la lista de 'NormalizedUtility' usando el valor
 * de la fórmula (utilidadNormalizada).
 */
public class On_logn_Quicksort { 

    /**
     * Este es el "botón" principal que el Main.java va a presionar.
     * @param list La lista completa de objetos que mandó el DataReader.
     */
    public static void sort(List<NormalizedUtility> list) { 
        
        // ¡Paso Clave! Barajamos la lista (Collections.shuffle).
        // Esto evita el "peor caso" de Quicksort (que es O(n^2))
        // y asegura que siempre corra rápido (O(n log n)).
        Collections.shuffle(list);

        // Ahora sí, llamamos al algoritmo real con la lista barajada.
        // 0 es el primer índice y (list.size() - 1) es el último.
        quicksort(list, 0, list.size() - 1);
    }

    /**
     * El cerebro de Quicksort. Es un método "recursivo" (se llama a sí mismo)
     * para aplicar la estrategia de "divide y vencerás".
     *
     * @param list La lista que estamos ordenando.
     * @param bajo El índice de inicio de la pila (ej. 0).
     * @param alto El índice de fin de la pila (ej. 999999).
     */
    private static void quicksort(List<NormalizedUtility> list, int bajo, int alto) {
        
        // Condición para detenerse: si 'bajo' y 'alto' se cruzan,
        // significa que la pila tiene 1 o 0 elementos, así que está ordenada.
        if (bajo < alto) {
            
            // 1. "Dividir":
            // Llama a 'particion' para mover las cosas.
            // 'pi' es el lugar donde quedó el pivote (que ya está ordenado).
            int pi = particion(list, bajo, alto);
            
            // 2. "Vencer" (Recursión):
            // Llama a esta misma función para la pila de la izquierda
            quicksort(list, bajo, pi - 1);
            // y para la pila de la derecha.
            quicksort(list, pi + 1, alto);
        }
    }

    /**
     * Mueve los elementos en la lista
     * Agarra un "pivote" (un valor de referencia) y mueve todo lo que es
     * menor a su izquierda y todo lo que es mayor a su derecha.
     *
     * @return El nuevo índice donde quedó el pivote.
     */
    private static int particion(List<NormalizedUtility> list, int bajo, int alto) {
        
        // 1. Elegimos un "pivote". Tomamos el objeto del final de la pila.
        NormalizedUtility pivoteObjeto = list.get(alto);
        
        // Sacamos el número (de la fórmula) que vamos a comparar.
        double pivoteValor = pivoteObjeto.getUtilidadNormalizada();
        
        // 'i' es un marcador para el "muro" de los elementos pequeños.
        // Empieza "fuera" de la lista.
        int i = (bajo - 1); 

        // 2. Recorremos la pila (sin tocar el pivote del final)
        for (int j = bajo; j < alto; j++) {
            
            // ¡LA COMPARACIÓN CLAVE!
            // ¿Este objeto (j) es más pequeño que el valor de nuestro pivote?
            if (list.get(j).getUtilidadNormalizada() < pivoteValor) { 
                i++; // Sí, lo es. Movemos el muro de los pequeños...
                swap(list, i, j); // ...e intercambiamos el objeto (j) a esa zona (i).
            }
        }

        // 3. Al final, ponemos el pivote en su lugar correcto (justo después
        //    del último "pequeño" que encontramos)
        swap(list, i + 1, alto);
        
        // Devolvemos la posición final del pivote.
        return i + 1;
    }

    /**
     * Una función simple para intercambiar dos objetos en la lista.
     * No crea listas nuevas, solo cambia posiciones (es "in-place").
     */
    private static void swap(List<NormalizedUtility> list, int i, int j) {
        NormalizedUtility temp = list.get(i);
        list.set(i, list.get(j));
        list.set(j, temp);
    }
}