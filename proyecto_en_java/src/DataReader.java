 
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * =======================================================================
 * Script Lectura y Limpieza
 * =======================================================================
 * Script cual carga los datos, crea objetos NormalizedUtility, y luego *extrae*
 * el valor de utilidadNormalizada para cumplir con el main que Andy propuso List<Double>
 * 
 */
public class DataReader {

   
    private static final int COL_APP_NAME = 2;
    private static final int COL_REVIEW_ID = 5;
    private static final int COL_TIMESTAMP = 6;
    private static final int COL_RECOMMENDED = 7;
    private static final int COL_VOTES_HELPFUL = 8;
    private static final int MAX_INDEX = 8;

    /**
     * El Main pide List<Double>, pero cargamos los datos y devolvemos la columna
     * que se va a ordenar (la 'utilidadNormalizada').
     * @param rutaArchivo La ruta al CSV de 1M.
     * @return Una Lista de Doubles (los valores que se deben ordenar).
     * @throws IOException Si el archivo no se encuentra.
     */
    public static List<Double> leerYLimpiarDataset(String rutaArchivo) throws IOException {
        
        System.out.println("[DataReader] Iniciando lectura y limpieza...");
        // Usamos una lista temporal para construir los objetos de Ana
        List<NormalizedUtility> objetosCompletos = new ArrayList<>();
        //leo el archivo con su ruta 
        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            br.readLine(); 
            String line;
            
            while ((line = br.readLine()) != null) {
                String[] columnas = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
                
                if (columnas.length > MAX_INDEX) { 
                    try {
                        // --- 1. TRANSFORMACIÓN y CARGA ---
                        String appName = columnas[COL_APP_NAME].trim();
                        long reviewId = Long.parseLong(columnas[COL_REVIEW_ID].trim());
                        long timestamp = Long.parseLong(columnas[COL_TIMESTAMP].trim());
                        boolean recommended = Boolean.parseBoolean(columnas[COL_RECOMMENDED].trim().toLowerCase());
                        int votesHelpful = (int) Double.parseDouble(columnas[COL_VOTES_HELPFUL].trim()); 

                        // Creamos el objeto (AQUÍ SE EJECUTA LA FÓRMULA DE ANA)
                        NormalizedUtility reviewObj = new NormalizedUtility(
                            appName, reviewId, timestamp, recommended, votesHelpful
                        );
                        
                        objetosCompletos.add(reviewObj);
                        
                    } catch (Exception e) { 
                        // Limpieza: Ignoramos las líneas que estén corruptas
                    }
                }
            }
        }

        // ==================================================================
        // Adapto y transformo a list double para el main
        // Convertimos la lista de objetos complejos a la lista simple de Doubles
        // 
        // ==================================================================
        List<Double> valoresParaOrdenar = new ArrayList<>();
        for (NormalizedUtility obj : objetosCompletos) {
            // Extraemos *solo* el valor calculado por la fórmula de Ana
            valoresParaOrdenar.add(obj.getUtilidadNormalizada());
        }
        
        System.out.println("[DataReader] Lectura y limpeiza completada. valores para ordenar listos " + valoresParaOrdenar.size() );
        return valoresParaOrdenar;
    }}