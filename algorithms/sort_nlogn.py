from typing import List
from .NormalizedUtility import NormalizedUtility 

# FUNCIONES: MergeSort
def sort(lista: List[NormalizedUtility]):
    """Funcion de entrada pública para iniciar el Merge Sort."""
    if lista is None or len(lista) <= 1:
        return
    _merge_sort(lista, 0, len(lista) - 1)

def _merge_sort(lista: List[NormalizedUtility], izquierda: int, derecha: int):
    """Implementacion recursiva del Merge Sort, separacion de elementos."""
    if izquierda < derecha:
        medio = (izquierda + derecha) // 2 
        _merge_sort(lista, izquierda, medio)
        _merge_sort(lista, medio + 1, derecha)
        _merge(lista, izquierda, medio, derecha)

def _merge(lista: List[NormalizedUtility], izquierda: int, medio: int, derecha: int):
    """Metodo de ordenamiento (merge) segun appName, reviewID y timestampCreated."""
    temporal = []
    i = izquierda
    j = medio + 1

    # Compara para ordenar
    while i <= medio and j <= derecha:
        a = lista[i]
        b = lista[j]
        
        # Comparación multinivel: app_name > review_id > timestampCreated
        if a.app_name < b.app_name:
            temporal.append(a); i += 1
        elif a.app_name > b.app_name:
            temporal.append(b); j += 1
        else:
            # Desempate por review_id
            if a.review_id < b.review_id:
                temporal.append(a); i += 1
            elif a.review_id > b.review_id:
                temporal.append(b); j += 1
            else:
                # Desempate final por timestamp_created
                if a.timestamp_created <= b.timestamp_created:
                    temporal.append(a); i += 1
                else:
                    temporal.append(b); j += 1

    # Copia los elementos restantes
    while i <= medio:
        temporal.append(lista[i]); i += 1
    while j <= derecha:
        temporal.append(lista[j]); j += 1
        
    # Copia los elementos ordenados de la lista temporal a la lista original
    for k in range(len(temporal)):
        lista[izquierda + k] = temporal[k]