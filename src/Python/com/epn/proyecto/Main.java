package com.epn.proyecto;

import java.util.ArrayList;
import java.util.List;

//Muchachos aqui importan sus clases
// import com.proyecto.algoritmos.MergeSort;
// import com.proyecto.algoritmos.InsertionSort;
// import com.proyecto.datos.DataPrep;

public class Main {

    public static void main(String[] args) {

        System.out.println("Iniciando Pruebas UNIPROCESO en Java (Complejidad Algorítmica)...");

        // 1. TAREA DE Daniel: Cargar los 1M de datos
        // ⚠️ Debes reemplazar 'SimularCarga.cargar()' con tu función:
        // List<Datos> datosOriginales = DataPrep.cargarYPreparar(1000000);

        // --- Placeholder de Carga (Quitar después) ---
        List<Datos> datosOriginales = SimularCarga.cargar();


        System.out.println("\n--- 1. Pruebas de Ordenamiento Uniproceso ---");

        // 1.1 Prueba O(n^2) - Tarea de Daniel
        // ⚠️ Debes crear una instancia de la clase real: new InsertionSort()
        Algoritmo sortLento = new SimularInsertionSort();
        double tiempoLento = Tiempo.medir(sortLento, datosOriginales);
        System.out.printf("Insertion Sort (O(n^2)): %.4f segundos\n", tiempoLento);

        // 1.2 Prueba O(n log n) - Tarea de Ana
        // ⚠️ Debes crear una instancia de la clase real: new MergeSort()
        Algoritmo sortRapido = new SimularMergeSort();
        double tiempoRapido = Tiempo.medir(sortRapido, datosOriginales);
        System.out.printf("Merge Sort (O(n log n)): %.4f segundos\n", tiempoRapido);

        System.out.println("\n--- Pruebas Java Uniproceso Finalizadas ---");
    }

    // --- CLASES AUXILIARES (Quitar después) ---
    // Simulan el código, aqui ustedes peuden probar sus algoritmos
    static class SimularCarga {
        public static List<Datos> cargar() {
            // Retorna una lista vacía para evitar NullPointerException, pero el código real de Jairo la llenará.
            return new ArrayList<>();
        }
    }
    static class SimularInsertionSort implements Algoritmo {
        @Override
        public void ejecutar(List<Datos> datos) { /* Simula el sort O(n^2) */ }
    }
    static class SimularMergeSort implements Algoritmo {
        @Override
        public void ejecutar(List<Datos> datos) { /* Simula el sort O(n log n) */ }
    }
}