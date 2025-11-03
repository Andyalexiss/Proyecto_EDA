import multiprocessing as mp
from typing import List, Callable, Any, Tuple
import time


# --- Función Auxiliar para los Procesos ---
def _worker_process(func: Callable, chunk_data: List[Any], q: mp.Queue):
    """
    Función ejecutada por cada proceso hijo.
    Mide el tiempo parcial de su tarea y coloca el resultado en la cola.
    """
    inicio = time.perf_counter()
    resultado = func(chunk_data)
    fin = time.perf_counter()

    # Envía el tiempo y el resultado parcial a la cola
    q.put((fin - inicio, resultado))


# --- Manager Principal ---
def execute_parallel(func: Callable, data: List[Any], num_processes: int = None) -> Tuple[float, List[Any]]:
    """
    Ejecuta una función sobre el dataset dividiendo el trabajo en múltiples procesos.

    :param func: La función a paralelizar (por ejemplo, el algoritmo O(n log n)).
    :param data: El dataset completo (lista de datos).
    :param num_processes: Número de procesos a usar. Por defecto, usa todos los núcleos.
    :return: Una tupla con (tiempo_total_maximo, resultado_combinado).
    """
    if num_processes is None:
        num_processes = mp.cpu_count()  # Usa todos los núcleos disponibles

    if num_processes <= 0:
        raise ValueError("El número de procesos debe ser al menos 1.")

    print(f"-> Iniciando ejecución paralela con {num_processes} procesos...")

    # 1. División del Dataset
    chunk_size = len(data) // num_processes
    chunks = [data[i:i + chunk_size] for i in range(0, len(data), chunk_size)]

    # Asegura que el último chunk tome los elementos restantes
    if sum(len(c) for c in chunks) != len(data) and chunks:
        chunks[-1].extend(data[sum(len(c) for c in chunks):])

    # 2. Configuración y Ejecución
    processes: List[mp.Process] = []
    results_queue = mp.Queue()

    for chunk in chunks:
        p = mp.Process(target=_worker_process, args=(func, chunk, results_queue))
        processes.append(p)
        p.start()

    # 3. Esperar y Recolectar Resultados
    for p in processes:
        p.join()

    # 4. Procesar Resultados
    tiempos = []
    partial_results = []

    while not results_queue.empty():
        time_partial, result_partial = results_queue.get()
        tiempos.append(time_partial)
        partial_results.append(result_partial)

    # El tiempo total es el tiempo que tardó el proceso más lento (cuello de botella)
    time_max = max(tiempos) if tiempos else 0.0

    # Combina los resultados parciales (asume que los resultados son listas planas)
    final_result = [item for sublist in partial_results for item in sublist] if partial_results else []

    return time_max, final_result