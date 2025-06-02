package controle;

public class ResultadoBusca implements Comparable<ResultadoBusca> {

    private int id;
    private float score;

    public ResultadoBusca(int id, float score) {
        this.id = id;
        this.score = score;
    }

    public int getId() {
        return id;
    }

    public float getScore() {
        return score;
    }

    @Override
    public int compareTo(ResultadoBusca o) {
        return Float.compare(o.score, this.score);
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Score: " + String.format("%.4f", score);
    }
}
