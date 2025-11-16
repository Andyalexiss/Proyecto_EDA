import csv
from typing import List, Any
# Asume que NormalizedUtility está en la carpeta 'algorithms'
from algorithms.NormalizedUtility import NormalizedUtility 

class read_and_clean:
    """
    Clase para leer y limpiar el dataset de reviews.
    Es el DataReader oficial del proyecto, responsable de la transformación de datos.
    """
    
    # Constantes de clase que definen los índices de las columnas necesarias.
    COL_APP_NAME = 2
    COL_REVIEW_ID = 3
    COL_TIMESTAMP = 6
    COL_RECOMMENDED = 8
    COL_VOTES_HELPFUL = 9
    MAX_INDEX = 9
    
    def __init__(self):
        
        pass

    @staticmethod
    def leer_y_limpiar_dataset(ruta_archivo: str) -> List[NormalizedUtility]:
        """
        Metodo estático que lee el CSV, realiza el proceso de limpieza y 
        transformacion, y devuelve la lista de objetos listos para el sort.
        
        @param ruta_archivo: Ruta al archivo CSV.
        @return: Lista de objetos NormalizedUtility limpios.
        """
        print(f"[DataReader] Iniciando lectura y limpieza de {ruta_archivo}...")
        
        lista_de_reviews = []
        errores = 0
        
        # Accedemos a la constante para la verificación del tamaño de la fila
        MAX_IDX = read_and_clean.MAX_INDEX

        try: 
            with open(ruta_archivo, mode='r', encoding='utf-8') as f:
                lector_csv = csv.reader(f)
                
                try:
                    next(lector_csv) # Omitir el encabezado del archivo
                except StopIteration:
                    print("Error: El archivo CSV está vacío.")
                    return []

                for columnas in lector_csv:
                    # Limpieza 1: Verificación de longitud de fila.
                    if len(columnas) > MAX_IDX:
                        try:
                            # 1. Transformación (Conversión de tipos)
                            app_name = columnas[read_and_clean.COL_APP_NAME].strip()
                            review_id = int(columnas[read_and_clean.COL_REVIEW_ID].strip())
                            timestamp = int(columnas[read_and_clean.COL_TIMESTAMP].strip())
                            
                            # CORRECCIÓN DE SINTAXIS: Acceder correctamente a la constante COL_RECOMMENDED de la clase
                            recommended = columnas[read_and_clean.COL_RECOMMENDED].strip().lower() in ('verdadero', 'true')
                            
                            votes_helpful = int(columnas[read_and_clean.COL_VOTES_HELPFUL].strip())

                            # 2. Creación del objeto (dispara la fórmula)
                            lista_de_reviews.append(
                                NormalizedUtility(app_name, review_id, timestamp, recommended, votes_helpful)
                            )
                        
                        except (ValueError, IndexError):
                            # Limpieza 2: Ignorar filas donde la conversión de tipo falla.
                            errores += 1

        except FileNotFoundError:
            print(f"Error: No se encontró el archivo '{ruta_archivo}'")
            return []
        
        print(f"[DataReader] Lectura completa. {len(lista_de_reviews)} filas cargadas. {errores} errores ignorados.")
        return lista_de_reviews