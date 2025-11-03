import NormalizedUtility;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.io.*;
import java.util.Map;
public class OnLogN_Sort {
    // Ana
    // Implementación del Merge Sort
    public static void mergeSort(List<NormalizedUtility> lista, int izquierda, int derecha) {
        if (izquierda < derecha) { //Separa hasta que tenga 1 elemento
            int medio = (izquierda + derecha) / 2;
            mergeSort(lista, izquierda, medio);
            mergeSort(lista, medio + 1, derecha);
            merge(lista, izquierda, medio, derecha);
        }
    }

    //Metodo de ordenamiento segun reviewID y timestampCreated
    private static void merge(List<NormalizedUtility> lista, int izquierda, int medio, int derecha) {
        List<NormalizedUtility> temporal = new ArrayList<>();
        int i = izquierda, j = medio + 1;

        //Compara para ordenar
        while (i <= medio && j <= derecha) {
            NormalizedUtility a = lista.get(i);
            NormalizedUtility b = lista.get(j);

            int appNameComp = a.getAppName().compareTo(b.getAppName());

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

        while (i <= medio) temporal.add(lista.get(i++));
        while (j <= derecha) temporal.add(lista.get(j++));

        //Presenta los elementos ya ordenados
        for (int k = 0; k < temporal.size(); k++) {
            lista.set(izquierda + k, temporal.get(k));
        }
    }
    public static Map<String, Double> calcularSatisfaccionPorJuego(List<NormalizedUtility> reviews) {
        Map<String, List<NormalizedUtility>> porJuego = new HashMap<>();

        // Agrupamos por app_name
        for (NormalizedUtility r : reviews) {
            porJuego.computeIfAbsent(r.getAppName(), k -> new ArrayList<>()).add(r);
        }

        Map<String, Double> satisfaccionPorJuego = new HashMap<>();

        // Satisfaccion para cada juego
        for (Map.Entry<String, List<NormalizedUtility>> entry : porJuego.entrySet()) {
            String app = entry.getKey();
            List<NormalizedUtility> lista = entry.getValue();

            double sumaPonderada = 0.0;
            double totalVotos = 0.0;

            for (NormalizedUtility r : lista) {
                double peso = Math.max(1, r.getVotesHelpful());
                double recomendacion = r.isRecommended() ? 1.0 : 0.0;
                sumaPonderada += peso * recomendacion;
                totalVotos += peso;
            }

            double satisfaccion = totalVotos == 0 ? 0.0 : sumaPonderada / totalVotos;
            satisfaccionPorJuego.put(app, satisfaccion);
        }

        return satisfaccionPorJuego;
    }


    public static void main(String[] args) {
        int errores = 0;
        List<NormalizedUtility> lista = new ArrayList<>();
        String archivoEntrada = "src/main/resources/datosEda.csv"; //Base de datos original
        String archivoSalida = "src/main/resources/datosEda_ordenado.csv"; //Base de datos ordenados

        //Lee el archivo original
        try (BufferedReader br = new BufferedReader(new FileReader(archivoEntrada))) {
            String linea = br.readLine(); //Titulo de línea
            linea = br.readLine();

            //Lee el archivo línea por línea
            while (linea != null) {
                String[] columnas = linea.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");

                //Extrae los valores de cada columna
                try {
                    String appName = columnas[2].trim();
                    long reviewId = Long.parseLong(columnas[3].trim());
                    long timestamp = Long.parseLong(columnas[6].trim());
                    boolean recommended = columnas[8].trim().equalsIgnoreCase("VERDADERO") ||
                            columnas[8].trim().equalsIgnoreCase("TRUE");
                    int votesHelpful = Integer.parseInt(columnas[9].trim());

                    //Crea una instancia del tipo Review
                    lista.add(new NormalizedUtility(appName, reviewId, timestamp, recommended, votesHelpful));
                } catch (Exception e) {
                    errores++;
                }

                linea = br.readLine();
            }

            //Lanza error al leer
        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
            return;
        }

        long inicio = System.nanoTime(); 
        mergeSort(lista, 0, lista.size() - 1); // Ordenamiento
        long fin = System.nanoTime(); // tiempo del sort
        double tiempoSegundos = (fin - inicio) / 1_000_000_000.0; // Conversion a segundos
        System.out.println("Total de reviews: " + lista.size());
        System.out.printf("Tiempo de ordenamiento: %.6f segundos%n", tiempoSegundos);
        System.out.println("Total de errores de reviews: " + errores);
        Map<String, Double> satisfaccionPorJuego = calcularSatisfaccionPorJuego(lista);
        System.out.println("Satisfacción por juego: ");
        for (Map.Entry<String, Double> entry : satisfaccionPorJuego.entrySet()) {
            System.out.printf("%-30s : %.2f%%%n", entry.getKey(), entry.getValue() * 100);
        }

        //Escribir los datos ordenados
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivoSalida))) {
            // Encabezado del nuevo archivo
            bw.write("app_name,review_id,timestamp_created,recommended,votes_helpful,playtime_forever,utilidad_normalizada");
            bw.newLine();

            // Escribe los datos ordenados
            for (NormalizedUtility r : lista) {
                bw.write(r.getAppName() + "," +
                        r.getReviewId() + "," +
                        r.getTimestampCreated() + "," +
                        r.isRecommended() + "," +
                        r.getVotesHelpful() + "," +
                        String.format("%.4f", r.getUtilidadNormalizada()));
                bw.newLine();
            }

            System.out.println("Archivo CSV ordenado guardado como: " + archivoSalida);
            //Lanza error al escribir
        } catch (IOException e) {
            System.out.println("Error al escribir el archivo: " + e.getMessage());
        }
    }
}

