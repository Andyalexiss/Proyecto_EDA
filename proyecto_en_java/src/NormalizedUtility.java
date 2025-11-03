package ProyectoB1;
import java.lang.Math;
import java.util.List;
public class NormalizedUtility {
    // Ana
    // Atributos de la Clase
    private String appName;
    private long reviewId;             // Id de la reseña
    private long timestampCreated;     // Fecha/hora en que se creó la reseña
    private boolean recommended;       // Si el usuario recomienda el juego (TRUE/FALSE)
    private int votesHelpful;          // Número de votos útiles recibidos
    private int votesFunny;
    private double weightedVoteScore;
    private int commentCount;
    private double utilidadNormalizada; // Resultado del cálculo de utilidad normalizada

    public NormalizedUtility(String appName, long reviewId, long timestampCreated,
                             boolean recommended, int votesHelpful) {
        this.appName = appName;
        this.reviewId = reviewId;
        this.timestampCreated = timestampCreated;
        this.recommended = recommended;
        this.votesHelpful = votesHelpful;
        this.votesFunny = votesFunny;
        this.weightedVoteScore = weightedVoteScore;
        this.commentCount = commentCount;
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
    public int getVotesFunny() {
        return votesFunny;
    }
    public double getWeightedVoteScore() {
        return weightedVoteScore;
    }
    public int getCommentCount() {
        return commentCount;
    }
    public double getUtilidadNormalizada() {
        return utilidadNormalizada;
    }

    private double calcularUtilidadNormalizada() {
        double base = (recommended ? 1 : 0)
                + (votesHelpful / 10.0)
                + (weightedVoteScore * 2);
        return Math.min(base / 3.0, 1.0);
    }

    //Metodo para calcular
    public static double calcularSatisfaccion(List<NormalizedUtility> reviews) {
        if (reviews == null || reviews.isEmpty()) return 0.0;
        double suma = 0.0;
        for (NormalizedUtility r : reviews) {
            double R = r.isRecommended() ? 1.0 : 0.0;

            double totalVotes = r.getVotesHelpful() + r.getVotesFunny() + 1.0;
            double H = r.getVotesHelpful() / totalVotes;

            double W = r.getWeightedVoteScore();

            double C = Math.log(1 + r.getCommentCount()) / Math.log(2);
            C = Math.min(C / 10.0, 1.0);
            double satisfaccion = (0.4 * R) + (0.3 * H) + (0.2 * W) + (0.1 * C);
            suma += satisfaccion;
        }

        return suma / reviews.size();
    }
}

