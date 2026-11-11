public class MatchResolver {
    public static boolean resolve(Board board, OpenCards open) {
        if (!open.isFull()) {
            throw new IllegalStateException("ต้องเปิดการ์ดให้ครบ 2 ใบก่อน");
        }

        Position second = open.pop();
        Position first = open.pop();

        Card firstCard = board.get(first.getRow(), first.getCol());
        Card secondCard = board.get(second.getRow(), second.getCol());

        if (firstCard.getPairId() == secondCard.getPairId()) {
            board.markMatched(first.getRow(), first.getCol());
            board.markMatched(second.getRow(), second.getCol());
            return true;
        }

        board.hide(first.getRow(), first.getCol());
        board.hide(second.getRow(), second.getCol());
        return false;
    }
}
