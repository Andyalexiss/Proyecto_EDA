package com.epn.proyecto;

import java.util.ArrayList;
import java.util.List;

public class Tiempo {

    /**
     * Mide el tiempo de ejecución de un algoritmo en modo UNIPROCESO.
     * * @param algoritmo La implementación del Algoritmo (MergeSort o InsertionSort).
     * @param datos La lista de datos original (desordenada).
     * @return El tiempo de ejecución en segundos (double).
     */
    public static double medir(Algoritmo algoritmo, List<Datos> datos) {

        // ⚠️ Muchachos es escencial COPIAR la lista para que la lista original quede intacta
        // y pueda ser usada por la siguiente prueba.
        List<Datos> datosCopia = new ArrayList<>(datos);

        // Inicia la medición con la máxima precisión en nanosegundos
        long inicio = System.nanoTime();

        // Llama al método 'ejecutar' del algoritmo
        algoritmo.ejecutar(datosCopia);

        long fin = System.nanoTime();

        // Convierte la diferencia de nanosegundos a segundos
        return (fin - inicio) / 1_000_000_000.0;
    }

    // El método medirParalelo() NO se incluye aquí, ya que se hará en Python. El cual esta en la otra carpeta
}