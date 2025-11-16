import os
import csv 
from typing import List

# --- Configuración de la Ruta ---
# El archivo se guardará en la carpeta 'output'
FILE_PATH = "output/tiempos_uniproceso.csv"
HEADER = "Algoritmo,Complejidad,Tiempo_Segundos,Equipo_CPU\n"

def write_header() -> None:
    """
    Escribe el encabezado en el archivo CSV.
    Crea la carpeta 'output/' si no existe.
    """
    # Crea la carpeta 'output' si no existe (solución al error de ruta)
    output_dir = os.path.dirname(FILE_PATH)
    if output_dir and not os.path.exists(output_dir):
        os.makedirs(output_dir)

    # Escribe el encabezado solo si el archivo no existe o está vacío
    if not os.path.exists(FILE_PATH) or os.path.getsize(FILE_PATH) == 0:
        with open(FILE_PATH, mode='w', encoding='utf-8') as f:
            f.write(HEADER)

def write_result(algoritmo: str, complejidad: str, tiempo_segundos: float, equipo_info: str) -> None:
    """
    Añade una línea de resultado al archivo CSV.
    
    @param algoritmo: Nombre del algoritmo.
    @param complejidad: Complejidad (Ej: O(n^2)).
    @param tiempo_segundos: Tiempo de ejecución en segundos.
    @param equipo_info: Nombre del equipo.
    """
    # Usamos 'a' para el modo 'append' (añadir)
    try:
        with open(FILE_PATH, mode='a', encoding='utf-8') as f:
            # Formateamos los datos como una sola línea CSV
            line = f"{algoritmo},{complejidad},{tiempo_segundos:.6f},{equipo_info}\n"
            f.write(line)
            print(f"-> Resultado guardado: {algoritmo}") # Confirmación para el usuario
    except IOError as e:
        # Esto captura el error de "ruta no encontrada" si no se pudo crear la carpeta
        print(f"Error al escribir el resultado: {e}")