public class ConsoleUi {
    // shortcut: ตัวอักษรรองรับ 8 คู่ตาม Board.PAIRS
    private static final char[] LETTERS = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H'};

    public static Position parseMove(String line) {
        String[] parts = line.trim().split("\\s+");
        if (parts.length != 2) {
            throw new IllegalArgumentException("รูปแบบไม่ถูกต้อง พิมพ์เป็น 'แถว คอลัมน์' เช่น '2 3'");
        }

        int row;
        int col;
        try {
            row = Integer.parseInt(parts[0]);
            col = Integer.parseInt(parts[1]);
        } catch (NumberFormatException error) {
            throw new IllegalArgumentException("รูปแบบไม่ถูกต้อง พิมพ์เป็น 'แถว คอลัมน์' เช่น '2 3'");
        }

        if (row < 1 || row > Board.ROWS || col < 1 || col > Board.COLS) {
            throw new IllegalArgumentException("ตำแหน่งอยู่นอกกระดาน (แถว/คอลัมน์ต้อง 1-4)");
        }

        return new Position(row - 1, col - 1);
    }

    public static String render(Board board) {
        StringBuilder text = new StringBuilder();
        text.append("  1 2 3 4\n");

        for (int row = 0; row < Board.ROWS; row++) {
            text.append(row + 1).append(' ');
            for (int col = 0; col < Board.COLS; col++) {
                if (col > 0) {
                    text.append(' ');
                }

                Card card = board.get(row, col);
                if (card.getState() == CardState.HIDDEN) {
                    text.append('#');
                } else {
                    text.append(LETTERS[card.getPairId()]);
                }
            }
            text.append('\n');
        }

        return text.toString();
    }
}
