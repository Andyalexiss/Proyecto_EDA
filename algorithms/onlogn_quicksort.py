import random
from typing import List, Any


# =======================================================================
# onlogn_quicksort
# =======================================================================
"""
Implementación del Quicksort de Tres Vías (3-way partition) de Dijkstra.

Optimizado para manejar grandes volúmenes de valores duplicados 
(utilidadNormalizada) 
"""
# =======================================================================

def intercambiar(lista: List[Any], i: int, j: int):
    """
    Método de utilidad para intercambiar dos elementos en la lista (in-place).
    
    Args:
        lista (List): La lista donde se realiza el intercambio.
        i (int): Índice del primer elemento.
        j (int): Índice del segundo elemento.
    """
    lista[i], lista[j] = lista[j], lista[i]


def ordenar_recursivo(lista: List[Any], indice_inicio: int, indice_fin: int):
    """
    El metodo recursivo principal del Quicksort de Tres Vias.

    Divide la lista en tres zonas: Menores (<), Iguales (=) y Mayores (>) al pivote.
    
    Args:
        lista (List): La porción de la lista a ordenar.
        indice_inicio (int): Límite inferior de la porción actual.
        indice_fin (int): Límite superior de la porción actual.
    """
    # 1. Condicion base: si ya no hay elementos o solo hay uno, se detiene.
    if indice_inicio >= indice_fin: 
        return

    # 2. Pivote Aleatorio: Se elige un pivote al azar para estabilidad 
    # y evitar el peor caso (O(N^2)) en listas pre-ordenadas.
    indice_aleatorio = random.randint(indice_inicio, indice_fin)
    
    # Se coloca el pivote en el indice de inicio para la particion
    intercambiar(lista, indice_aleatorio, indice_inicio)

    # El pivote se selecciona del indice 'indice_inicio'
    # Asumimos que los objetos tienen el método para obtener el valor de comparación.
    valor_pivote = lista[indice_inicio].get_utilidad_normalizada()
    
    # 3. TRES PUNTEROS 
    indice_menor = indice_inicio    
    indice_mayor = indice_fin       
    i = indice_inicio + 1           

    # 4. tres caminos
    while i <= indice_mayor:
        valor_actual = lista[i].get_utilidad_normalizada()
        
        if valor_actual < valor_pivote:
            # Caso 1: Menor que el pivote. 
            # Se intercambia con el primer elemento de la zona central (=) y se expande la zona <.
            intercambiar(lista, indice_menor, i)
            indice_menor += 1
            i += 1 
            
        elif valor_actual > valor_pivote:
            # Caso 2: Mayor que el pivote. 
            # Se intercambia con el último elemento de la zona > y se contrae la zona >.
            intercambiar(lista, i, indice_mayor)
            indice_mayor -= 1  
            # i no avanza porque el elemento recién intercambiado en lista[i] necesita ser revisado
            
        else:
            # Caso 3: Igual al pivote. 
            # Se queda en la zona central
            i += 1
    
    # 5. Llamada Recursiva
    # Se llama recursivamente SOLO a las zonas < y > (excluyendo la zona =)
    ordenar_recursivo(lista, indice_inicio, indice_menor - 1)  # Zona < valorPivote
    ordenar_recursivo(lista, indice_mayor + 1, indice_fin)      # Zona > valorPivote


def sort(lista: List[Any]):
    """
    Método principal (interfaz pública). Inicia el ordenamiento.
    
    Llama al método recursivo con los índices de toda la lista.
    
    Args:
        lista (List): La lista de objetos NormalizedUtility a ordenar (in-place).
    """
    ordenar_recursivo(lista, 0, len(lista) - 1)

