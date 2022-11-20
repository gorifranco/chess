import java.io.IOException;

public class RunGame {
    GAME game;
    GUI gui;
    boolean gameOn = true;


    public RunGame() throws IOException {
        game = new GAME();
        gui = new GUI(game);
    }
}
