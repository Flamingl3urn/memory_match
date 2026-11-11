import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final int MIN_PLAYERS = 2;
    private static final int MAX_PLAYERS = 5;
    private static final Scanner SCANNER = new Scanner(System.in);

    public static void main(String[] args) {
        int count = askCount();
        if (count < 0) {
            return;
        }

        List<Player> players = askPlayers(count);
        if (players == null) {
            return;
        }

        try {
            TurnQueue turns = new TurnQueue(players);
            Board board = new Board(System.currentTimeMillis());
            Game game = new Game(board, turns);

            if (play(game)) {
                showResult(game);
            }
        } catch (RuntimeException error) {
            System.out.println(error.getMessage());
        }
    }

    // คืน -1 ถ้าผู้เล่นกด Ctrl+D
    private static int askCount() {
        while (true) {
            System.out.print("จำนวนผู้เล่น (" + MIN_PLAYERS + "-" + MAX_PLAYERS + "): ");
            if (!SCANNER.hasNextLine()) {
                return -1;
            }

            String line = SCANNER.nextLine().trim();
            try {
                int count = Integer.parseInt(line);
                if (count >= MIN_PLAYERS && count <= MAX_PLAYERS) {
                    return count;
                }
            } catch (NumberFormatException error) {
                // ไม่ใช่ตัวเลข ก็ถามใหม่
            }

            System.out.println("กรุณากรอกตัวเลข " + MIN_PLAYERS + " ถึง " + MAX_PLAYERS);
        }
    }

    // คืน null ถ้าผู้เล่นกด Ctrl+D
    private static List<Player> askPlayers(int count) {
        List<Player> players = new ArrayList<>();

        while (players.size() < count) {
            System.out.print("ชื่อผู้เล่นคนที่ " + (players.size() + 1) + ": ");
            if (!SCANNER.hasNextLine()) {
                return null;
            }

            String line = SCANNER.nextLine();
            Player player;
            try {
                player = new Player(line);
            } catch (IllegalArgumentException error) {
                System.out.println(error.getMessage());
                continue;
            }

            boolean duplicated = false;
            for (Player other : players) {
                if (other.getName().equalsIgnoreCase(player.getName())) {
                    duplicated = true;
                }
            }

            if (duplicated) {
                System.out.println("ชื่อผู้เล่นซ้ำกับคนก่อนหน้า");
            } else {
                players.add(player);
            }
        }

        return players;
    }

    // คืน false ถ้าผู้เล่นกด Ctrl+D กลางเกม
    private static boolean play(Game game) {
        while (!game.isOver()) {
            System.out.print(ConsoleUi.render(game.getBoard()));
            System.out.println(
                "\nตาของ " + game.current().getName() + " (" + game.current().getScore() + " คะแนน)");

            int flipped = 0;
            while (flipped < 2) {
                System.out.print("เลือกช่อง (แถว คอลัมน์ 1-4): ");
                if (!SCANNER.hasNextLine()) {
                    return false;
                }

                String line = SCANNER.nextLine();

                Position position;
                try {
                    position = ConsoleUi.parseMove(line);
                } catch (IllegalArgumentException error) {
                    System.out.println(error.getMessage());
                    continue;
                }

                try {
                    game.flip(position.getRow(), position.getCol());
                    flipped++;
                    System.out.print(ConsoleUi.render(game.getBoard()));
                } catch (RuntimeException error) {
                    System.out.println(error.getMessage());
                }
            }

            if (game.resolve()) {
                System.out.println("จับคู่ได้! " + game.current().getName() + " ได้ 1 คะแนน");
            } else {
                System.out.println("ไม่ตรงกัน พลิกกลับ");
            }
            System.out.println();
        }

        return true;
    }

    private static void showResult(Game game) {
        System.out.print(ConsoleUi.render(game.getBoard()));
        System.out.println("\nสรุปอันดับ");

        List<Player> ranking = game.getTurns().ranking();
        for (int i = 0; i < ranking.size(); i++) {
            Player player = ranking.get(i);
            System.out.println((i + 1) + ". " + player.getName() + " - " + player.getScore() + " คะแนน");
        }

        List<Player> winners = game.getTurns().winners();
        StringBuilder names = new StringBuilder();
        for (int i = 0; i < winners.size(); i++) {
            if (i > 0) {
                names.append(", ");
            }
            names.append(winners.get(i).getName());
        }

        if (winners.size() == 1) {
            System.out.println("\nผู้ชนะ: " + names);
        } else {
            System.out.println("\nเสมอ: " + names);
        }
    }
}
