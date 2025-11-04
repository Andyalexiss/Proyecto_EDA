
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * Script Lectura y Limpieza
 * =======================================================================
 * Script cual carga los datos, crea objetos NormalizedUtility, y luego *extrae*
 * el valor de utilidadNormalizada para cumplir con el main que Andy propuso List<Double>
 * 
 */
public class DataReader {

    // (Usando los índices que validamos: 2, 3, 6, 8, 9)
    private static final int COL_APP_NAME = 2;
    private static final int COL_REVIEW_ID = 3; 
    private static final int COL_TIMESTAMP = 6;
    private static final int COL_RECOMMENDED = 8;
    private static final int COL_VOTES_HELPFUL = 9;
    private static final int MAX_INDEX = 9;

    /**
     * Script de Lectura y Limpieza.
     * @param rutaArchivo La ruta al CSV de 1M.
     * @return Una Lista de objetos NormalizedUtility.
     * @throws IOException Si el archivo no se encuentra.
     */
    public static List<NormalizedUtility> leerYLimpiarDataset(String rutaArchivo) throws IOException {
        
        System.out.println("[DataReader] Iniciando lectura y limpieza...");
        List<NormalizedUtility> reviews = new ArrayList<>();
        
        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            br.readLine(); // Descartar encabezado
            String line;
            
            while ((line = br.readLine()) != null) {
                String[] columnas = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
                
                if (columnas.length > MAX_INDEX) { 
                    try {
                        String appName = columnas[COL_APP_NAME].trim();
                        long reviewId = Long.parseLong(columnas[COL_REVIEW_ID].trim());
                        long timestamp = Long.parseLong(columnas[COL_TIMESTAMP].trim());
                        boolean recommended = Boolean.parseBoolean(columnas[COL_RECOMMENDED].trim().toLowerCase());
                        int votesHelpful = (int) Double.parseDouble(columnas[COL_VOTES_HELPFUL].trim()); 

                        NormalizedUtility reviewObj = new NormalizedUtility(
                            appName, reviewId, timestamp, recommended, votesHelpful
                        );
                        reviews.add(reviewObj);
                        
                    } catch (Exception e) {
                }
            }
        }
        System.out.println("[DataReader] Lectura completada. Se cargaron " + reviews.size() + " filas limpias.");
        //corrigo para que el código de Ana se vincule con el Main / no cambio a list double 
        return reviews;
    }
}