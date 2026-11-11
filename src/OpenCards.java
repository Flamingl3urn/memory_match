import java.util.Stack;

public class OpenCards {
    private final Stack<Position> cards = new Stack<>();

    public void push(Position position) {
        if (cards.size() >= 2) {
            throw new IllegalStateException("เปิดการ์ดครบ 2 ใบแล้ว ต้องจับคู่ก่อน");
        }
        if (cards.contains(position)) {
            throw new IllegalStateException("ช่องนี้เปิดอยู่แล้ว");
        }
        cards.push(position);
    }

    public Position pop() {
        if (cards.isEmpty()) {
            throw new IllegalStateException("ยังไม่มีการ์ดที่เปิดอยู่");
        }
        return cards.pop();
    }

    public int size() {
        return cards.size();
    }

    public boolean isFull() {
        return cards.size() == 2;
    }
}
