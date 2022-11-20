import java.io.*;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class GAME {
    PIECE[] pieces;
    int playerMoving;   //0 = BLANQUES

    public GAME() throws FileNotFoundException {
        this.pieces = newPieces();
        this.playerMoving = 0;
    }

    //CREATE ALL PIECES
    public PIECE[] newPieces() {
        PIECE[] pieces = new PIECE[32];
        pieces[0] = new KNIGHT(new int[]{1, 0}, 0);
        pieces[1] = new KNIGHT(new int[]{1, 1}, 0);
        pieces[2] = new KNIGHT(new int[]{1, 2}, 0);
        pieces[3] = new KNIGHT(new int[]{1, 3}, 0);
        pieces[4] = new KNIGHT(new int[]{1, 4}, 0);
        pieces[5] = new KNIGHT(new int[]{1, 5}, 0);
        pieces[6] = new KNIGHT(new int[]{1, 6}, 0);
        pieces[7] = new KNIGHT(new int[]{1, 7}, 0);
        pieces[8] = new PAWN(new int[]{0, 2}, 0);
        pieces[9] = new PAWN(new int[]{0, 5}, 0);
        pieces[10] = new ROOK(new int[]{0, 0}, 0);
        pieces[11] = new ROOK(new int[]{0, 7}, 0);
        pieces[12] = new BISHOP(new int[]{0, 1}, 0);
        pieces[13] = new BISHOP(new int[]{0, 6}, 0);
        pieces[14] = new QUEEN(new int[]{0, 4}, 0);
        pieces[15] = new KING(new int[]{0, 3}, 0);
        pieces[16] = new KNIGHT(new int[]{6, 0}, 1);
        pieces[17] = new KNIGHT(new int[]{6, 1}, 1);
        pieces[18] = new KNIGHT(new int[]{6, 2}, 1);
        pieces[19] = new KNIGHT(new int[]{6, 3}, 1);
        pieces[20] = new KNIGHT(new int[]{6, 4}, 1);
        pieces[21] = new KNIGHT(new int[]{6, 5}, 1);
        pieces[22] = new KNIGHT(new int[]{6, 6}, 1);
        pieces[23] = new KNIGHT(new int[]{6, 7}, 1);
        pieces[24] = new PAWN(new int[]{7, 2}, 1);
        pieces[25] = new PAWN(new int[]{7, 5}, 1);
        pieces[26] = new ROOK(new int[]{7, 0}, 1);
        pieces[27] = new ROOK(new int[]{7, 7}, 1);
        pieces[28] = new BISHOP(new int[]{7, 1}, 1);
        pieces[29] = new BISHOP(new int[]{7, 6}, 1);
        pieces[30] = new QUEEN(new int[]{7, 4}, 1);
        pieces[31] = new KING(new int[]{7, 3}, 1);

        return pieces;
    }

    // cambia el jugador activo

    public void playerChange() {
        if (playerMoving == 0) {
            playerMoving = 1;
        } else {
            playerMoving = 0;
        }
    }

    public void loadGame(String file) throws FileNotFoundException {
        try {
            BufferedReader br = new BufferedReader(new FileReader("D:\\fp\\java\\provaGUI\\src\\savedgames/" + file));
            String all = br.readLine();
            System.out.println(all);
            String[] loadedPieces = all.split(",");
            System.out.println(Arrays.toString(loadedPieces));
            System.out.println(loadedPieces.length);
            playerMoving = Integer.parseInt(loadedPieces[0]);
            ArrayList<PIECE> newPieces = new ArrayList<>();
            for (int i = 1; i < loadedPieces.length; i++) {
                String[] newStr = loadedPieces[i].split("@");
                StringBuilder newPieceString = new StringBuilder();
                for (int j = 6; j < newStr[0].length(); j++) {
                    newPieceString.append(newStr[0].charAt(j));
                }
                switch (newPieceString.toString()) {
                    case "PAWN" -> {
                        newPieces.add(new PAWN(new int[]{Character.getNumericValue(newStr[1].charAt(0)),Character.getNumericValue(newStr[1].charAt(1))},Character.getNumericValue(newStr[1].charAt(2))));
                    }
                    case "ROOK" -> {
                        newPieces.add(new ROOK(new int[]{Character.getNumericValue(newStr[1].charAt(0)),Character.getNumericValue(newStr[1].charAt(1))},Character.getNumericValue(newStr[1].charAt(2))));
                    }
                    case "BISHOP" -> {
                        newPieces.add(new BISHOP(new int[]{Character.getNumericValue(newStr[1].charAt(0)),Character.getNumericValue(newStr[1].charAt(1))},Character.getNumericValue(newStr[1].charAt(2))));
                    }
                    case "KNIGHT" -> {
                        newPieces.add(new KNIGHT(new int[]{Character.getNumericValue(newStr[1].charAt(0)),Character.getNumericValue(newStr[1].charAt(1))},Character.getNumericValue(newStr[1].charAt(2))));
                    }
                    case "QUEEN" -> {
                        newPieces.add(new QUEEN(new int[]{Character.getNumericValue(newStr[1].charAt(0)),Character.getNumericValue(newStr[1].charAt(1))},Character.getNumericValue(newStr[1].charAt(2))));
                    }
                    case "KING" -> {
                        newPieces.add(new KING(new int[]{Character.getNumericValue(newStr[1].charAt(0)),Character.getNumericValue(newStr[1].charAt(1))},Character.getNumericValue(newStr[1].charAt(2))));
                    }

                }
            }
            PIECE[] pc = new PIECE[newPieces.size()];
            pieces = newPieces.toArray(pc);
            for (PIECE piece : pieces) {
                System.out.println(Arrays.toString(piece.position));
            }
        } catch (Exception ex) {
            System.out.println("file not found");
        }
    }

    // GET ALL FILES FROM DIR savedgames
    public String[] getSavedGames() throws IOException {
        ArrayList<String> arrList = new ArrayList<>();
        Path dir = Path.of("D:\\fp\\java\\provaGUI\\src\\savedgames/");
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
            Iterator<Path> it = stream.iterator();
            if (it.hasNext()) {
                do {
                    String fileName = it.next().getFileName().toString();
                    arrList.add(fileName);
                }
                while (it.hasNext());
            } else {
                arrList.add("-1");
            }
        }
        //Arraylist to String[][]
        String[] string = new String[arrList.size()];
        string = arrList.toArray(string);
        return string;
    }

    public void saveGame(String gameName) throws IOException {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("_dd_MM_yyyy-HH_mm_ss");
        LocalDateTime now = LocalDateTime.now();
        String path = "D:\\fp\\java\\provaGUI\\src\\savedgames/" + gameName + dtf.format(now) + ".txt";
        Files.createFile(Path.of(path));
        try {
            FileOutputStream fos = new FileOutputStream(path);
            fos.write(String.valueOf(playerMoving).getBytes());
            fos.write(",".getBytes());
            for (PIECE piece : pieces) {
                fos.write(piece.getClass().toString().getBytes());
                fos.write(String.valueOf('@').getBytes());
                fos.write(String.valueOf(piece.position[0]).getBytes());
                fos.write(String.valueOf(piece.position[1]).getBytes());
                fos.write(String.valueOf(piece.color).getBytes());
                fos.write(",".getBytes());
            }
            fos.close();
            System.out.println("Game saved");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    //CAMBIA LA POSICION DE UNA PIEZA
    public void moovePiece(PIECE piece, int[] position) {
        pieces[getPieceIndex(piece)].position = position;
    }

    // IF FIELD IS OCCUPIED RETURNS TRUE
    public boolean isFieldOccupied(int[] field) {
        boolean found = false;
        for (PIECE piece : pieces) {
            if (Arrays.equals(field, piece.position)) {
                found = true;
                break;
            }
        }
        return found;
    }

    //RETURN PIECE INDEX FROM PIECE DONE
    public int getPieceIndex(PIECE piece) {
        int index = 0;
        for (int i = 0; i < pieces.length; i++) {
            if (pieces[i].equals(piece)) {
                index = i;
                break;
            }
        }
        return index;
    }

    public PIECE getPiece(int[] position) {
        PIECE result = null;
        for (PIECE piece : pieces) {
            if (Arrays.equals(piece.position, position)) {
                result = piece;
                break;
            }

        }
        return result;
    }

    //KILLS PIECE AT POSITION DONE
    public void killPiece(int[] position) {

        ArrayList<PIECE> newPieces = new ArrayList<>();
        for (PIECE piece : pieces) {
            if (!Arrays.equals(position, piece.position)) {
                newPieces.add(piece);
            }
        }
        PIECE[] result = new PIECE[newPieces.size()];
        pieces = newPieces.toArray(result);
    }


    //TORNA TRUE SI LA CASELLA ESTÀ ENTRE 0 I 7
    public boolean fieldValid(int[] field) {
        return (field[0] >= 0 && field[0] < 8) && (field[1] >= 0 && field[1] < 8);
    }

    //RETORNA TOTES LES CASELLES ON ES POT MOURE LA PEÇA QUE LI ARRIBA
    public int[][] possibleMovements(PIECE piece) {
        ArrayList<int[]> newArray = new ArrayList<>();
        boolean running = true;
        int iOrigen = piece.position[0];
        int jOrigen = piece.position[1];

        switch (piece.getClass().toString()) {
            //MOVIMENTS POSSIBLES ALFIL
            case "class PAWN" -> {
                if (jOrigen != 7 && iOrigen != 7) {
                    while (running) {
                        iOrigen++;
                        jOrigen++;
                        if (isFieldOccupied(new int[]{iOrigen, jOrigen})) {
                            if (getPiece(new int[]{iOrigen, jOrigen}).color != piece.color) {
                                newArray.add(new int[]{iOrigen, jOrigen});
                            }
                            running = false;
                        } else {
                            newArray.add(new int[]{iOrigen, jOrigen});
                        }
                        if (iOrigen == 7 || jOrigen == 7) {
                            running = false;
                        }
                    }
                    running = true;
                }
                iOrigen = piece.position[0];
                jOrigen = piece.position[1];
                if (iOrigen != 7 && jOrigen != 0) {
                    while (running) {
                        iOrigen++;
                        jOrigen--;
                        if (isFieldOccupied(new int[]{iOrigen, jOrigen})) {
                            if (getPiece(new int[]{iOrigen, jOrigen}).color != piece.color) {
                                newArray.add(new int[]{iOrigen, jOrigen});
                            }
                            running = false;
                        } else {
                            newArray.add(new int[]{iOrigen, jOrigen});
                        }
                        if (iOrigen == 7 || jOrigen == 0) {
                            running = false;
                        }
                    }
                    running = true;
                }
                iOrigen = piece.position[0];
                jOrigen = piece.position[1];
                if (iOrigen != 0 && jOrigen != 7) {
                    while (running) {
                        iOrigen--;
                        jOrigen++;
                        if (isFieldOccupied(new int[]{iOrigen, jOrigen})) {
                            if (getPiece(new int[]{iOrigen, jOrigen}).color != piece.color) {
                                newArray.add(new int[]{iOrigen, jOrigen});
                            }
                            running = false;
                        } else {
                            newArray.add(new int[]{iOrigen, jOrigen});
                        }
                        if (iOrigen == 0 || jOrigen == 7) {
                            running = false;
                        }
                    }
                    running = true;
                }
                iOrigen = piece.position[0];
                jOrigen = piece.position[1];
                if (iOrigen != 0 && jOrigen != 0) {
                    while (running) {
                        iOrigen--;
                        jOrigen--;
                        if (isFieldOccupied(new int[]{iOrigen, jOrigen})) {
                            if (getPiece(new int[]{iOrigen, jOrigen}).color != piece.color) {
                                newArray.add(new int[]{iOrigen, jOrigen});
                            }
                            running = false;
                        } else {
                            newArray.add(new int[]{iOrigen, jOrigen});
                        }
                        if (iOrigen == 0 || jOrigen == 0) {
                            running = false;
                        }
                    }
                }
            }
            //cavall
            case "class BISHOP" -> {
                if (fieldValid(new int[]{iOrigen + 2, jOrigen + 1})) {
                    if (isFieldOccupied(new int[]{iOrigen + 2, jOrigen + 1})) {
                        if (getPiece(new int[]{iOrigen + 2, jOrigen + 1}).color != playerMoving) {
                            newArray.add(new int[]{iOrigen + 2, jOrigen + 1});
                        }
                    } else {
                        newArray.add(new int[]{iOrigen + 2, jOrigen + 1});
                    }
                }
                if (fieldValid(new int[]{iOrigen + 2, jOrigen - 1})) {
                    if (isFieldOccupied(new int[]{iOrigen + 2, jOrigen - 1})) {
                        if (getPiece(new int[]{iOrigen + 2, jOrigen - 1}).color != playerMoving) {
                            newArray.add(new int[]{iOrigen + 2, jOrigen - 1});
                        }
                    } else {
                        newArray.add(new int[]{iOrigen + 2, jOrigen - 1});
                    }
                }
                if (fieldValid(new int[]{iOrigen + 1, jOrigen + 2})) {
                    if (isFieldOccupied(new int[]{iOrigen + 1, jOrigen + 2})) {
                        if (getPiece(new int[]{iOrigen + 1, jOrigen + 2}).color != playerMoving) {
                            newArray.add(new int[]{iOrigen + 1, jOrigen + 2});
                        }
                    } else {
                        newArray.add(new int[]{iOrigen + 1, jOrigen + 2});
                    }
                }
                if (fieldValid(new int[]{iOrigen + 1, jOrigen - 2})) {
                    if (isFieldOccupied(new int[]{iOrigen + 1, jOrigen - 2})) {
                        if (getPiece(new int[]{iOrigen + 1, jOrigen - 2}).color != playerMoving) {
                            newArray.add(new int[]{iOrigen + 1, jOrigen - 2});
                        }
                    } else {
                        newArray.add(new int[]{iOrigen + 1, jOrigen - 2});
                    }
                }
                if (fieldValid(new int[]{iOrigen - 2, jOrigen + 1})) {
                    if (isFieldOccupied(new int[]{iOrigen - 2, jOrigen + 1})) {
                        if (getPiece(new int[]{iOrigen - 2, jOrigen + 1}).color != playerMoving) {
                            newArray.add(new int[]{iOrigen - 2, jOrigen + 1});
                        }
                    } else {
                        newArray.add(new int[]{iOrigen - 2, jOrigen + 1});
                    }
                }
                if (fieldValid(new int[]{iOrigen - 2, jOrigen - 1})) {
                    if (isFieldOccupied(new int[]{iOrigen - 2, jOrigen - 1})) {
                        if (getPiece(new int[]{iOrigen - 2, jOrigen - 1}).color != playerMoving) {
                            newArray.add(new int[]{iOrigen - 2, jOrigen - 1});
                        }
                    } else {
                        newArray.add(new int[]{iOrigen - 2, jOrigen - 1});
                    }
                }
                if (fieldValid(new int[]{iOrigen - 1, jOrigen + 2})) {
                    if (isFieldOccupied(new int[]{iOrigen - 1, jOrigen + 2})) {
                        if (getPiece(new int[]{iOrigen - 1, jOrigen + 2}).color != playerMoving) {
                            newArray.add(new int[]{iOrigen - 1, jOrigen + 2});
                        }
                    } else {
                        newArray.add(new int[]{iOrigen - 1, jOrigen + 2});
                    }
                }
                if (fieldValid(new int[]{iOrigen - 1, jOrigen - 2})) {
                    if (isFieldOccupied(new int[]{iOrigen - 1, jOrigen - 2})) {
                        if (getPiece(new int[]{iOrigen - 1, jOrigen - 2}).color != playerMoving) {
                            newArray.add(new int[]{iOrigen - 1, jOrigen - 2});
                        }
                    } else {
                        newArray.add(new int[]{iOrigen - 1, jOrigen - 2});
                    }
                }
            }
            case "class ROOK" -> {
                if (iOrigen != 7) {
                    while (running) {
                        iOrigen++;
                        if (!isFieldOccupied(new int[]{iOrigen, jOrigen})) {
                            newArray.add(new int[]{iOrigen, jOrigen});
                        } else if (isFieldOccupied(new int[]{iOrigen, jOrigen}) && getPiece(new int[]{iOrigen, jOrigen}).color != piece.color) {
                            newArray.add(new int[]{iOrigen, jOrigen});
                            running = false;
                        } else {
                            running = false;
                        }
                        if (iOrigen == 7) {
                            running = false;
                        }
                    }
                    running = true;
                }
                iOrigen = piece.position[0];
                jOrigen = piece.position[1];
                if (iOrigen != 0) {
                    while (running) {
                        iOrigen--;
                        if (!isFieldOccupied(new int[]{iOrigen, jOrigen})) {
                            newArray.add(new int[]{iOrigen, jOrigen});
                        } else if (isFieldOccupied(new int[]{iOrigen, jOrigen}) && getPiece(new int[]{iOrigen, jOrigen}).color != piece.color) {
                            newArray.add(new int[]{iOrigen, jOrigen});
                            running = false;
                        } else {
                            running = false;
                        }
                        if (iOrigen == 0) {
                            running = false;
                        }
                    }
                    running = true;
                }
                iOrigen = piece.position[0];
                jOrigen = piece.position[1];
                if (jOrigen != 7) {
                    while (running) {
                        jOrigen++;
                        if (!isFieldOccupied(new int[]{iOrigen, jOrigen})) {
                            newArray.add(new int[]{iOrigen, jOrigen});
                        } else if (isFieldOccupied(new int[]{iOrigen, jOrigen}) && getPiece(new int[]{iOrigen, jOrigen}).color != piece.color) {
                            newArray.add(new int[]{iOrigen, jOrigen});
                            running = false;
                        } else {
                            running = false;
                        }
                        if (jOrigen == 7) {
                            running = false;
                        }
                    }
                    running = true;
                }
                iOrigen = piece.position[0];
                jOrigen = piece.position[1];
                if (jOrigen != 0) {
                    while (running) {
                        jOrigen--;
                        if (!isFieldOccupied(new int[]{iOrigen, jOrigen})) {
                            newArray.add(new int[]{iOrigen, jOrigen});
                        } else if (isFieldOccupied(new int[]{iOrigen, jOrigen}) && getPiece(new int[]{iOrigen, jOrigen}).color != piece.color) {
                            newArray.add(new int[]{iOrigen, jOrigen});
                            running = false;
                        } else {
                            running = false;
                        }
                        if (jOrigen == 0) {
                            running = false;
                        }
                    }
                }
            }
            case "class KING" -> {
                if (iOrigen + 1 < 8 && (!isFieldOccupied(new int[]{iOrigen + 1, jOrigen}) || getPiece(new int[]{iOrigen + 1, jOrigen}).color != piece.color)) {
                    newArray.add(new int[]{iOrigen + 1, jOrigen});
                }
                if (iOrigen + 1 < 8 && jOrigen + 1 < 7 && (!isFieldOccupied(new int[]{iOrigen + 1, jOrigen}) || getPiece(new int[]{iOrigen + 1, jOrigen + 1}).color != piece.color)) {
                    newArray.add(new int[]{iOrigen + 1, jOrigen + 1});
                }
                if (jOrigen + 1 < 7 && (!isFieldOccupied(new int[]{iOrigen, jOrigen + 1}) || getPiece(new int[]{iOrigen, jOrigen + 1}).color != piece.color)) {
                    newArray.add(new int[]{iOrigen, jOrigen + 1});
                }
                if (iOrigen - 1 > 0 && (!isFieldOccupied(new int[]{iOrigen - 1, jOrigen}) || getPiece(new int[]{iOrigen - 1, jOrigen}).color != piece.color)) {
                    newArray.add(new int[]{iOrigen - 1, jOrigen});
                }
                if (iOrigen - 1 > 0 && jOrigen - 1 > 0 && (!isFieldOccupied(new int[]{iOrigen - 1, jOrigen - 1}) || getPiece(new int[]{iOrigen - 1, jOrigen - 1}).color != piece.color)) {
                    newArray.add(new int[]{iOrigen - 1, jOrigen - 1});
                }
                if (jOrigen - 1 > 0 && (!isFieldOccupied(new int[]{iOrigen, jOrigen - 1}) || getPiece(new int[]{iOrigen, jOrigen - 1}).color != piece.color)) {
                    newArray.add(new int[]{iOrigen, jOrigen - 1});
                }
            }
            case "class KNIGHT" -> {
                if (piece.color == 0) {
                    if (!isFieldOccupied(new int[]{iOrigen + 1, jOrigen})) {
                        newArray.add(new int[]{iOrigen + 1, jOrigen});
                    }
                    if (!isFieldOccupied(new int[]{iOrigen + 1, jOrigen}) && !isFieldOccupied(new int[]{iOrigen + 2, jOrigen}) && iOrigen == 1) {
                        newArray.add(new int[]{iOrigen + 2, jOrigen});
                    }
                    if (isFieldOccupied(new int[]{iOrigen + 1, jOrigen + 1}) && getPiece(new int[]{iOrigen + 1, jOrigen + 1}).color != piece.color) {
                        newArray.add(new int[]{iOrigen + 1, jOrigen + 1});
                    }
                    if (isFieldOccupied(new int[]{iOrigen + 1, jOrigen - 1}) && getPiece(new int[]{iOrigen + 1, jOrigen - 1}).color != piece.color) {
                        newArray.add(new int[]{iOrigen + 1, jOrigen - 1});
                    }
                } else {
                    if (!isFieldOccupied(new int[]{iOrigen - 1, jOrigen})) {
                        newArray.add(new int[]{iOrigen - 1, jOrigen});
                    }
                    if (!isFieldOccupied(new int[]{iOrigen - 1, jOrigen}) && !isFieldOccupied(new int[]{iOrigen - 2, jOrigen}) && iOrigen == 6) {
                        newArray.add(new int[]{iOrigen - 2, jOrigen});
                    }
                    if (isFieldOccupied(new int[]{iOrigen - 1, jOrigen - 1}) && getPiece(new int[]{iOrigen - 1, jOrigen - 1}).color != piece.color) {
                        newArray.add(new int[]{iOrigen - 1, jOrigen - 1});
                    }
                    if (isFieldOccupied(new int[]{iOrigen - 1, jOrigen + 1}) && getPiece(new int[]{iOrigen - 1, jOrigen + 1}).color != piece.color) {
                        newArray.add(new int[]{iOrigen - 1, jOrigen + 1});
                    }
                }
            }
            case "class QUEEN" -> {
                if (iOrigen != 7 && jOrigen != 7) {
                    while (running) {
                        iOrigen++;
                        jOrigen++;
                        if (isFieldOccupied(new int[]{iOrigen, jOrigen})) {
                            if (getPiece(new int[]{iOrigen, jOrigen}).color != piece.color) {
                                newArray.add(new int[]{iOrigen, jOrigen});
                            }
                            running = false;
                        } else {
                            newArray.add(new int[]{iOrigen, jOrigen});
                        }
                        if (iOrigen == 7 || jOrigen == 7) {
                            running = false;
                        }
                    }
                    running = true;
                    iOrigen = piece.position[0];
                    jOrigen = piece.position[1];
                }
                if (iOrigen != 7 && jOrigen != 0) {
                    while (running) {
                        iOrigen++;
                        jOrigen--;
                        if (isFieldOccupied(new int[]{iOrigen, jOrigen})) {
                            if (getPiece(new int[]{iOrigen, jOrigen}).color != piece.color) {
                                newArray.add(new int[]{iOrigen, jOrigen});
                            }
                            running = false;
                        } else {
                            newArray.add(new int[]{iOrigen, jOrigen});
                        }
                        if (iOrigen == 7 || jOrigen == 0) {
                            running = false;
                        }
                    }
                    running = true;
                    iOrigen = piece.position[0];
                    jOrigen = piece.position[1];
                }
                if (iOrigen != 0 && jOrigen != 7) {
                    while (running) {
                        iOrigen--;
                        jOrigen++;
                        if (isFieldOccupied(new int[]{iOrigen, jOrigen})) {
                            if (getPiece(new int[]{iOrigen, jOrigen}).color != piece.color) {
                                newArray.add(new int[]{iOrigen, jOrigen});
                            }
                            running = false;
                        } else {
                            newArray.add(new int[]{iOrigen, jOrigen});
                        }
                        if (iOrigen == 0 || jOrigen == 7) {
                            running = false;
                        }
                    }
                    running = true;
                    iOrigen = piece.position[0];
                    jOrigen = piece.position[1];
                }
                if (iOrigen != 0 && jOrigen != 0) {
                    while (running) {
                        iOrigen--;
                        jOrigen--;
                        if (isFieldOccupied(new int[]{iOrigen, jOrigen})) {
                            if (getPiece(new int[]{iOrigen, jOrigen}).color != piece.color) {
                                newArray.add(new int[]{iOrigen, jOrigen});
                            }
                            running = false;
                        } else {
                            newArray.add(new int[]{iOrigen, jOrigen});
                            ;
                        }
                        if (iOrigen == 0 || jOrigen == 0) {
                            running = false;
                        }
                    }
                    running = true;
                    iOrigen = piece.position[0];
                    jOrigen = piece.position[1];
                }
                if (iOrigen != 7) {
                    while (running) {
                        iOrigen++;
                        if (!isFieldOccupied(new int[]{iOrigen, jOrigen})) {
                            newArray.add(new int[]{iOrigen, jOrigen});
                        } else if (isFieldOccupied(new int[]{iOrigen, jOrigen}) && getPiece(new int[]{iOrigen, jOrigen}).color != piece.color) {
                            newArray.add(new int[]{iOrigen, jOrigen});
                            running = false;
                        } else {
                            running = false;
                        }
                        if (iOrigen == 7) {
                            running = false;
                        }
                    }
                    running = true;
                    iOrigen = piece.position[0];
                    jOrigen = piece.position[1];
                }
                if (iOrigen != 0) {
                    while (running) {
                        iOrigen--;
                        if (!isFieldOccupied(new int[]{iOrigen, jOrigen})) {
                            newArray.add(new int[]{iOrigen, jOrigen});
                        } else if (isFieldOccupied(new int[]{iOrigen, jOrigen}) && getPiece(new int[]{iOrigen, jOrigen}).color != piece.color) {
                            newArray.add(new int[]{iOrigen, jOrigen});
                            running = false;
                        } else {
                            running = false;
                        }
                        if (iOrigen == 0) {
                            running = false;
                        }
                    }
                    running = true;
                    iOrigen = piece.position[0];
                    jOrigen = piece.position[1];
                }
                if (jOrigen != 7) {
                    while (running) {
                        jOrigen++;
                        if (!isFieldOccupied(new int[]{iOrigen, jOrigen})) {
                            newArray.add(new int[]{iOrigen, jOrigen});
                        } else if (isFieldOccupied(new int[]{iOrigen, jOrigen}) && getPiece(new int[]{iOrigen, jOrigen}).color != piece.color) {
                            newArray.add(new int[]{iOrigen, jOrigen});
                            running = false;
                        } else {
                            running = false;
                        }
                        if (jOrigen == 7) {
                            running = false;
                        }
                    }
                    running = true;
                    iOrigen = piece.position[0];
                    jOrigen = piece.position[1];
                }
                if (jOrigen != 0) {
                    while (running) {
                        jOrigen--;
                        if (!isFieldOccupied(new int[]{iOrigen, jOrigen})) {
                            newArray.add(new int[]{iOrigen, jOrigen});
                        } else if (isFieldOccupied(new int[]{iOrigen, jOrigen}) && getPiece(new int[]{iOrigen, jOrigen}).color != piece.color) {
                            newArray.add(new int[]{iOrigen, jOrigen});
                            running = false;
                        } else {
                            running = false;
                        }
                        if (jOrigen == 0) {
                            running = false;
                        }
                    }
                }
            }
        }
        // return possibleMovements;
        int[][] prova = new int[2][newArray.size()];
        prova = newArray.toArray(prova);
        return prova;
    }
}

