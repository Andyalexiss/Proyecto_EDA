import csv
from NormalizedUtility import NormalizedUtility
from typing import List

class read_and_clean: # El nombre de la clase
    """
    Clase que encapsula la lógica para leer y limpiar el dataset de reviews.
    """
    
   # filas necesarias para los ordenamientos 
    COL_APP_NAME = 2
    COL_REVIEW_ID = 3
    COL_TIMESTAMP = 6
    COL_RECOMMENDED = 8
    COL_VOTES_HELPFUL = 9
    MAX_INDEX = 9
    
    def __init__(self):
        # El constructor puede estar vacío si la clase solo tiene métodos estáticos.
        pass

    def leer_y_limpiar_dataset(self, ruta_archivo: str) -> List[NormalizedUtility]:
        """
        Método que lee el CSV, lo limpia y devuelve una Lista de objetos 
        NormalizedUtility.
        """
        print(f"[DataReader] Iniciando lectura y limpieza de {ruta_archivo}...")
        
        lista_de_reviews = []
        errores = 0
        
        # Usamos self. para acceder a las constantes de la clase
        C_APP_NAME = self.COL_APP_NAME
        C_REVIEW_ID = self.COL_REVIEW_ID
        C_TIMESTAMP = self.COL_TIMESTAMP
        C_RECOMMENDED = self.COL_RECOMMENDED
        C_VOTES_HELPFUL = self.COL_VOTES_HELPFUL
        MAX_IDX = self.MAX_INDEX

        try:
            with open(ruta_archivo, mode='r', encoding='utf-8') as f:
                lector_csv = csv.reader(f)
                
                try:
                    next(lector_csv) # Omitir encabezado
                except StopIteration:
                    print("Error: El archivo CSV está vacío.")
                    return []

                for columnas in lector_csv:
                    if len(columnas) > MAX_IDX:
                        try:
                            # 1. Transformación y Limpieza
                            app_name = columnas[C_APP_NAME].strip()
                            review_id = int(columnas[C_REVIEW_ID].strip())
                            timestamp = int(columnas[C_TIMESTAMP].strip())
                            recommended = columnas[C_RECOMMENDED].strip().lower() in ('verdadero', 'true')
                            votes_helpful = int(columnas[C_VOTES_HELPFUL].strip())

                            # 2. Creación del objeto
                            lista_de_reviews.append(
                                NormalizedUtility(app_name, review_id, timestamp, recommended, votes_helpful)
                            )
                        
                        except (ValueError, IndexError):
                            errores += 1

        except FileNotFoundError:
            print(f"Error: No se encontró el archivo '{ruta_archivo}'")
            return []
        
        print(f"[DataReader] Lectura completa. {len(lista_de_reviews)} filas cargadas. {errores} errores ignorados.")
        return lista_de_reviews