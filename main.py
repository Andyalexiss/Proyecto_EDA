import time
import multiprocessing as mp
import sys
from typing import List, Callable, Any
from copy import deepcopy 


# Modulos del proyecto (Archivos dentro de subcarpetas)
from results.read_and_clean import read_and_clean
from algorithms.NormalizedUtility import NormalizedUtility
from algorithms.sort_nlogn import sort as mergesort_worker     
from algorithms.insertion_on2 import sort as insertionsort_worker 
from multiprocessing_module.timer import Timer


# --- Configuracion del Experimento ---
RUTA_DATASET = "datosEda.csv" 
NOMBRE_EQUIPO = "Equipo_ryzen 7 8845hs_Multiproceso_Final"

# =======================================================================
# --- LOGICA DE FUSION (REDUCE) ---
# Funcion que une dos listas ya ordenadas, manteniendo el orden.
# =======================================================================
def merge_listas_ordenadas(izquierda: List[NormalizedUtility], derecha: List[NormalizedUtility]) -> List[NormalizedUtility]:
    """Fusiona dos listas ordenadas por utilidadNormalizada en una sola."""
    resultado = []
    i = j = 0
    while i < len(izquierda) and j < len(derecha):
        if izquierda[i].utilidad_normalizada <= derecha[j].utilidad_normalizada:
            resultado.append(izquierda[i])
            i += 1
        else:
            resultado.append(derecha[j])
            j += 1
    resultado.extend(izquierda[i:])
    resultado.extend(derecha[j:])
    return resultado

# =======================================================================
# --- SCRIPT MAESTRO (MAIN) ---
# =======================================================================
def main():
    """
    Funcion principal que orquesta la comparacion de O(n²) contra O(n log n)
    en un entorno de multiples nucleos (Multiproceso).
    """
    
   
    print("--- PRUEBAS DE TIEMPO MULTIPROCESO INICIADAS ---")

    # 1. CARGA DE DATOS (DataReader)
    try:
        datosParaPrueba = read_and_clean.leer_y_limpiar_dataset(RUTA_DATASET)
    except Exception as e:
        print(f"Error fatal al cargar los datos: {e}", file=sys.stderr)
        return

    num_nucleos = mp.cpu_count()
    print(f"Configurando Pool de {num_nucleos} nucleos...")
    
    # 2. PREPARACION DE CHUNKS (Division del dataset)
    chunk_size = len(datosParaPrueba) // num_nucleos
    chunks_base = [datosParaPrueba[i:i + chunk_size] for i in range(0, len(datosParaPrueba), chunk_size)]
    
    timer_total = Timer()

    # -----------------------------------------------------------------------
    # --- PRUEBA 1: ALGORITMO O(n log n) (Mergesort - El Rapido) ---
    # -----------------------------------------------------------------------
    print(f"\n[PRUEBA 1] Ordenando con Mergesort Paralelo (O(n log n))...")
    timer_total.start()
    
    with mp.Pool(processes=num_nucleos) as pool:
        # FASE MAP: Cada nucleo ordena su pedazo con el Mergesort
        chunks_ordenados_ana = pool.map(mergesort_worker, chunks_base)
    
    # FASE REDUCE: Fusionar y aplicar filtro de robustez
    valid_chunks_ana = [c for c in chunks_ordenados_ana if isinstance(c, list)]
    
    if valid_chunks_ana:
        lista_final_nlogn = valid_chunks_ana[0]
        for i in range(1, len(valid_chunks_ana)):
            lista_final_nlogn = merge_listas_ordenadas(lista_final_nlogn, valid_chunks_ana[i])
            
    timer_total.stop()
    tiempo_nlogn = timer_total.get_duration()
    print(f"-> Tiempo O(n log n) TOTAL: {tiempo_nlogn:.4f} segundos")
    

    # -----------------------------------------------------------------------
    # --- PRUEBA 2:  ALGORITMO LENTO (O(n²) - Insertion Sort) ---
    # -----------------------------------------------------------------------
    
    # Preparamos los chunks de nuevo
    datos_insertion = datosParaPrueba.copy()
    chunks_insertion = [datos_insertion[i:i + chunk_size] for i in range(0, len(datos_insertion), chunk_size)]
    
    print(f"\n[PRUEBA 2] Ordenando con Insertion Sort (O(n²))...")
    timer_total.start()
    
    with mp.Pool(processes=num_nucleos) as pool:
        # FASE MAP: Cada nucleo ordena su pedazo del Insertion Sort
        chunks_ordenados_tuyos = pool.map(insertionsort_worker, chunks_insertion)
    

    valid_chunks_tuyos = [c for c in chunks_ordenados_tuyos if isinstance(c, list)]
    
    if valid_chunks_tuyos:
        lista_final_n2 = valid_chunks_tuyos[0]
        for i in range(1, len(valid_chunks_tuyos)):
            lista_final_n2 = merge_listas_ordenadas(lista_final_n2, valid_chunks_tuyos[i])
            
    timer_total.stop()
    tiempo_n2 = timer_total.get_duration()
    print(f"-> Tiempo O(n²) TOTAL: {tiempo_n2:.4f} segundos")
   

  


if __name__ == "__main__":
    main()