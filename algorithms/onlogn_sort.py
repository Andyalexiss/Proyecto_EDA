# Ana (O(n log n): MergeSort o QuickSort)
import csv  
from typing import List

# CLASE: NormalizedUtility
class NormalizedUtility:
    
    #Clase de datos para almacenar la información de la reseña.
    def __init__(self, app_name: str, review_id: int, timestamp_created: int, 
                 recommended: bool, votes_helpful: int):
        
        self.app_name = app_name
        self.review_id = review_id
        self.timestamp_created = timestamp_created
        self.recommended = recommended
        self.votes_helpful = votes_helpful
        self.weighted_vote_score = 0.0
        self.utilidad_normalizada = self._calcular_utilidad_normalizada()

    def _calcular_utilidad_normalizada(self) -> float:
        #Calcula la utilidad normalizada.
        base = (1 if self.recommended else 0) \
               + (self.votes_helpful / 10.0) \
               + (self.weighted_vote_score * 2)
        return min(base / 3.0, 1.0)

    def __repr__(self) -> str:
        #Imprime los datos
        return f"App: {self.app_name}, ID: {self.review_id}, Time: {self.timestamp_created}"


# FUNCIÓN: DataReader
def cargar_datos(ruta_archivo: str) -> List[NormalizedUtility]:
    print(f"[DataReader] Iniciando lectura de {ruta_archivo}...")
    reviews = []
    # Índices de columnas del DataReader.java
    COL_APP_NAME = 2
    COL_REVIEW_ID = 3
    COL_TIMESTAMP = 6
    COL_RECOMMENDED = 8
    COL_VOTES_HELPFUL = 9

    try:
        with open(ruta_archivo, mode='r', encoding='utf-8') as file:
            reader = csv.reader(file)
            next(reader, None) #Omite encabezado
            
            for row in reader:
                try:
                    if len(row) > COL_VOTES_HELPFUL:
                        # Extraer datos
                        app_name = row[COL_APP_NAME].strip()
                        review_id = int(row[COL_REVIEW_ID].strip())
                        timestamp = int(row[COL_TIMESTAMP].strip())
                        recommended = row[COL_RECOMMENDED].strip().lower() == 'true'
                        votes_helpful = int(float(row[COL_VOTES_HELPFUL].strip()))

                        # Crear y añadir a la lista
                        reviews.append(NormalizedUtility(
                            app_name, review_id, timestamp, recommended, votes_helpful
                        ))
                except Exception as e:
                    # Ignorar filas con datos incorrectos o incompletos
                    pass 
                    
    except FileNotFoundError:
        print(f"Error: No se encontró el archivo en {ruta_archivo}")
        return []
    except Exception as e:
        print(f"Error inesperado al leer el archivo: {e}")
        return []

    print(f"[DataReader] Lectura completada. Se cargaron {len(reviews)} filas.")
    return reviews

#FUNCIÓN: Guardar los datos ordenados en u nuevo csv
def guardar_datos_ordenados(lista_ordenada: List[NormalizedUtility], ruta_salida: str):
    
    #Escribe la lista ordenada en un nuevo archivo CSV
    
    print(f"\n[DataWriter] Guardando datos ordenados en {ruta_salida}...")
    try:
        with open(ruta_salida, mode='w', encoding='utf-8', newline='') as file:
            writer = csv.writer(file)
            
            # Escribir el encabezado
            writer.writerow([
                "app_name", "review_id", "timestamp_created", 
                "recommended", "votes_helpful", "utilidad_normalizada"
            ])
            
            # Escribir los datos
            for item in lista_ordenada:
                writer.writerow([
                    item.app_name,
                    item.review_id,
                    item.timestamp_created,
                    item.recommended,
                    item.votes_helpful,
                    f"{item.utilidad_normalizada:.4f}" 
                ])
        print(f"[DataWriter] Archivo guardado exitosamente.")
    except Exception as e:
        print(f"[DataWriter] Error al guardar el archivo: {e}")

# FUNCIONES: MergeSort
def sort(lista: List[NormalizedUtility]):
    if lista is None or len(lista) <= 1:
        return
    _merge_sort(lista, 0, len(lista) - 1)

def _merge_sort(lista: List[NormalizedUtility], izquierda: int, derecha: int):

    #Implementación recursiva de Merge Sort
    if izquierda < derecha:
        medio = (izquierda + derecha) // 2 
        _merge_sort(lista, izquierda, medio)
        _merge_sort(lista, medio + 1, derecha)
        _merge(lista, izquierda, medio, derecha)

def _merge(lista: List[NormalizedUtility], izquierda: int, medio: int, derecha: int):
    # 
    temporal = []
    i = izquierda
    j = medio + 1

    while i <= medio and j <= derecha:
        a = lista[i]
        b = lista[j]
        if a.app_name < b.app_name:
            temporal.append(a)
            i += 1
        elif a.app_name > b.app_name:
            temporal.append(b)
            j += 1
        else:
            if a.review_id < b.review_id:
                temporal.append(a)
                i += 1
            elif a.review_id > b.review_id:
                temporal.append(b)
                j += 1
            else:
                if a.timestamp_created <= b.timestamp_created:
                    temporal.append(a)
                    i += 1
                else:
                    temporal.append(b)
                    j += 1

    while i <= medio:
        temporal.append(lista[i])
        i += 1
    while j <= derecha:
        temporal.append(lista[j])
        j += 1
    for k in range(len(temporal)):
        lista[izquierda + k] = temporal[k]

if __name__ == "__main__":

    RUTA_DATASET = "datosEda.csv"
    RUTA_SALIDA = "datosEda_ordenado_py.csv" #ARchivo de salida

    datos = cargar_datos(RUTA_DATASET)

    if datos:
        print("\nEjecutando el MergeSortsobre el archivo CSV original")
        
        sort(datos)
        
        print("Ordenamiento completado")
    
        guardar_datos_ordenados(datos, RUTA_SALIDA)
        
        print("\nPrimeros 10 resultados:")
        for item in datos[:10]:
            print(item)
    else:
        print("ERROR: No se cargaron datos.")
