public class Position {
    private final int row;
    private final int col;

    public Position(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Position)) {
            return false;
        }
        Position position = (Position) other;
        return row == position.row && col == position.col;
    }

    @Override
    public int hashCode() {
        return row * 4 + col;
    }

    @Override
    public String toString() {
        return "(" + row + ", " + col + ")";
    }
}
