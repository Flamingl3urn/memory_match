public class Card {
    private final int pairId;
    private CardState state;

    public Card(int pairId) {
        this.pairId = pairId;
        this.state = CardState.HIDDEN;
    }

    public int getPairId() {
        return pairId;
    }

    public CardState getState() {
        return state;
    }

    public void setState(CardState state) {
        this.state = state;
    }
}
