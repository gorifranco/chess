public abstract class PIECE {
boolean alive = true;
int[] position;
int color; // 0 = WHITE   1 = BLACK

    public PIECE(int[] position, int color) {
        this.position = position;
        this.color = color;
    }
}
