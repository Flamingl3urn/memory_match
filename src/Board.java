import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Board {
    public static final int ROWS = 4;
    public static final int COLS = 4;
    public static final int PAIRS = 8;

    private final Card[][] cells;

    public Board(long seed) {
        cells = new Card[ROWS][COLS];

        // 1) ใส่คู่การ์ดลงลิสต์: 0 0 1 1 ... 7 7
        List<Integer> deck = new ArrayList<>();
        for (int pairId = 0; pairId < PAIRS; pairId++) {
            deck.add(pairId);
            deck.add(pairId);
        }

        // 2) สลับแบบสุ่ม
        Random random = new Random(seed);
        Collections.shuffle(deck, random);

        // 3) กระจายลงกระดาน 4x4
        int index = 0;
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                cells[row][col] = new Card(deck.get(index));
                index++;
            }
        }
    }

    public Card get(int row, int col) {
        check(row, col);
        return cells[row][col];
    }

    public void reveal(int row, int col) {
        Card card = get(row, col);
        if (card.getState() != CardState.HIDDEN) {
            throw new IllegalStateException("ช่องนี้ถูกเปิดหรือจับคู่ไปแล้ว");
        }
        card.setState(CardState.REVEALED);
    }

    public void hide(int row, int col) {
        Card card = get(row, col);
        if (card.getState() != CardState.REVEALED) {
            throw new IllegalStateException("ช่องนี้ยังไม่ถูกเปิด");
        }
        card.setState(CardState.HIDDEN);
    }

    public void markMatched(int row, int col) {
        Card card = get(row, col);
        if (card.getState() != CardState.REVEALED) {
            throw new IllegalStateException("ช่องนี้ยังไม่ถูกเปิด");
        }
        card.setState(CardState.MATCHED);
    }

    public boolean allMatched() {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                if (cells[row][col].getState() != CardState.MATCHED) {
                    return false;
                }
            }
        }
        return true;
    }

    private void check(int row, int col) {
        if (row < 0 || row >= ROWS || col < 0 || col >= COLS) {
            throw new IllegalArgumentException("ตำแหน่งอยู่นอกกระดาน");
        }
    }
}
