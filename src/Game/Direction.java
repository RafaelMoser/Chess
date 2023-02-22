package Game;

public enum Direction {
    N(0, 0),
    U(1, 0),
    UR(1, 1),
    R(0, 1),
    DR(-1, 1),
    D(-1, 0),
    DL(-1, -1),
    L(0, -1),
    UL(1, -1);

    public final int x;
    public final int y;

    Direction(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public static Direction findDirection(Position from, Position to) {
        if (from.x() < to.x()) {
            if (from.y() < to.y()) {
                return UR;
            } else if (from.y() == to.y()) {
                return U;
            } else {
                return UL;
            }
        } else if (from.x() == to.x()) {
            if (from.y() < to.y()) {
                return R;
            } else if (from.y() == to.y()) {
                return N;
            } else {
                return L;
            }
        } else {
            if (from.y() < to.y()) {
                return DR;
            } else if (from.y() == to.y()) {
                return D;
            } else {
                return DL;
            }
        }
    }

    public static boolean isSlidingMove(Position from, Position to) {
        if (from.equals(to)) {
            return false;
        }
        if (Math.abs(from.x() - to.x()) == Math.abs(from.y() - to.y())) {
            return true;
        } else if (from.x() == to.x()) {
            return from.y() != to.y();
        } else {
            return from.y() == to.y();
        }
    }
}
