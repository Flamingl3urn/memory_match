public class Game {
    private final Board board;
    private final TurnQueue turns;
    private final OpenCards open;

    public Game(Board board, TurnQueue turns) {
        this.board = board;
        this.turns = turns;
        this.open = new OpenCards();
    }

    public FlipOutcome flip(int row, int col) {
        if (isOver()) {
            throw new IllegalStateException("เกมจบแล้ว");
        }
        if (open.isFull()) {
            throw new IllegalStateException("เปิดการ์ดครบ 2 ใบแล้ว ต้องจับคู่ก่อน");
        }

        board.reveal(row, col);
        open.push(new Position(row, col));

        if (open.isFull()) {
            return FlipOutcome.SECOND_CARD;
        }
        return FlipOutcome.FIRST_CARD;
    }

    public boolean resolve() {
        boolean matched = MatchResolver.resolve(board, open);
        if (matched) {
            turns.current().addScore();
            return true;
        }
        turns.advance();
        return false;
    }

    public boolean isOver() {
        return board.allMatched();
    }

    public Player current() {
        return turns.current();
    }

    public Board getBoard() {
        return board;
    }

    public TurnQueue getTurns() {
        return turns;
    }
}
