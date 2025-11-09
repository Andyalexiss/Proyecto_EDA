import NormalizedUtility;
import java.util.ArrayList;
import java.util.List;

public class OnLogN_Sort {
    // Ana
    //Lamada al método MergeSort recursivo
    public static void sort(List<NormalizedUtility> lista) {
        if (lista == null || lista.size() <= 1) {
            return; // No hay nada que ordenar
        }
        // Llama a la función secundaria recursiva
        mergeSort(lista, 0, lista.size() - 1);
    }

    //Implementación recursiva del Merge Sort, separacion de elementos.
    private static void mergeSort(List<NormalizedUtility> lista, int izquierda, int derecha) {
        if (izquierda < derecha) { //Separa hasta que tenga 1 elemento
            int medio = (izquierda + derecha) / 2;
            mergeSort(lista, izquierda, medio);
            mergeSort(lista, medio + 1, derecha);
            merge(lista, izquierda, medio, derecha);
        }
    }

    //Metodo de ordenamiento (merge) para appName, reviewID y timestampCreated.

    private static void merge(List<NormalizedUtility> lista, int izquierda, int medio, int derecha) {
        //ArrayList para la lista temporal
        List<NormalizedUtility> temporal = new ArrayList<>(derecha - izquierda + 1);
        int i = izquierda, j = medio + 1;

        //Compara para ordenar
        while (i <= medio && j <= derecha) {
            NormalizedUtility a = lista.get(i);
            NormalizedUtility b = lista.get(j);

            int appNameComp = a.getAppName().compareTo(b.getAppName());
            //Compara el appName
            if (appNameComp < 0) {
                temporal.add(a);
                i++;
            } else if (appNameComp > 0) {
                temporal.add(b);
                j++;
            } else {
                //Se compara review ID, si el juego es el mismo
                if (a.getReviewId() < b.getReviewId()) {
                    temporal.add(a);
                    i++;
                } else if (a.getReviewId() > b.getReviewId()) {
                    temporal.add(b);
                    j++;
                } else {
                    //Comparamos por tiempo
                    if (a.getTimestampCreated() <= b.getTimestampCreated()) {
                        temporal.add(a);
                        i++;
                    } else {
                        temporal.add(b);
                        j++;
                    }
                }
            }
        }

        //Copia los elementos restantes de la primera mitad en la lista temporal
        while (i <= medio) {
            temporal.add(lista.get(i++));
        }

        //Copia los elementos restantes de la segunda mitad en la lista temporal
        while (j <= derecha) {
            temporal.add(lista.get(j++));
        }

        //Copia los elementos ordenados de la lista temporal a la lista original
        for (int k = 0; k < temporal.size(); k++) {
            lista.set(izquierda + k, temporal.get(k));
        }
    }
}


