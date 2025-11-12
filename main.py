
"""
Script Maestro para la ejecución y medición de tiempo de los algoritmos 
O(n log n) en MODO MULTIPROCESO (Python).
"""

import copy
import time
from typing import List
import os

# Importaciones de componentes del proyecto
from utils import cargar_datos, NormalizedUtility 
from onlogn_quicksort import sort as daniel_quicksort_sort 
from onlogn_sort import sort as ana_mergesort_sort      
from results_writer import ResultsWriter                 
from multiprocessing_module.multiprocess_manager import execute_parallel, Timer 



RUTA_DATASET = "datosEda.csv"
NOMBRE_EQUIPO = "Equipo_Ryzen_Daniel" 
RESULTADOS_CSV = "resultados_multiproceso.csv"
NUM_PROCESOS = os.cpu_count() // 2 if os.cpu_count() else 4 


def ejecutar_pruebas_multiproceso():
  
    
    # 0. Inicializar el archivo de resultados
    ResultsWriter.write_header(RESULTADOS_CSV)
    print("--- PRUEBAS DE TIEMPO MULTIPROCESO INICIADAS ---")
    print(f"Número de Procesos (Workers) configurados: {NUM_PROCESOS}")

    # 1. CARGA DE DATOS
    datos_para_prueba: List[NormalizedUtility]
    try:
        datos_para_prueba = cargar_datos(RUTA_DATASET)
        print("Dataset cargado y listo. Tamaño:", len(datos_para_prueba))
    except Exception as e:
        print(f"Error fatal al cargar los datos: {e}")
        return

    # -------------------------------------------------------------------
    # --- PRUEBA 1: ALGORITMO O(n log n) de Ana (MergeSort MULTIPROCESO) ---
    # -------------------------------------------------------------------
    
    datos_mergesort = copy.copy(datos_para_prueba) 

    print("\n[PRUEBA 1] Ejecutando MergeSort de Ana en MODO MULTIPROCESO...")
    
    # El Manager ejecuta el MergeSort de Ana sobre los chunks
    tiempo_multiproceso_max_ana, datos_ana_parcialmente_ordenados = execute_parallel(
        func=ana_mergesort_sort, 
        data=datos_mergesort, 
        num_processes=NUM_PROCESOS
    )
    
    print(f"[MAIN] Tiempo Multiproceso (MergeSort Workers): {tiempo_multiproceso_max_ana:.4f} segundos")

    # Fusión Final (MergeSort Serial Global)
    timer_final_ana = Timer()
    print("[MAIN] Realizando MergeSort final (Merge Serial) para consolidar...")
    
    timer_final_ana.start()
    ana_mergesort_sort(datos_ana_parcialmente_ordenados)
    timer_final_ana.stop()

    tiempo_merge_final_ana = timer_final_ana.get_duration_seconds()
    tiempo_registro_ana = tiempo_multiproceso_max_ana + tiempo_merge_final_ana
    
    print(f"Tiempo Total (MergeSort Multiproceso): {tiempo_registro_ana:.4f} segundos")
    
    # Registrar el resultado
    ResultsWriter.write_result(
        "OnLogN_Sort (MergeSort Multiproceso)", "O(n log n)", tiempo_registro_ana, NOMBRE_EQUIPO, RESULTADOS_CSV
    )
    
    # -------------------------------------------------------------------
    # --- PRUEBA 2: ALGORITMO O(n log n) Quicksort de Daniel (MULTIPROCESO) ---
    # -------------------------------------------------------------------
    
    datos_quicksort = copy.copy(datos_para_prueba) 

    print("\n[PRUEBA 2] Ejecutando QuickSort de Daniel en MODO MULTIPROCESO...")
    
    # El Manager ejecuta tu QuickSort sobre los chunks
    tiempo_multiproceso_max_daniel, datos_daniel_parcialmente_ordenados = execute_parallel(
        func=daniel_quicksort_sort, 
        data=datos_quicksort, 
        num_processes=NUM_PROCESOS
    )
    
    print(f"[MAIN] Tiempo Multiproceso (QuickSort Workers): {tiempo_multiproceso_max_daniel:.4f} segundos")

    
    timer_final_daniel = Timer()
    print("[MAIN] Realizando QuickSort final (Merge Serial) para consolidar...")
    
    timer_final_daniel.start()
    daniel_quicksort_sort(datos_daniel_parcialmente_ordenados)
    timer_final_daniel.stop()

    tiempo_merge_final_daniel = timer_final_daniel.get_duration_seconds()
    tiempo_registro_daniel = tiempo_multiproceso_max_daniel + tiempo_merge_final_daniel
    
    print(f"Tiempo Total (QuickSort Multiproceso): {tiempo_registro_daniel:.4f} segundos")
    
    # Registrar el resultado
    ResultsWriter.write_result(
        "On_logn_Quicksort (Multiproceso)", "O(n log n)", tiempo_registro_daniel, NOMBRE_EQUIPO, RESULTADOS_CSV
    )
    
    print("\n--- PRUEBAS DE TIEMPO MULTIPROCESO FINALIZADAS ---")


if __name__ == "__main__":
    ejecutar_pruebas_multiproceso()