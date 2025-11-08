# Ana (O(n log n): MergeSort o QuickSort)
import csv
import time
from collections import defaultdict

class NormalizedUtility:
    """Clase que representa una review (equivalente a NormalizedUtility en Java)."""
    def __init__(self, app_name, review_id, timestamp_created, recommended, votes_helpful):
        self.app_name = app_name
        self.review_id = review_id
        self.timestamp_created = timestamp_created
        self.recommended = recommended
        self.votes_helpful = votes_helpful
        
        # Placeholders basados en la escritura del CSV en Java
        self.playtime_forever = 0.0 
        self.utilidad_normalizada = self._calcular_utilidad_placeholder()

    def _calcular_utilidad_placeholder(self):
        """Lógica de placeholder para utilidad_normalizada."""
        if self.votes_helpful > 0:
            return (1.0 if self.recommended else 0.0) * self.votes_helpful
        return 0.5 if self.recommended else 0.0

    def __repr__(self):
        """Representación para depuración."""
        return f"NormalizedUtility(app='{self.app_name}', id={self.review_id})"

# --- Funciones de Ordenamiento (Merge Sort) ---

def merge_sort(lista, izquierda, derecha):
    if izquierda < derecha:
        medio = (izquierda + derecha) // 2
        merge_sort(lista, izquierda, medio)
        merge_sort(lista, medio + 1, derecha)
        _merge(lista, izquierda, medio, derecha)

def _merge(lista, izquierda, medio, derecha):
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

# --- Función de Cálculo de Satisfacción ---

def calcular_satisfaccion_por_juego(reviews):
    por_juego = defaultdict(list)

    for r in reviews:
        por_juego[r.app_name].append(r)

    satisfaccion_por_juego = {}

    for app, lista in por_juego.items():
        suma_ponderada = 0.0
        total_votos = 0.0

        for r in lista:
            peso = max(1, r.votes_helpful)
            recomendacion = 1.0 if r.recommended else 0.0
            suma_ponderada += peso * recomendacion
            total_votos += peso

        satisfaccion = 0.0 if total_votos == 0 else suma_ponderada / total_votos
        satisfaccion_por_juego[app] = satisfaccion

    return satisfaccion_por_juego

# --- Bloque Principal de Ejecución ---

def main():
    errores = 0
    lista = []
    archivo_entrada = "src/main/resources/datosEda.csv"
    archivo_salida = "src/main/resources/datosEda_ordenado.csv"

    try:
        with open(archivo_entrada, mode='r', encoding='utf-8') as f:
            lector_csv = csv.reader(f)
            
            try:
                next(lector_csv) # Omitir encabezado
            except StopIteration:
                print("Error: El archivo CSV está vacío.")
                return

            for columnas in lector_csv:
                try:
                    app_name = columnas[2].strip()
                    review_id = int(columnas[3].strip())
                    timestamp = int(columnas[6].strip())
                    recommended = columnas[8].strip().lower() in ('verdadero', 'true')
                    votes_helpful = int(columnas[9].strip())

                    lista.append(NormalizedUtility(app_name, review_id, timestamp, recommended, votes_helpful))
                
                except (ValueError, IndexError):
                    errores += 1

    except FileNotFoundError:
        print(f"Error al leer el archivo: No se encontró el archivo '{archivo_entrada}'")
        return
    except Exception as e:
        print(f"Error inesperado al leer el archivo: {e}")
        return

    inicio = time.perf_counter_ns()
    if lista:
        merge_sort(lista, 0, len(lista) - 1)
    fin = time.perf_counter_ns()
    
    tiempo_segundos = (fin - inicio) / 1_000_000_000.0

    print(f"Total de reviews: {len(lista)}")
    print(f"Tiempo de ordenamiento: {tiempo_segundos:.6f} segundos")
    print(f"Total de errores de reviews: {errores}")

    satisfaccion_por_juego = calcular_satisfaccion_por_juego(lista)
    print("Satisfacción por juego: ")
    for app, satisfaccion in satisfaccion_por_juego.items():
        print(f"{app:<30} : {satisfaccion * 100:.2f}%")

    try:
        with open(archivo_salida, mode='w', encoding='utf-8', newline='') as f:
            escritor_csv = csv.writer(f)
            
            escritor_csv.writerow([
                "app_name", "review_id", "timestamp_created", "recommended",
                "votes_helpful", "playtime_forever", "utilidad_normalizada"
            ])

            for r in lista:
                escritor_csv.writerow([
                    r.app_name,
                    r.review_id,
                    r.timestamp_created,
                    r.recommended,
                    r.votes_helpful,
                    r.playtime_forever,
                    f"{r.utilidad_normalizada:.4f}"
                ])

            print(f"Archivo CSV ordenado guardado como: {archivo_salida}")
    
    except IOError as e:
        print(f"Error al escribir el archivo: {e}")


if __name__ == "__main__":
    main()
