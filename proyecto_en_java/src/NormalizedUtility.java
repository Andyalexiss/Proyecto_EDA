import java.lang.Math;

public class NormalizedUtility {
    // Ana
    //Atributos de la Clase
    private String appName;
    private long reviewId;             //Id de la reseña
    private long timestampCreated;     //Fecha/hora en que se creó la reseña
    private boolean recommended;       //Si el usuario recomienda el juego (TRUE/FALSE)
    private int votesHelpful;          //Número de votos útiles recibidos
    private double weightedVoteScore;  //Puntaje de voto ponderado
    private double utilidadNormalizada;//Resultado del cálculo de utilidad normalizada

    public NormalizedUtility(String appName, long reviewId, long timestampCreated,
                             boolean recommended, int votesHelpful) {
        this.appName = appName;
        this.reviewId = reviewId;
        this.timestampCreated = timestampCreated;
        this.recommended = recommended;
        this.votesHelpful = votesHelpful;
        this.weightedVoteScore = 0.0; // Valor por defecto
        this.utilidadNormalizada = calcularUtilidadNormalizada();
    }

    // Getters
    public String getAppName() {
        return appName;
    }
    public long getReviewId() {
        return reviewId;
    }
    public long getTimestampCreated() {
        return timestampCreated;
    }
    public boolean isRecommended() {
        return recommended;
    }
    public int getVotesHelpful() {
        return votesHelpful;
    }
    public double getWeightedVoteScore() {
        return weightedVoteScore;
    }
    public double getUtilidadNormalizada() {
        return utilidadNormalizada;
    }

    //Calcula la utilidad normalizada basada en los atributos
    private double calcularUtilidadNormalizada() {
        double base = (recommended ? 1 : 0)
                + (votesHelpful / 10.0)
                + (weightedVoteScore * 2);
        return Math.min(base / 3.0, 1.0);
    }
}



