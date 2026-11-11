public class Player {
    private final String name;
    private int score;

    public Player(String name) {
        String trimmed = name.trim();
        if (trimmed.isEmpty()) {
            throw new IllegalArgumentException("ชื่อผู้เล่นห้ามว่าง");
        }
        this.name = trimmed;
        this.score = 0;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public void addScore() {
        score++;
    }
}
