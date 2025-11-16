import random
from typing import List
from .NormalizedUtility import NormalizedUtility 

# =======================================================================
# ALGORITMO: InsertionSort
# =======================================================================
def sort(lista: List[NormalizedUtility]) -> List[NormalizedUtility]:
    """
    Implementación manual del Insertion Sort (O(n²)).
    
    Esta función está diseñada para ser el trabajador que cada núcleo de CPU
    utiliza para ordenar su porción de datos.
    
    @param lista: El chunk (porción) de la lista a ordenar.
    @return: La lista de NormalizedUtility ya ordenada.
    """
    
#barajo la lista
    random.shuffle(lista)
    
    n = len(lista)
    # Bucle principal: Recorre la lista desde el segundo elemento.
    for i in range(1, n):
        
        key = lista[i]
        valor_actual = key.utilidad_normalizada
        j = i - 1
        
        # Bucle interno: Desplaza los elementos de la sub-lista ordenada a la derecha,
        # siempre que sean mayores que el valor_actual.
        while j >= 0 and lista[j].utilidad_normalizada > valor_actual:
            lista[j + 1] = lista[j] # Desplazar el objeto
            j -= 1
        
        # Inserta 'key' en su posicion correcta.
        lista[j + 1] = key
    return lista