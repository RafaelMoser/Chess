package Game;

public record Position(int x, int y) {

    public boolean equals(int x, int y) {
        return this.x == x && this.y == y;
    }
}
