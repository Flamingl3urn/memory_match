import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class TurnQueue {
    public static final int MIN_PLAYERS = 1;
    public static final int MAX_PLAYERS = 5;

    private final Queue<Player> players = new LinkedList<>();

    public TurnQueue(List<Player> list) {
        if (list.size() < MIN_PLAYERS) {
            throw new IllegalArgumentException("ต้องมีผู้เล่นอย่างน้อย 1 คน");
        }
        if (list.size() > MAX_PLAYERS) {
            throw new IllegalArgumentException("รองรับผู้เล่นได้สูงสุด 5 คน");
        }

        // ตรวจชื่อซ้ำแบบไม่สนตัวพิมพ์ใหญ่/เล็ก
        for (int i = 0; i < list.size(); i++) {
            for (int j = i + 1; j < list.size(); j++) {
                String a = list.get(i).getName().toLowerCase();
                String b = list.get(j).getName().toLowerCase();
                if (a.equals(b)) {
                    throw new IllegalArgumentException("ชื่อผู้เล่นซ้ำกับคนก่อนหน้า");
                }
            }
        }

        players.addAll(list);
    }

    public Player current() {
        return players.peek();
    }

    public void advance() {
        Player player = players.poll();
        players.add(player);
    }

    public List<Player> ranking() {
        List<Player> ranked = new ArrayList<>(players);

        // insertion sort จากคะแนนมากไปน้อย (เสมอกันคงลำดับเดิม)
        for (int i = 1; i < ranked.size(); i++) {
            int j = i;
            while (j > 0 && ranked.get(j - 1).getScore() < ranked.get(j).getScore()) {
                Player temp = ranked.get(j - 1);
                ranked.set(j - 1, ranked.get(j));
                ranked.set(j, temp);
                j--;
            }
        }

        return ranked;
    }

    public List<Player> winners() {
        List<Player> ranked = ranking();
        int topScore = ranked.get(0).getScore();

        List<Player> winners = new ArrayList<>();
        for (Player player : ranked) {
            if (player.getScore() == topScore) {
                winners.add(player);
            }
        }
        return winners;
    }
}
