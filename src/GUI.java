import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;

public class GUI extends JFrame implements ActionListener {
    JLabel gameBackground;
    JLabel menu;
    JLabel loadBackground;
    JButton newGameButton;
    JButton loadGame;
    JButton saveButton;
    JTextField gameName;
    JButton[][] caselles = new JButton[8][8];
    GAME game;

    int[] selected1;

    // WHITE IMAGE ICONS
    ImageIcon whiteQueen = new ImageIcon(ImageIO.read(getClass().getResource("img/whitequeen.png")));
    ImageIcon whiteKing = new ImageIcon(ImageIO.read(getClass().getResource("img/whiteking.png")));
    ImageIcon whiteKnight = new ImageIcon(ImageIO.read(getClass().getResource("img/whiteknight.png")));
    ImageIcon whitePawn = new ImageIcon(ImageIO.read(getClass().getResource("img/whitepawn.png")));
    ImageIcon whiteRook = new ImageIcon(ImageIO.read(getClass().getResource("img/whiterook.png")));
    ImageIcon whiteBishop = new ImageIcon(ImageIO.read(getClass().getResource("img/whitebishop.png")));

    // BLACK IMAGE ICONS
    ImageIcon blackQueen = new ImageIcon(ImageIO.read(getClass().getResource("img/blackqueen.png")));
    ImageIcon blackKing = new ImageIcon(ImageIO.read(getClass().getResource("img/blackking.png")));
    ImageIcon blackKnight = new ImageIcon(ImageIO.read(getClass().getResource("img/blackknight.png")));
    ImageIcon blackPawn = new ImageIcon(ImageIO.read(getClass().getResource("img/blackpawn.png")));
    ImageIcon blackRook = new ImageIcon(ImageIO.read(getClass().getResource("img/blackrook.png")));
    ImageIcon blackBishop = new ImageIcon(ImageIO.read(getClass().getResource("img/blackbishop.png")));

    Color brown = new Color(153, 76, 0);
    Color fieldSelectedColor = new Color(102, 255, 178);

    public GUI(GAME game) throws IOException {
        super("Chess");
        this.game = game;
        add(displayMenu());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(700, 740);
        setResizable(false);
        setVisible(true);
    }

    //LOAD MENU
    public JLabel loadGameMenu() throws IOException {
        loadBackground = new JLabel();
        loadBackground.setLayout(null);
        String[] savedGames = game.getSavedGames();

        JList<String> savesList = new JList<>(savedGames);
        savesList.setBounds(150, 50, 400, 350);
        savesList.setEnabled(true);
        savesList.setVisible(true);
        savesList.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    try {
                        game.loadGame(savesList.getSelectedValue());
                    } catch (FileNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                    try {
                        loadBackground.setVisible(false);
                        add(displayGame());
                        gameBackground.setVisible(true);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });

        loadBackground.add(savesList);

        return loadBackground;
    }



    //CREACION TABLERO
    public JLabel displayGame() throws IOException {
        gameBackground = new JLabel();
        gameBackground.setLayout(null);

        // Tablero
        JPanel centerPanel = new JPanel();
        JPanel saveBar = new JPanel();
        saveBar.setLayout(null);
        saveBar.setBounds(0, 0, 650, 36);
        saveBar.setOpaque(false);
        saveButton = new JButton();
        saveButton.setBounds(200,5,100,30);
        saveButton.setVisible(true);
        saveButton.setText("Save Game");
        saveButton.addActionListener(this);
        saveBar.add(saveButton);

        gameName = new JTextField();
        gameName.setBounds(320,5,150,30);
        gameName.addActionListener(this);
        gameName.setToolTipText("Game name");
        saveBar.add(gameName);

        gameBackground.add(saveBar, BorderLayout.CENTER);

        // tablero
        centerPanel.setLayout(new GridLayout(8, 8));
        gameBackground.add(centerPanel, BorderLayout.CENTER);
        centerPanel.setBounds(0, 40, 650, 650);
        centerPanel.setOpaque(false);

        //CASILLAS Y PIEZAS
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                caselles[i][j] = new JButton();
                caselles[i][j].setEnabled(false);
                if ((i + j) % 2 == 0) {
                    caselles[i][j].setBackground(Color.gray);

                } else {
                    caselles[i][j].setBackground(brown);
                }

                if (game.isFieldOccupied(new int[]{i, j})) {
                    if(game.getPiece(new int[]{i, j}).color==game.playerMoving){
                        caselles[i][j].setEnabled(true);
                        caselles[i][j].setFocusable(true);
                    }
                    switch (game.getPiece(new int[]{i, j}).getClass().toString()) {
                        case "class PAWN" -> {
                            if (game.getPiece(new int[]{i, j}).color == 0) {
                                try {
                                    caselles[i][j].setIcon(whitePawn);
                                } catch (Exception ex) {
                                    System.out.println("ex");
                                }
                            } else {
                                try {
                                    caselles[i][j].setIcon(blackPawn);
                                } catch (Exception ex) {
                                    System.out.println("ex");
                                }
                            }
                        }
                        case "class KING" -> {
                            if (game.getPiece(new int[]{i, j}).color == 0) {
                                try {
                                    caselles[i][j].setIcon(whiteKing);
                                } catch (Exception ex) {
                                    System.out.println("ex");
                                }
                            } else {
                                try {
                                    caselles[i][j].setIcon(blackKing);
                                } catch (Exception ex) {
                                    System.out.println("ex");
                                }
                            }
                        }
                        case "class QUEEN" -> {
                            if (game.getPiece(new int[]{i, j}).color == 0) {
                                try {
                                    caselles[i][j].setIcon(whiteQueen);
                                } catch (Exception ex) {
                                    System.out.println("ex");
                                }
                            } else {
                                try {
                                    caselles[i][j].setIcon(blackQueen);
                                } catch (Exception ex) {
                                    System.out.println("ex");
                                }
                            }
                        }
                        case "class ROOK" -> {
                            if (game.getPiece(new int[]{i, j}).color == 0) {
                                try {
                                    caselles[i][j].setIcon(whiteRook);
                                } catch (Exception ex) {
                                    System.out.println("ex");
                                }
                            } else {
                                try {
                                    caselles[i][j].setIcon(blackRook);
                                } catch (Exception ex) {
                                    System.out.println("ex");
                                }
                            }
                        }
                        case "class KNIGHT" -> {
                            if (game.getPiece(new int[]{i, j}).color == 0) {
                                try {
                                    caselles[i][j].setIcon(whiteKnight);
                                } catch (Exception ex) {
                                    System.out.println("ex");
                                }
                            } else {
                                try {
                                    caselles[i][j].setIcon(blackKnight);
                                } catch (Exception ex) {
                                    System.out.println("ex");
                                }
                            }
                        }
                        case "class BISHOP" -> {
                            if (game.getPiece(new int[]{i, j}).color == 0) {
                                try {
                                    caselles[i][j].setIcon(whiteBishop);
                                } catch (Exception ex) {
                                    System.out.println("ex");
                                }
                            } else {
                                try {
                                    caselles[i][j].setIcon(blackBishop);
                                } catch (Exception ex) {
                                    System.out.println("ex");
                                }
                            }
                        }
                    }
                }
                centerPanel.add(caselles[i][j]);
                caselles[i][j].addActionListener(this);
                caselles[i][j].setName(String.valueOf(i) + j);
            }
        }
        return gameBackground;
    }

    // MENU INICIAL
    public JLabel displayMenu() {
        menu = new JLabel();

        menu.setLayout(null);
        newGameButton = new JButton();
        newGameButton.setBounds(311, 227, 166, 56);
        newGameButton.setBorderPainted(true);
        newGameButton.setContentAreaFilled(false);
        newGameButton.addActionListener(this);
        newGameButton.setBackground(Color.green);
        newGameButton.setText("NEW GAME");

        loadGame = new JButton();
        loadGame.setBounds(311, 357, 166, 56);
        loadGame.setBorderPainted(true);
        loadGame.setContentAreaFilled(false);
        loadGame.addActionListener(this);
        loadGame.setBackground(Color.green);
        loadGame.setText("LOAD GAME");

        menu.add(newGameButton);
        menu.add(loadGame);

        return menu;
    }

    //CANVIA LES CASELLES ON ES POT MOURE UNA PEÇA A COLOR VERD I ENABLED
    public void changeButtonColors(int[] positionSelected) {
        int[][] possibleMovements = game.possibleMovements(game.getPiece(positionSelected));
        for (int[] possibleMovement : possibleMovements) {
            int a = possibleMovement[0];
            int b = possibleMovement[1];
            caselles[a][b].setBackground(fieldSelectedColor);
            caselles[a][b].setEnabled(true);
        }
    }

    // ALL FIELD COLORS TO ORIGINAL
    public void resetFieldColors() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if ((i + j) % 2 == 0) {
                    caselles[i][j].setBackground(Color.gray);

                } else {
                    caselles[i][j].setBackground(brown);
                }
            }

        }
    }

    // RESET FIELDS FOCUS WHEN PLAYER CHANGES
    public void resetFocus() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (fieldOccupied(new int[]{i, j})) {
                    if (game.getPiece(new int[]{i, j}).color == game.playerMoving) {
                        caselles[i][j].setFocusable(true);
                        caselles[i][j].setEnabled(true);
                    } else {
                        caselles[i][j].setFocusable(false);
                        caselles[i][j].setEnabled(false);
                    }
                } else {
                    caselles[i][j].setFocusable(false);
                    caselles[i][j].setEnabled(false);
                }
            }

        }
    }

    // TRUE IF FIELD IS OCCUPIED
    public boolean fieldOccupied(int[] field) {
        boolean occupied = false;
        for (int i = 0; i < game.pieces.length; i++) {
            if (Arrays.equals(game.pieces[i].position, field)) {
                occupied = true;
                break;
            }
        }
        return occupied;
    }

    public void changeImageIcon(int[] field1, int[] field2, PIECE piece) {
        caselles[field1[0]][field1[1]].setIcon(null);
        switch (piece.getClass().toString()) {
            case "class KNIGHT" -> {
                if (piece.color == 0) {
                    try {
                        caselles[field2[0]][field2[1]].setIcon(whiteKnight);
                    } catch (Exception ex) {
                        System.out.println("ex");
                    }
                } else {
                    try {
                        caselles[field2[0]][field2[1]].setIcon(blackKnight);
                    } catch (Exception ex) {
                        System.out.println("ex");
                    }
                }
            }
            case "class PAWN" -> {
                if (piece.color == 0) {
                    try {
                        caselles[field2[0]][field2[1]].setIcon(whitePawn);
                    } catch (Exception ex) {
                        System.out.println("ex");
                    }
                } else {
                    try {
                        caselles[field2[0]][field2[1]].setIcon(blackPawn);
                    } catch (Exception ex) {
                        System.out.println("ex");
                    }
                }
            }
            case "class QUEEN" -> {
                if (piece.color == 0) {
                    try {
                        caselles[field2[0]][field2[1]].setIcon(whiteQueen);
                    } catch (Exception ex) {
                        System.out.println("ex");
                    }
                } else {
                    try {
                        caselles[field2[0]][field2[1]].setIcon(blackQueen);
                    } catch (Exception ex) {
                        System.out.println("ex");
                    }
                }
            }
            case "class KING" -> {
                if (piece.color == 0) {
                    try {
                        caselles[field2[0]][field2[1]].setIcon(whiteKing);
                    } catch (Exception ex) {
                        System.out.println("ex");
                    }
                } else {
                    try {
                        caselles[field2[0]][field2[1]].setIcon(blackKing);
                    } catch (Exception ex) {
                        System.out.println("ex");
                    }
                }
            }
            case "class BISHOP" -> {
                if (piece.color == 0) {
                    try {
                        caselles[field2[0]][field2[1]].setIcon(whiteBishop);
                    } catch (Exception ex) {
                        System.out.println("ex");
                    }
                } else {
                    try {
                        caselles[field2[0]][field2[1]].setIcon(blackBishop);
                    } catch (Exception ex) {
                        System.out.println("ex");
                    }
                }
            }
            case "class ROOK" -> {
                if (piece.color == 0) {
                    try {
                        caselles[field2[0]][field2[1]].setIcon(whiteRook);
                    } catch (Exception ex) {
                        System.out.println("ex");
                    }
                } else {
                    try {
                        caselles[field2[0]][field2[1]].setIcon(blackRook);
                    } catch (Exception ex) {
                        System.out.println("ex");
                    }
                }
            }
        }

    }

    //MOOVES A PIECE: CHANGES POSITION IN GAME, CHANGES IMG, CHANGES PLAYER MOOVING
    public void moove(int[] field1, int[] field2) {
        if (game.isFieldOccupied(field2)) {
            game.killPiece(field2);
        }
        changeImageIcon(field1, field2, game.getPiece(field1));
        game.playerChange();
        game.moovePiece(game.getPiece(field1), field2);
        resetFieldColors();
        resetFocus();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(newGameButton)) {
            menu.setVisible(false);
            try {
                add(displayGame());
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            gameBackground.setVisible(true);
        }
        if (e.getSource().equals(saveButton)) {
            if (gameName.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Introduce game name", "Error", JOptionPane.PLAIN_MESSAGE);
            } else {
                try {
                    game.saveGame(gameName.getText());
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                gameBackground.setVisible(true);
            }
        }
        if (e.getSource().equals(loadGame)) {
            String[] saves;
            try {
                saves = game.getSavedGames();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            if (saves[0].equals("-1")) {
                JOptionPane.showMessageDialog(null, "No saved games found", "Error", JOptionPane.PLAIN_MESSAGE);
            }else {
                menu.setVisible(false);
                try {
                    add(loadGameMenu());
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
            }

            if (e.getSource() instanceof JButton) {
                int[] selected = new int[]{Integer.parseInt(String.valueOf(((JButton) e.getSource()).getName().charAt(0))), Integer.parseInt(String.valueOf(((JButton) e.getSource()).getName().charAt(1)))};
                if (!fieldOccupied(selected)) {
                    moove(selected1, selected);
                } else {
                    if (game.getPiece(selected).color != game.playerMoving) {
                        moove(selected1, selected);
                    } else {
                        resetFocus();
                        resetFieldColors();
                        selected1 = new int[]{Integer.parseInt(String.valueOf(((JButton) e.getSource()).getName().charAt(0))), Integer.parseInt(String.valueOf(((JButton) e.getSource()).getName().charAt(1)))};
                        changeButtonColors(selected1);
                    }
                }
            }
        }
    }

