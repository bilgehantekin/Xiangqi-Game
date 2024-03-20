import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Game extends AbstractGame{
    private int count = 0;

    String [] charArray = new String[] {"a","b","c","d","e","f","g","h","i","j"};
    String [] possibilities = new String[90];
    boolean isGameOver = false;




    public Game(String player1, String player2) {
        board = new Board();
        board.firstBoard();
        ArrayList<Item> item1 = new ArrayList<>();
        ArrayList<Item> item2 = new ArrayList<>();
        for(int i = 0; i < 16; i++) {
            item1.add(board.items[i]);
        }
        for(int i = 16; i < 32; i++) {
            item2.add(board.items[i]);
        }
        red = new Player(player1, 0, item1 );
        black = new Player(player2, 0, item2);
        int number = 0;
        for(int i = 0; i < charArray.length; i++) {
            for(int j = 1; j <= 9; j++) {
                possibilities[number] = charArray[i] + j;
                number++;
            }
        }
    }

    @Override
     void play(String from, String to) {
        int index = -1;
        for(int i = 0; i < board.items.length; i++) {
            if(board.items[i].getPosition().equals(from)) {
                index = i;
            }
            if(from.equals("xx")) {
                index = -1;
            }

        }



        try {
            if(from.length() != 2 || to.length() != 2 || to.equals("xx") || index == -1 || isGameOver) {
                System.out.println("hatali hareket");
            }
        else if (isLegal(index, from, to)) {
            count++;
        }
        else {
            System.out.println("hatali hareket");
        } } catch (Exception e) {
        }



    }

    @Override
    void save_binary(String address) {

        ObjectOutputStream o = null;
        try {
            o = new ObjectOutputStream(new FileOutputStream(address));
            int countNumber = count;
            float pointRed = red.puan;
            float pointBlack = black.puan;

            for(int i = 0; i < board.items.length; i++) {
                o.writeUTF(board.items[i].getPosition());
            }
            o.writeInt(countNumber);
            o.writeFloat(pointRed);
            o.writeFloat(pointBlack);
            o.close();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    void save_text(String address) {
        PrintWriter out = null;

        try {
            out = new PrintWriter(new FileOutputStream(address));
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        int countNumber = count;
        float pointRed = red.puan;
        float pointBlack = black.puan;

        for(int i = 0; i < board.items.length; i++) {
            out.println(board.items[i].getPosition());
        }
        out.println(countNumber);
        out.println(pointRed);
        out.println(pointBlack);
        out.close();
    }

    @Override
    void load_text(String address) {
        Scanner s = null;
        try {
            s = new Scanner(new FileInputStream(address));
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        String [] array = new String[32];
        for(int i = 0; i < array.length; i++) {
            array[i] = s.nextLine();
        }
        int countNumber = s.nextInt();
        float pointRed = s.nextFloat();
        float pointBlack = s.nextFloat();

        for(int i = 0; i < board.items.length; i++) {
            board.items[i].setPosition(array[i]);
        }
        count = countNumber;
        red.puan = pointRed;
        black.puan = pointBlack;

    }

    @Override
    void load_binary(String address) {
        ObjectInputStream r = null;
        try {
            r = new ObjectInputStream(new FileInputStream(address));
            String [] array = new String[32];
            for(int i = 0; i < array.length; i++) {
                array[i] = r.readUTF();
            }
            int countNumber = r.readInt();
            float pointRed = r.readFloat();
            float pointBlack = r.readFloat();

            for(int i = 0; i < board.items.length; i++) {
                board.items[i].setPosition(array[i]);
            }
            count = countNumber;
            red.puan = pointRed;
            black.puan = pointBlack;

            r.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public Board getBoard() {
        return this.board;
    }
    public boolean isLegal(int index, String from, String to) {
        int position1 = Integer.parseInt(from.substring(1, 2));
        int position2 = Integer.parseInt(to.substring(1, 2));
        String positionx = from.substring(0, 1);
        String positiony = to.substring(0, 1);
        boolean isEaten = false;
        int eatenIndex = -1;
        int indexx = -1, indexy = -1;
        for(int i = 0; i < charArray.length; i++) {
            if (positionx.equals(charArray[i])) {
                indexx = i;
            }
            if (positiony.equals(charArray[i])) {
                indexy = i;
            }
        }
        if(indexx == -1 || indexy == -1) {
            return false;
        }
        if(from.length() != 2 || to.length() != 2) {
            return false;
        }
        if (!board.items[index].getPosition().equals(from)) {
            return false;
        }
        if (index == 0 || index == 8 || index == 16 || index == 24) {
            if(areGeneralsLegal(index, from, to, indexx, indexy, position1, position2, charArray) && Kale(index, from, to, indexx, indexy, position1, position2, charArray)) {
                if(count % 2 == 0) {
                    board.items[index].setPosition(to);
                    red.item.add(index, board.items[index]);
                    red.item.remove(index + 1);
                    for(int i = 0; i < board.items.length / 2; i++) {
                        if(to.equals(black.item.get(i).getPosition())) {
                            isEaten = true;
                            eatenIndex = i;
                            board.items[i + 16].setPosition("xx");
                            black.item.add(i, board.items[i + 16]);
                            black.item.remove(i + 1);
                            if(i == 0 || i == 8) {
                                red.puan += 9;
                            }
                            if(i == 1 || i == 7) {
                                red.puan += 4;
                            }
                            if(i == 2 || i == 6) {
                                red.puan += 2;
                            }
                            if(i == 3 || i == 5) {
                                red.puan += 2;
                            }
                            if(i == 9 || i == 10) {
                                red.puan += 4.5;
                            }
                            if(i == 11 || i == 12 || i == 13 || i == 14 || i == 15) {
                                red.puan += 1;
                            }
                        }
                    }
                    if(isStillAndStillCheck() == 0) {
                        isGameOver = true;
                        System.out.println("ŞAH MAT! " + red.name + " oyunu kazandı. " + red.name + "'in puanı: " + red.puan + ", " + black.name + "'nin puanı: " + black.puan);
                        return true;
                    }
                    if(isStillCheck()) {
                        board.items[index].setPosition(from);
                        red.item.add(index, board.items[index]);
                        red.item.remove(index + 1);
                        if(isEaten) {
                            board.items[eatenIndex + 16].setPosition(to);
                            black.item.add(eatenIndex, board.items[eatenIndex + 16]);
                            black.item.remove(eatenIndex + 1);
                            if(eatenIndex == 0 || eatenIndex == 8) {
                                red.puan -= 9;
                            }
                            if(eatenIndex == 1 || eatenIndex == 7) {
                                red.puan -= 4;
                            }
                            if(eatenIndex == 2 || eatenIndex == 6) {
                                red.puan -= 2;
                            }
                            if(eatenIndex == 3 || eatenIndex == 5) {
                                red.puan -= 2;
                            }
                            if(eatenIndex == 9 || eatenIndex == 10) {
                                red.puan -= 4.5;
                            }
                            if(eatenIndex == 11 || eatenIndex == 12 || eatenIndex == 13 || eatenIndex == 14 || eatenIndex == 15) {
                                red.puan -= 1;
                            }
                        }

                        return false;
                    }
                    return true;
                }
                else {
                    board.items[index].setPosition(to);
                    black.item.add(index - 16, board.items[index]);
                    black.item.remove(index - 15);
                    for(int i = 0; i < board.items.length / 2; i++) {
                        if(to.equals(red.item.get(i).getPosition())) {
                            isEaten = true;
                            eatenIndex = i;
                            board.items[i].setPosition("xx");
                            red.item.add(i, board.items[i]);
                            red.item.remove(i + 1);
                            if(i == 0 || i == 8) {
                                black.puan += 9;
                            }
                            if(i == 1 || i == 7) {
                                black.puan += 4;
                            }
                            if(i == 2 || i == 6) {
                                black.puan += 2;
                            }
                            if(i == 3 || i == 5) {
                                black.puan += 2;
                            }
                            if(i == 9 || i == 10) {
                                black.puan += 4.5;
                            }
                            if(i == 11 || i == 12 || i == 13 || i == 14 || i == 15) {
                                black.puan += 1;
                            }
                        }
                    }
                    if(isStillAndStillCheck() == 0) {
                        isGameOver = true;
                        System.out.println("ŞAH MAT! " + black.name + " oyunu kazandı. " + red.name + "'in puanı: " + red.puan + ", " + black.name + "'nin puanı: " + black.puan);
                        return true;
                    }
                    if(isStillCheck()) {
                        board.items[index].setPosition(from);
                        black.item.add(index - 16, board.items[index]);
                        black.item.remove(index - 15);
                        if(isEaten) {
                            board.items[eatenIndex].setPosition(to);
                            red.item.add(eatenIndex, board.items[eatenIndex]);
                            red.item.remove(eatenIndex + 1);
                            if(eatenIndex == 0 || eatenIndex == 8) {
                                black.puan -= 9;
                            }
                            if(eatenIndex == 1 || eatenIndex == 7) {
                                black.puan -= 4;
                            }
                            if(eatenIndex == 2 || eatenIndex == 6) {
                                black.puan -= 2;
                            }
                            if(eatenIndex == 3 || eatenIndex == 5) {
                                black.puan -= 2;
                            }
                            if(eatenIndex == 9 || eatenIndex == 10) {
                                black.puan -= 4.5;
                            }
                            if(eatenIndex == 11 || eatenIndex == 12 || eatenIndex == 13 || eatenIndex == 14 || eatenIndex == 15) {
                                black.puan -= 1;
                            }
                        }

                        return false;
                    }
                    return true;
                }
            }
            else {
                return false;
            }
        }
        if (index == 1 || index == 7 || index == 17 || index == 23) {
            if(areGeneralsLegal(index, from, to, indexx, indexy, position1, position2, charArray) && At(index, from, to, indexx, indexy, position1, position2, charArray)) {
                if (count % 2 == 0) {
                    board.items[index].setPosition(to);
                    red.item.add(index, board.items[index]);
                    red.item.remove(index + 1);
                    for(int i = 0; i < board.items.length / 2; i++) {
                        if(to.equals(black.item.get(i).getPosition())) {
                            isEaten = true;
                            eatenIndex = i;
                            board.items[i + 16].setPosition("xx");
                            black.item.add(i, board.items[i + 16]);
                            black.item.remove(i + 1);
                            if(i == 0 || i == 8) {
                                red.puan += 9;
                            }
                            if(i == 1 || i == 7) {
                                red.puan += 4;
                            }
                            if(i == 2 || i == 6) {
                                red.puan += 2;
                            }
                            if(i == 3 || i == 5) {
                                red.puan += 2;
                            }
                            if(i == 9 || i == 10) {
                                red.puan += 4.5;
                            }
                            if(i == 11 || i == 12 || i == 13 || i == 14 || i == 15) {
                                red.puan += 1;
                            }
                        }
                    }
                    if(isStillAndStillCheck() == 0) {
                        isGameOver = true;
                        System.out.println("ŞAH MAT! " + red.name + " oyunu kazandı. " + red.name + "'in puanı: " + red.puan + ", " + black.name + "'nin puanı: " + black.puan);
                        return true;
                    }
                    if(isStillCheck()) {
                        board.items[index].setPosition(from);
                        red.item.add(index, board.items[index]);
                        red.item.remove(index + 1);
                        if(isEaten) {
                            board.items[eatenIndex + 16].setPosition(to);
                            black.item.add(eatenIndex, board.items[eatenIndex + 16]);
                            black.item.remove(eatenIndex + 1);
                            if(eatenIndex == 0 || eatenIndex == 8) {
                                red.puan -= 9;
                            }
                            if(eatenIndex == 1 || eatenIndex == 7) {
                                red.puan -= 4;
                            }
                            if(eatenIndex == 2 || eatenIndex == 6) {
                                red.puan -= 2;
                            }
                            if(eatenIndex == 3 || eatenIndex == 5) {
                                red.puan -= 2;
                            }
                            if(eatenIndex == 9 || eatenIndex == 10) {
                                red.puan -= 4.5;
                            }
                            if(eatenIndex == 11 || eatenIndex == 12 || eatenIndex == 13 || eatenIndex == 14 || eatenIndex == 15) {
                                red.puan -= 1;
                            }
                        }

                        return false;
                    }
                    return true;
                }
                else {
                    board.items[index].setPosition(to);
                    black.item.add(index - 16, board.items[index]);
                    black.item.remove(index - 15);
                    for(int i = 0; i < board.items.length / 2; i++) {
                        if(to.equals(red.item.get(i).getPosition())) {
                            isEaten = true;
                            eatenIndex = i;
                            board.items[i].setPosition("xx");
                            red.item.add(i, board.items[i]);
                            red.item.remove(i + 1);
                            if(i == 0 || i == 8) {
                                black.puan += 9;
                            }
                            if(i == 1 || i == 7) {
                                black.puan += 4;
                            }
                            if(i == 2 || i == 6) {
                                black.puan += 2;
                            }
                            if(i == 3 || i == 5) {
                                black.puan += 2;
                            }
                            if(i == 9 || i == 10) {
                                black.puan += 4.5;
                            }
                            if(i == 11 || i == 12 || i == 13 || i == 14 || i == 15) {
                                black.puan += 1;
                            }
                        }
                    }
                    if(isStillAndStillCheck() == 0) {
                        isGameOver = true;
                        System.out.println("ŞAH MAT! " + black.name + " oyunu kazandı. " + red.name + "'in puanı: " + red.puan + ", " + black.name + "'nin puanı: " + black.puan);
                        return true;
                    }
                    if(isStillCheck()) {
                        board.items[index].setPosition(from);
                        black.item.add(index - 16, board.items[index]);
                        black.item.remove(index - 15);
                        if(isEaten) {
                            board.items[eatenIndex].setPosition(to);
                            red.item.add(eatenIndex, board.items[eatenIndex]);
                            red.item.remove(eatenIndex + 1);
                            if(eatenIndex == 0 || eatenIndex == 8) {
                                black.puan -= 9;
                            }
                            if(eatenIndex == 1 || eatenIndex == 7) {
                                black.puan -= 4;
                            }
                            if(eatenIndex == 2 || eatenIndex == 6) {
                                black.puan -= 2;
                            }
                            if(eatenIndex == 3 || eatenIndex == 5) {
                                black.puan -= 2;
                            }
                            if(eatenIndex == 9 || eatenIndex == 10) {
                                black.puan -= 4.5;
                            }
                            if(eatenIndex == 11 || eatenIndex == 12 || eatenIndex == 13 || eatenIndex == 14 || eatenIndex == 15) {
                                black.puan -= 1;
                            }
                        }
                        return false;
                    }
                    return true;
                }
            }
            else {
                return false;
            }
        }
        if (index == 2 || index == 6 || index == 18 || index == 22) {
            if(areGeneralsLegal(index, from, to, indexx, indexy, position1, position2, charArray) && Fil(index, from, to, indexx, indexy, position1, position2, charArray)) {
                if (count % 2 == 0) {
                    board.items[index].setPosition(to);
                    red.item.add(index, board.items[index]);
                    red.item.remove(index + 1);
                    for(int i = 0; i < board.items.length / 2; i++) {
                        if(to.equals(black.item.get(i).getPosition())) {
                            isEaten = true;
                            eatenIndex = i;
                            board.items[i + 16].setPosition("xx");
                            black.item.add(i, board.items[i + 16]);
                            black.item.remove(i + 1);
                            if(i == 0 || i == 8) {
                                red.puan += 9;
                            }
                            if(i == 1 || i == 7) {
                                red.puan += 4;
                            }
                            if(i == 2 || i == 6) {
                                red.puan += 2;
                            }
                            if(i == 3 || i == 5) {
                                red.puan += 2;
                            }
                            if(i == 9 || i == 10) {
                                red.puan += 4.5;
                            }
                            if(i == 11 || i == 12 || i == 13 || i == 14 || i == 15) {
                                red.puan += 1;
                            }
                        }
                    }
                    if(isStillAndStillCheck() == 0) {
                        isGameOver = true;
                        System.out.println("ŞAH MAT! " + red.name + " oyunu kazandı. " + red.name + "'in puanı: " + red.puan + ", " + black.name + "'nin puanı: " + black.puan);
                        return true;
                    }
                    if(isStillCheck()) {
                        board.items[index].setPosition(from);
                        red.item.add(index, board.items[index]);
                        red.item.remove(index + 1);
                        if(isEaten) {
                            board.items[eatenIndex + 16].setPosition(to);
                            black.item.add(eatenIndex, board.items[eatenIndex + 16]);
                            black.item.remove(eatenIndex + 1);
                            if(eatenIndex == 0 || eatenIndex == 8) {
                                red.puan -= 9;
                            }
                            if(eatenIndex == 1 || eatenIndex == 7) {
                                red.puan -= 4;
                            }
                            if(eatenIndex == 2 || eatenIndex == 6) {
                                red.puan -= 2;
                            }
                            if(eatenIndex == 3 || eatenIndex == 5) {
                                red.puan -= 2;
                            }
                            if(eatenIndex == 9 || eatenIndex == 10) {
                                red.puan -= 4.5;
                            }
                            if(eatenIndex == 11 || eatenIndex == 12 || eatenIndex == 13 || eatenIndex == 14 || eatenIndex == 15) {
                                red.puan -= 1;
                            }
                        }

                        return false;
                    }
                    return true;
                }
                else {
                    board.items[index].setPosition(to);
                    black.item.add(index - 16, board.items[index]);
                    black.item.remove(index - 15);
                    for(int i = 0; i < board.items.length / 2; i++) {
                        if(to.equals(red.item.get(i).getPosition())) {
                            isEaten = true;
                            eatenIndex = i;
                            board.items[i].setPosition("xx");
                            red.item.add(i, board.items[i]);
                            red.item.remove(i + 1);
                            if(i == 0 || i == 8) {
                                black.puan += 9;
                            }
                            if(i == 1 || i == 7) {
                                black.puan += 4;
                            }
                            if(i == 2 || i == 6) {
                                black.puan += 2;
                            }
                            if(i == 3 || i == 5) {
                                black.puan += 2;
                            }
                            if(i == 9 || i == 10) {
                                black.puan += 4.5;
                            }
                            if(i == 11 || i == 12 || i == 13 || i == 14 || i == 15) {
                                black.puan += 1;
                            }
                        }
                    }
                    if(isStillAndStillCheck() == 0) {
                        isGameOver = true;
                        System.out.println("ŞAH MAT! " + black.name + " oyunu kazandı. " + red.name + "'in puanı: " + red.puan + ", " + black.name + "'nin puanı: " + black.puan);
                        return true;
                    }
                    if(isStillCheck()) {
                        board.items[index].setPosition(from);
                        black.item.add(index - 16, board.items[index]);
                        black.item.remove(index - 15);
                        if(isEaten) {
                            board.items[eatenIndex].setPosition(to);
                            red.item.add(eatenIndex, board.items[eatenIndex]);
                            red.item.remove(eatenIndex + 1);
                            if(eatenIndex == 0 || eatenIndex == 8) {
                                black.puan -= 9;
                            }
                            if(eatenIndex == 1 || eatenIndex == 7) {
                                black.puan -= 4;
                            }
                            if(eatenIndex == 2 || eatenIndex == 6) {
                                black.puan -= 2;
                            }
                            if(eatenIndex == 3 || eatenIndex == 5) {
                                black.puan -= 2;
                            }
                            if(eatenIndex == 9 || eatenIndex == 10) {
                                black.puan -= 4.5;
                            }
                            if(eatenIndex == 11 || eatenIndex == 12 || eatenIndex == 13 || eatenIndex == 14 || eatenIndex == 15) {
                                black.puan -= 1;
                            }
                        }

                        return false;
                    }
                    return true;
                }
            }
            else {
                return false;
            }
        }
        if (index == 3 || index == 5 || index == 19 || index == 21) {
            if(areGeneralsLegal(index, from, to, indexx, indexy, position1, position2, charArray) && Vezir(index, from, to, indexx, indexy, position1, position2, charArray)) {
                if (count % 2 == 0) {
                    board.items[index].setPosition(to);
                    red.item.add(index, board.items[index]);
                    red.item.remove(index + 1);
                    for(int i = 0; i < board.items.length / 2; i++) {
                        if(to.equals(black.item.get(i).getPosition())) {
                            isEaten = true;
                            eatenIndex = i;
                            board.items[i + 16].setPosition("xx");
                            black.item.add(i, board.items[i + 16]);
                            black.item.remove(i + 1);
                            if(i == 0 || i == 8) {
                                red.puan += 9;
                            }
                            if(i == 1 || i == 7) {
                                red.puan += 4;
                            }
                            if(i == 2 || i == 6) {
                                red.puan += 2;
                            }
                            if(i == 3 || i == 5) {
                                red.puan += 2;
                            }
                            if(i == 9 || i == 10) {
                                red.puan += 4.5;
                            }
                            if(i == 11 || i == 12 || i == 13 || i == 14 || i == 15) {
                                red.puan += 1;
                            }
                        }
                    }
                    if(isStillAndStillCheck() == 0) {
                        isGameOver = true;
                        System.out.println("ŞAH MAT! " + red.name + " oyunu kazandı. " + red.name + "'in puanı: " + red.puan + ", " + black.name + "'nin puanı: " + black.puan);
                        return true;
                    }
                    if(isStillCheck()) {
                        board.items[index].setPosition(from);
                        red.item.add(index, board.items[index]);
                        red.item.remove(index + 1);
                        if(isEaten) {
                            board.items[eatenIndex + 16].setPosition(to);
                            black.item.add(eatenIndex, board.items[eatenIndex + 16]);
                            black.item.remove(eatenIndex + 1);
                            if(eatenIndex == 0 || eatenIndex == 8) {
                                red.puan -= 9;
                            }
                            if(eatenIndex == 1 || eatenIndex == 7) {
                                red.puan -= 4;
                            }
                            if(eatenIndex == 2 || eatenIndex == 6) {
                                red.puan -= 2;
                            }
                            if(eatenIndex == 3 || eatenIndex == 5) {
                                red.puan -= 2;
                            }
                            if(eatenIndex == 9 || eatenIndex == 10) {
                                red.puan -= 4.5;
                            }
                            if(eatenIndex == 11 || eatenIndex == 12 || eatenIndex == 13 || eatenIndex == 14 || eatenIndex == 15) {
                                red.puan -= 1;
                            }
                        }

                        return false;
                    }
                    return true;
                }
                else {
                    board.items[index].setPosition(to);
                    black.item.add(index - 16, board.items[index]);
                    black.item.remove(index - 15);
                    for(int i = 0; i < board.items.length / 2; i++) {
                        if(to.equals(red.item.get(i).getPosition())) {
                            isEaten = true;
                            eatenIndex = i;
                            board.items[i].setPosition("xx");
                            red.item.add(i, board.items[i]);
                            red.item.remove(i + 1);
                            if(i == 0 || i == 8) {
                                black.puan += 9;
                            }
                            if(i == 1 || i == 7) {
                                black.puan += 4;
                            }
                            if(i == 2 || i == 6) {
                                black.puan += 2;
                            }
                            if(i == 3 || i == 5) {
                                black.puan += 2;
                            }
                            if(i == 9 || i == 10) {
                                black.puan += 4.5;
                            }
                            if(i == 11 || i == 12 || i == 13 || i == 14 || i == 15) {
                                black.puan += 1;
                            }
                        }
                    }
                    if(isStillAndStillCheck() == 0) {
                        isGameOver = true;
                        System.out.println("ŞAH MAT! " + black.name + " oyunu kazandı. " + red.name + "'in puanı: " + red.puan + ", " + black.name + "'nin puanı: " + black.puan);
                        return true;
                    }
                    if(isStillCheck()) {
                        board.items[index].setPosition(from);
                        black.item.add(index - 16, board.items[index]);
                        black.item.remove(index - 15);
                        if(isEaten) {
                            board.items[eatenIndex].setPosition(to);
                            red.item.add(eatenIndex, board.items[eatenIndex]);
                            red.item.remove(eatenIndex + 1);
                            if(eatenIndex == 0 || eatenIndex == 8) {
                                black.puan -= 9;
                            }
                            if(eatenIndex == 1 || eatenIndex == 7) {
                                black.puan -= 4;
                            }
                            if(eatenIndex == 2 || eatenIndex == 6) {
                                black.puan -= 2;
                            }
                            if(eatenIndex == 3 || eatenIndex == 5) {
                                black.puan -= 2;
                            }
                            if(eatenIndex == 9 || eatenIndex == 10) {
                                black.puan -= 4.5;
                            }
                            if(eatenIndex == 11 || eatenIndex == 12 || eatenIndex == 13 || eatenIndex == 14 || eatenIndex == 15) {
                                black.puan -= 1;
                            }
                        }

                        return false;
                    }
                    return true;
                }
            }
            else {
                return false;
            }
        }
        if (index == 4 || index == 20) {
            if(areGeneralsLegal(index, from, to, indexx, indexy, position1, position2, charArray) && Şah(index, from, to, indexx, indexy, position1, position2, charArray)) {
                if (count % 2 == 0) {
                    board.items[index].setPosition(to);
                    red.item.add(index, board.items[index]);
                    red.item.remove(index + 1);
                    for(int i = 0; i < board.items.length / 2; i++) {
                        if(to.equals(black.item.get(i).getPosition())) {
                            isEaten = true;
                            eatenIndex = i;
                            board.items[i + 16].setPosition("xx");
                            black.item.add(i, board.items[i + 16]);
                            black.item.remove(i + 1);
                            if(i == 0 || i == 8) {
                                red.puan += 9;
                            }
                            if(i == 1 || i == 7) {
                                red.puan += 4;
                            }
                            if(i == 2 || i == 6) {
                                red.puan += 2;
                            }
                            if(i == 3 || i == 5) {
                                red.puan += 2;
                            }
                            if(i == 9 || i == 10) {
                                red.puan += 4.5;
                            }
                            if(i == 11 || i == 12 || i == 13 || i == 14 || i == 15) {
                                red.puan += 1;
                            }
                        }
                    }
                    if(isStillAndStillCheck() == 0) {
                        isGameOver = true;
                        System.out.println("ŞAH MAT! " + red.name + " oyunu kazandı. " + red.name + "'in puanı: " + red.puan + ", " + black.name + "'nin puanı: " + black.puan);
                        return true;
                    }
                    if(isStillCheck()) {
                        board.items[index].setPosition(from);
                        red.item.add(index, board.items[index]);
                        red.item.remove(index + 1);
                        if(isEaten) {
                            board.items[eatenIndex + 16].setPosition(to);
                            black.item.add(eatenIndex, board.items[eatenIndex + 16]);
                            black.item.remove(eatenIndex + 1);
                            if(eatenIndex == 0 || eatenIndex == 8) {
                                red.puan -= 9;
                            }
                            if(eatenIndex == 1 || eatenIndex == 7) {
                                red.puan -= 4;
                            }
                            if(eatenIndex == 2 || eatenIndex == 6) {
                                red.puan -= 2;
                            }
                            if(eatenIndex == 3 || eatenIndex == 5) {
                                red.puan -= 2;
                            }
                            if(eatenIndex == 9 || eatenIndex == 10) {
                                red.puan -= 4.5;
                            }
                            if(eatenIndex == 11 || eatenIndex == 12 || eatenIndex == 13 || eatenIndex == 14 || eatenIndex == 15) {
                                red.puan -= 1;
                            }
                        }

                        return false;
                    }
                    return true;
                }
                else {
                    board.items[index].setPosition(to);
                    black.item.add(index - 16, board.items[index]);
                    black.item.remove(index - 15);
                    for(int i = 0; i < board.items.length / 2; i++) {
                        if(to.equals(red.item.get(i).getPosition())) {
                            isEaten = true;
                            eatenIndex = i;
                            board.items[i].setPosition("xx");
                            red.item.add(i, board.items[i]);
                            red.item.remove(i + 1);
                            if(i == 0 || i == 8) {
                                black.puan += 9;
                            }
                            if(i == 1 || i == 7) {
                                black.puan += 4;
                            }
                            if(i == 2 || i == 6) {
                                black.puan += 2;
                            }
                            if(i == 3 || i == 5) {
                                black.puan += 2;
                            }
                            if(i == 9 || i == 10) {
                                black.puan += 4.5;
                            }
                            if(i == 11 || i == 12 || i == 13 || i == 14 || i == 15) {
                                black.puan += 1;
                            }
                        }
                    }
                    if(isStillAndStillCheck() == 0) {
                        isGameOver = true;
                        System.out.println("ŞAH MAT! " + black.name + " oyunu kazandı. " + red.name + "'in puanı: " + red.puan + ", " + black.name + "'nin puanı: " + black.puan);
                        return true;
                    }
                    if(isStillCheck()) {
                        board.items[index].setPosition(from);
                        black.item.add(index - 16, board.items[index]);
                        black.item.remove(index - 15);
                        if(isEaten) {
                            board.items[eatenIndex].setPosition(to);
                            red.item.add(eatenIndex, board.items[eatenIndex]);
                            red.item.remove(eatenIndex + 1);
                            if(eatenIndex == 0 || eatenIndex == 8) {
                                black.puan -= 9;
                            }
                            if(eatenIndex == 1 || eatenIndex == 7) {
                                black.puan -= 4;
                            }
                            if(eatenIndex == 2 || eatenIndex == 6) {
                                black.puan -= 2;
                            }
                            if(eatenIndex == 3 || eatenIndex == 5) {
                                black.puan -= 2;
                            }
                            if(eatenIndex == 9 || eatenIndex == 10) {
                                black.puan -= 4.5;
                            }
                            if(eatenIndex == 11 || eatenIndex == 12 || eatenIndex == 13 || eatenIndex == 14 || eatenIndex == 15) {
                                black.puan -= 1;
                            }
                        }

                        return false;
                    }
                    return true;
                }
            }
            else {
                return false;
            }
        }
        if (index == 9 || index == 10 || index == 25 || index == 26) {
            if(areGeneralsLegal(index, from, to, indexx, indexy, position1, position2, charArray) && Top(index, from, to, indexx, indexy, position1, position2, charArray)) {
                if (count % 2 == 0) {
                    board.items[index].setPosition(to);
                    red.item.add(index, board.items[index]);
                    red.item.remove(index + 1);
                    for(int i = 0; i < board.items.length / 2; i++) {
                        if(to.equals(black.item.get(i).getPosition())) {
                            isEaten = true;
                            eatenIndex = i;
                            board.items[i + 16].setPosition("xx");
                            black.item.add(i, board.items[i + 16]);
                            black.item.remove(i + 1);
                            if(i == 0 || i == 8) {
                                red.puan += 9;
                            }
                            if(i == 1 || i == 7) {
                                red.puan += 4;
                            }
                            if(i == 2 || i == 6) {
                                red.puan += 2;
                            }
                            if(i == 3 || i == 5) {
                                red.puan += 2;
                            }
                            if(i == 9 || i == 10) {
                                red.puan += 4.5;
                            }
                            if(i == 11 || i == 12 || i == 13 || i == 14 || i == 15) {
                                red.puan += 1;
                            }
                        }
                    }
                    if(isStillAndStillCheck() == 0) {
                        isGameOver = true;
                        System.out.println("ŞAH MAT! " + red.name + " oyunu kazandı. " + red.name + "'in puanı: " + red.puan + ", " + black.name + "'nin puanı: " + black.puan);
                        return true;
                    }
                    if(isStillCheck()) {
                        board.items[index].setPosition(from);
                        red.item.add(index, board.items[index]);
                        red.item.remove(index + 1);
                        if(isEaten) {
                            board.items[eatenIndex + 16].setPosition(to);
                            black.item.add(eatenIndex, board.items[eatenIndex + 16]);
                            black.item.remove(eatenIndex + 1);
                            if(eatenIndex == 0 || eatenIndex == 8) {
                                red.puan -= 9;
                            }
                            if(eatenIndex == 1 || eatenIndex == 7) {
                                red.puan -= 4;
                            }
                            if(eatenIndex == 2 || eatenIndex == 6) {
                                red.puan -= 2;
                            }
                            if(eatenIndex == 3 || eatenIndex == 5) {
                                red.puan -= 2;
                            }
                            if(eatenIndex == 9 || eatenIndex == 10) {
                                red.puan -= 4.5;
                            }
                            if(eatenIndex == 11 || eatenIndex == 12 || eatenIndex == 13 || eatenIndex == 14 || eatenIndex == 15) {
                                red.puan -= 1;
                            }
                        }

                        return false;
                    }
                    return true;
                }
                else {
                    board.items[index].setPosition(to);
                    black.item.add(index - 16, board.items[index]);
                    black.item.remove(index - 15);
                    for(int i = 0; i < board.items.length / 2; i++) {
                        if(to.equals(red.item.get(i).getPosition())) {
                            isEaten = true;
                            eatenIndex = i;
                            board.items[i].setPosition("xx");
                            red.item.add(i, board.items[i]);
                            red.item.remove(i + 1);
                            if(i == 0 || i == 8) {
                                black.puan += 9;
                            }
                            if(i == 1 || i == 7) {
                                black.puan += 4;
                            }
                            if(i == 2 || i == 6) {
                                black.puan += 2;
                            }
                            if(i == 3 || i == 5) {
                                black.puan += 2;
                            }
                            if(i == 9 || i == 10) {
                                black.puan += 4.5;
                            }
                            if(i == 11 || i == 12 || i == 13 || i == 14 || i == 15) {
                                black.puan += 1;
                            }
                        }
                    }
                    if(isStillAndStillCheck() == 0) {
                        isGameOver = true;
                        System.out.println("ŞAH MAT! " + black.name + " oyunu kazandı. " + red.name + "'in puanı: " + red.puan + ", " + black.name + "'nin puanı: " + black.puan);
                        return true;
                    }
                    if(isStillCheck()) {
                        board.items[index].setPosition(from);
                        black.item.add(index - 16, board.items[index]);
                        black.item.remove(index - 15);
                        if(isEaten) {
                            board.items[eatenIndex].setPosition(to);
                            red.item.add(eatenIndex, board.items[eatenIndex]);
                            red.item.remove(eatenIndex + 1);
                            if(eatenIndex == 0 || eatenIndex == 8) {
                                black.puan -= 9;
                            }
                            if(eatenIndex == 1 || eatenIndex == 7) {
                                black.puan -= 4;
                            }
                            if(eatenIndex == 2 || eatenIndex == 6) {
                                black.puan -= 2;
                            }
                            if(eatenIndex == 3 || eatenIndex == 5) {
                                black.puan -= 2;
                            }
                            if(eatenIndex == 9 || eatenIndex == 10) {
                                black.puan -= 4.5;
                            }
                            if(eatenIndex == 11 || eatenIndex == 12 || eatenIndex == 13 || eatenIndex == 14 || eatenIndex == 15) {
                                black.puan -= 1;
                            }
                        }

                        return false;
                    }
                    return true;
                }
            }
            else {
                return false;
            }
        }
        if (index == 11 || index == 12 || index == 13 || index == 14 || index == 15 ||
                index == 27 || index == 28 || index == 29 || index == 30 || index ==31) {
            if(areGeneralsLegal(index, from, to, indexx, indexy, position1, position2, charArray) && Piyon(index, from, to, indexx, indexy, position1, position2, charArray)) {
                if (count % 2 == 0) {
                    board.items[index].setPosition(to);
                    red.item.add(index, board.items[index]);
                    red.item.remove(index + 1);
                    for(int i = 0; i < board.items.length / 2; i++) {
                        if(to.equals(black.item.get(i).getPosition())) {
                            isEaten = true;
                            eatenIndex = i;
                            board.items[i + 16].setPosition("xx");
                            black.item.add(i, board.items[i + 16]);
                            black.item.remove(i + 1);
                            if(i == 0 || i == 8) {
                                red.puan += 9;
                            }
                            if(i == 1 || i == 7) {
                                red.puan += 4;
                            }
                            if(i == 2 || i == 6) {
                                red.puan += 2;
                            }
                            if(i == 3 || i == 5) {
                                red.puan += 2;
                            }
                            if(i == 9 || i == 10) {
                                red.puan += 4.5;
                            }
                            if(i == 11 || i == 12 || i == 13 || i == 14 || i == 15) {
                                red.puan += 1;
                            }
                        }
                    }
                    if(isStillAndStillCheck() == 0) {
                        isGameOver = true;
                        System.out.println("ŞAH MAT! " + red.name + " oyunu kazandı. " + red.name + "'in puanı: " + red.puan + ", " + black.name + "'nin puanı: " + black.puan);
                        return true;
                    }
                    if(isStillCheck()) {
                        board.items[index].setPosition(from);
                        red.item.add(index, board.items[index]);
                        red.item.remove(index + 1);
                        if(isEaten) {
                            board.items[eatenIndex + 16].setPosition(to);
                            black.item.add(eatenIndex, board.items[eatenIndex + 16]);
                            black.item.remove(eatenIndex + 1);
                            if(eatenIndex == 0 || eatenIndex == 8) {
                                red.puan -= 9;
                            }
                            if(eatenIndex == 1 || eatenIndex == 7) {
                                red.puan -= 4;
                            }
                            if(eatenIndex == 2 || eatenIndex == 6) {
                                red.puan -= 2;
                            }
                            if(eatenIndex == 3 || eatenIndex == 5) {
                                red.puan -= 2;
                            }
                            if(eatenIndex == 9 || eatenIndex == 10) {
                                red.puan -= 4.5;
                            }
                            if(eatenIndex == 11 || eatenIndex == 12 || eatenIndex == 13 || eatenIndex == 14 || eatenIndex == 15) {
                                red.puan -= 1;
                            }
                        }

                        return false;
                    }
                    return true;
                }
                else {
                    board.items[index].setPosition(to);
                    black.item.add(index - 16, board.items[index]);
                    black.item.remove(index - 15);
                    for(int i = 0; i < board.items.length / 2; i++) {
                        if(to.equals(red.item.get(i).getPosition())) {
                            isEaten = true;
                            eatenIndex = i;
                            board.items[i].setPosition("xx");
                            red.item.add(i, board.items[i]);
                            red.item.remove(i + 1);
                            if(i == 0 || i == 8) {
                                black.puan += 9;
                            }
                            if(i == 1 || i == 7) {
                                black.puan += 4;
                            }
                            if(i == 2 || i == 6) {
                                black.puan += 2;
                            }
                            if(i == 3 || i == 5) {
                                black.puan += 2;
                            }
                            if(i == 9 || i == 10) {
                                black.puan += 4.5;
                            }
                            if(i == 11 || i == 12 || i == 13 || i == 14 || i == 15) {
                                black.puan += 1;
                            }
                        }
                    }
                    if(isStillAndStillCheck() == 0) {
                        isGameOver = true;
                        System.out.println("ŞAH MAT! " + black.name + " oyunu kazandı. " + red.name + "'in puanı: " + red.puan + ", " + black.name + "'nin puanı: " + black.puan);
                        return true;
                    }
                    if(isStillCheck()) {
                        board.items[index].setPosition(from);
                        black.item.add(index - 16, board.items[index]);
                        black.item.remove(index - 15);
                        if(isEaten) {
                            board.items[eatenIndex].setPosition(to);
                            red.item.add(eatenIndex, board.items[eatenIndex]);
                            red.item.remove(eatenIndex + 1);
                            if(eatenIndex == 0 || eatenIndex == 8) {
                                black.puan -= 9;
                            }
                            if(eatenIndex == 1 || eatenIndex == 7) {
                                black.puan -= 4;
                            }
                            if(eatenIndex == 2 || eatenIndex == 6) {
                                black.puan -= 2;
                            }
                            if(eatenIndex == 3 || eatenIndex == 5) {
                                black.puan -= 2;
                            }
                            if(eatenIndex == 9 || eatenIndex == 10) {
                                black.puan -= 4.5;
                            }
                            if(eatenIndex == 11 || eatenIndex == 12 || eatenIndex == 13 || eatenIndex == 14 || eatenIndex == 15) {
                                black.puan -= 1;
                            }
                        }

                        return false;
                    }
                    return true;
                }
            }
            else {
                return false;
            }
        }
        return false;

    }
    public boolean Kale(int index, String from, String to, int indexx, int indexy, int position1, int position2, String[] charArray) {
        if(from.equals("x0")) {
            return false;
        }
        int piece = 0;
        if (count % 2 == 0) {
            for(int i = 0; i < board.items.length / 2; i++) {
                if(to.equals(red.item.get(i).getPosition())) {
                    return false;
                }
            }
            for(int i = 0; i < board.items.length / 2; i++) {
                if(from.equals(black.item.get(i).getPosition())) {
                    return false;
                }
            }
            if(indexx == indexy) {
                for(int i = position1 + 1; i <= position2; i++) {
                    for(int j = 0; j < board.items.length; j++) {
                        if(board.items[j].getPosition().equals(charArray[indexx] + i)) {
                            piece++;
                        }
                    }
                }
                for(int i = position1 - 1; i >= position2; i--) {
                    for(int j = 0; j < board.items.length; j++) {
                        if(board.items[j].getPosition().equals(charArray[indexx] + i)) {
                            piece++;
                        }
                    }
                }
                if(piece == 0) {
                    return true;
                }
                if(piece == 1) {
                    for(int i = 0; i < black.item.size(); i++) {
                        if(black.item.get(i).getPosition().equals(charArray[indexy] + position2)) {
                            return true;
                        }
                    }
                }
                return false;
            }
            if (position1 == position2) {
                for(int i = indexx + 1; i <= indexy; i++) {
                    for(int j = 0; j < board.items.length; j++) {
                        if(board.items[j].getPosition().equals(charArray[i] + position1)) {
                            piece++;
                        }
                    }
                }
                for(int i = indexx - 1; i >= indexy; i--) {
                    for(int j = 0; j < board.items.length; j++) {
                        if(board.items[j].getPosition().equals(charArray[i] + position1)) {
                            piece++;
                        }
                    }
                }
                if(piece == 0) {
                    return true;
                }
                if(piece == 1) {
                    for(int i = 0; i < black.item.size(); i++) {
                        if(black.item.get(i).getPosition().equals(charArray[indexy] + position2)) {
                            return true;
                        }
                    }
                }
                return false;
            }

        } else {
            for(int i = 0; i < board.items.length / 2; i++) {
                if(to.equals(black.item.get(i).getPosition())) {
                    return false;
                }
            }
            for(int i = 0; i < board.items.length / 2; i++) {
                if(from.equals(red.item.get(i).getPosition())) {
                    return false;
                }
            }
            if(indexx == indexy) {
                for(int i = position1 + 1; i <= position2; i++) {
                    for(int j = 0; j < board.items.length; j++) {
                        if(board.items[j].getPosition().equals(charArray[indexx] + i)) {
                            piece++;
                        }
                    }
                }
                for(int i = position1 - 1; i >= position2; i--) {
                    for(int j = 0; j < board.items.length; j++) {
                        if(board.items[j].getPosition().equals(charArray[indexx] + i)) {
                            piece++;
                        }
                    }
                }
                if(piece == 0) {
                    return true;
                }
                if(piece == 1) {
                    for(int i = 0; i < red.item.size(); i++) {
                        if(red.item.get(i).getPosition().equals(charArray[indexy] + position2)) {
                            return true;
                        }
                    }
                }
                return false;
            }
            if(position1 == position2) {
                for(int i = indexx + 1; i <= indexy; i++) {
                    for(int j = 0; j < board.items.length; j++) {
                        if(board.items[j].getPosition().equals(charArray[i] + position1)) {
                            piece++;
                        }
                    }
                }
                for(int i = indexx - 1; i >= indexy; i--) {
                    for(int j = 0; j < board.items.length; j++) {
                        if(board.items[j].getPosition().equals(charArray[i] + position1)) {
                            piece++;
                        }
                    }
                }
                if(piece == 0) {
                    return true;
                }
                if(piece == 1) {
                    for(int i = 0; i < red.item.size(); i++) {
                        if(red.item.get(i).getPosition().equals(charArray[indexy] + position2)) {
                            return true;
                        }
                    }
                }
                return false;
            }
        }
        return false;
    }
    public boolean At(int index, String from, String to, int indexx, int indexy, int position1, int position2, String[] charArray) {

        if(from.equals("x0")) {
            return false;
        }
        if (count % 2 == 0) {
            for(int i = 0; i < board.items.length / 2; i++) {
                if(to.equals(red.item.get(i).getPosition())) {
                    return false;
                }
            }
            for(int i = 0; i < board.items.length / 2; i++) {
                if(from.equals(black.item.get(i).getPosition())) {
                    return false;
                }
            }
            if((position1 - position2 == -1 || position1 - position2 == 1) && (indexx - indexy == -2)) {
                for(int i = 0; i < board.items.length; i++) {
                    if(board.items[i].getPosition().equals(charArray[indexx + 1] + position1)) {
                        return false;
                    }
                }
                return true;

            }
            if((position1 - position2 == -1 || position1 - position2 == 1) && (indexx - indexy == 2)) {
                for(int i = 0; i < board.items.length; i++) {
                    if(board.items[i].getPosition().equals(charArray[indexx - 1] + position1)) {
                        return false;
                    }
                }
                return true;

            }
            if((position1 - position2 == 2) && (indexx - indexy == 1 || indexx - indexy == -1)) {
                for(int i = 0; i < board.items.length; i++) {
                    if(board.items[i].getPosition().equals(charArray[indexx] + (position1 - 1))) {
                        return false;
                    }
                }
                return true;

            }
            if((position1 - position2 == -2) && (indexx - indexy == 1 || indexx - indexy == -1)) {
                for(int i = 0; i < board.items.length; i++) {
                    if(board.items[i].getPosition().equals(charArray[indexx] + (position1 + 1))) {
                        return false;
                    }
                }
                return true;

            }
        }
        else {
            for(int i = 0; i < board.items.length / 2; i++) {
                if(to.equals(black.item.get(i).getPosition())) {
                    return false;
                }
            }
            for(int i = 0; i < board.items.length / 2; i++) {
                if(from.equals(red.item.get(i).getPosition())) {
                    return false;
                }
            }
            if((position1 - position2 == -1 || position1 - position2 == 1) && (indexx - indexy == -2)) {
                for(int i = 0; i < board.items.length; i++) {
                    if(board.items[i].getPosition().equals(charArray[indexx + 1] + position1)) {
                        return false;
                    }
                }
                return true;

            }
            if((position1 - position2 == -1 || position1 - position2 == 1) && (indexx - indexy == 2)) {
                for(int i = 0; i < board.items.length; i++) {
                    if(board.items[i].getPosition().equals(charArray[indexx - 1] + position1)) {
                        return false;
                    }
                }
                return true;

            }
            if((position1 - position2 == 2) && (indexx - indexy == 1 || indexx - indexy == -1)) {
                for(int i = 0; i < board.items.length; i++) {
                    if(board.items[i].getPosition().equals(charArray[indexx] + (position1 - 1))) {
                        return false;
                    }
                }
                return true;

            }
            if((position1 - position2 == -2) && (indexx - indexy == 1 || indexx - indexy == -1)) {
                for(int i = 0; i < board.items.length; i++) {
                    if(board.items[i].getPosition().equals(charArray[indexx] + (position1 + 1))) {
                        return false;
                    }
                }
                return true;

            }
        }
        return false;

    }
    public boolean Fil(int index, String from, String to, int indexx, int indexy, int position1, int position2, String[] charArray) {
        if(from.equals("x0")) {
            return false;
        }
        if (count % 2 == 0) {
            for(int i = 0; i < board.items.length / 2; i++) {
                if(to.equals(red.item.get(i).getPosition())) {
                    return false;
                }
            }
            for(int i = 0; i < board.items.length / 2; i++) {
                if(from.equals(black.item.get(i).getPosition())) {
                    return false;
                }
            }
            if(indexy > 4) {
                return false;
            }
            if (position1 - position2 == 2 && indexx - indexy == -2) {
                for(int i = 0; i < board.items.length; i++) {
                    if(board.items[i].getPosition().equals(charArray[indexx + 1] + (position1 - 1))) {
                        return false;
                    }
                }
                return true;
            }
            if (position1 - position2 == -2 && indexx - indexy == -2) {
                for(int i = 0; i < board.items.length; i++) {
                    if(board.items[i].getPosition().equals(charArray[indexx + 1] + (position1 + 1))) {
                        return false;
                    }
                }
                return true;
            }
            if (position1 - position2 == 2 && indexx - indexy == 2) {
                for(int i = 0; i < board.items.length; i++) {
                    if(board.items[i].getPosition().equals(charArray[indexx - 1] + (position1 - 1))) {
                        return false;
                    }
                }
                return true;
            }
            if (position1 - position2 == -2 && indexx - indexy == 2) {
                for(int i = 0; i < board.items.length; i++) {
                    if(board.items[i].getPosition().equals(charArray[indexx - 1] + (position1 + 1))) {
                        return false;
                    }
                }
                return true;
            }
        }
        else {
            for(int i = 0; i < board.items.length / 2; i++) {
                if(to.equals(black.item.get(i).getPosition())) {
                    return false;
                }
            }
            for(int i = 0; i < board.items.length / 2; i++) {
                if(from.equals(red.item.get(i).getPosition())) {
                    return false;
                }
            }
            if(indexy < 5) {
                return false;
            }
            if (position1 - position2 == 2 && indexx - indexy == -2) {
                for(int i = 0; i < board.items.length; i++) {
                    if(board.items[i].getPosition().equals(charArray[indexx + 1] + (position1 - 1))) {
                        return false;
                    }
                }
                return true;
            }
            if (position1 - position2 == -2 && indexx - indexy == -2) {
                for(int i = 0; i < board.items.length; i++) {
                    if(board.items[i].getPosition().equals(charArray[indexx + 1] + (position1 + 1))) {
                        return false;
                    }
                }
                return true;
            }
            if (position1 - position2 == 2 && indexx - indexy == 2) {
                for(int i = 0; i < board.items.length; i++) {
                    if(board.items[i].getPosition().equals(charArray[indexx - 1] + (position1 - 1))) {
                        return false;
                    }
                }
                return true;
            }
            if (position1 - position2 == -2 && indexx - indexy == 2) {
                for(int i = 0; i < board.items.length; i++) {
                    if(board.items[i].getPosition().equals(charArray[indexx - 1] + (position1 + 1))) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }
    public boolean Vezir(int index, String from, String to, int indexx, int indexy, int position1, int position2, String[] charArray) {
        if(from.equals("x0")) {
            return false;
        }
        if (count % 2 == 0) {
            for(int i = 0; i < board.items.length / 2; i++) {
                if(to.equals(red.item.get(i).getPosition())) {
                    return false;
                }
            }
            for(int i = 0; i < board.items.length / 2; i++) {
                if(from.equals(black.item.get(i).getPosition())) {
                    return false;
                }
            }
            if (indexy > 2 || (position2 > 6 || position2 < 4)) {
                return false;
            }
            if (position1 - position2 == 1 && indexx - indexy == -1) {
                return true;
            }
            if (position1 - position2 == -1 && indexx - indexy == -1) {
                return true;
            }
            if (position1 - position2 == 1 && indexx - indexy == 1) {
                return true;
            }
            if (position1 - position2 == -1 && indexx - indexy == 1) {
                return true;
            }
        }
        else {
            for(int i = 0; i < board.items.length / 2; i++) {
                if(to.equals(black.item.get(i).getPosition())) {
                    return false;
                }
            }
            for(int i = 0; i < board.items.length / 2; i++) {
                if(from.equals(red.item.get(i).getPosition())) {
                    return false;
                }
            }
            if (indexy < 7 || (position2 > 6 || position2 < 4)) {
                return false;
            }
            if (position1 - position2 == 1 && indexx - indexy == -1) {
                return true;
            }
            if (position1 - position2 == -1 && indexx - indexy == -1) {
                return true;
            }
            if (position1 - position2 == 1 && indexx - indexy == 1) {
                return true;
            }
            if (position1 - position2 == -1 && indexx - indexy == 1) {
                return true;
            }
        }
        return false;
    }
    public boolean Şah(int index, String from, String to, int indexx, int indexy, int position1, int position2, String[] charArray) {
        if(from.equals("x0")) {
            return false;
        }
        if(count % 2 == 0) {
            for(int i = 0; i < board.items.length / 2; i++) {
                if(to.equals(red.item.get(i).getPosition())) {
                    return false;
                }
            }
            for(int i = 0; i < board.items.length / 2; i++) {
                if(from.equals(black.item.get(i).getPosition())) {
                    return false;
                }
            }
            if (indexy > 2 || (position2 > 6 || position2 < 4)) {
                return false;
            }
            if(indexx == indexy && position1 - position2 == 1) {
                return true;
            }
            if(indexx == indexy && position1 - position2 == -1) {
                return true;
            }
            if(indexx - indexy == -1 && position1 == position2) {
                return true;
            }
            if(indexx - indexy == 1 && position1 == position2) {
                return true;
            }
        }
        else {
            for(int i = 0; i < board.items.length / 2; i++) {
                if(to.equals(black.item.get(i).getPosition())) {
                    return false;
                }
            }
            for(int i = 0; i < board.items.length / 2; i++) {
                if(from.equals(red.item.get(i).getPosition())) {
                    return false;
                }
            }
            if (indexy < 7 || (position2 > 6 || position2 < 4)) {
                return false;
            }
            if(indexx == indexy && position1 - position2 == 1) {
                return true;
            }
            if(indexx == indexy && position1 - position2 == -1) {
                return true;
            }
            if(indexx - indexy == -1 && position1 == position2) {
                return true;
            }
            if(indexx - indexy == 1 && position1 == position2) {
                return true;
            }
        }
        return false;
    }
    public boolean Top(int index, String from, String to, int indexx, int indexy, int position1, int position2, String[] charArray) {
        if(from.equals("x0")) {
            return false;
        }
        int piece = 0;
        if (count % 2 == 0) {
            for(int i = 0; i < board.items.length / 2; i++) {
                if(to.equals(red.item.get(i).getPosition())) {
                    return false;
                }
            }
            for(int i = 0; i < board.items.length / 2; i++) {
                if(from.equals(black.item.get(i).getPosition())) {
                    return false;
                }
            }
            if (indexx == indexy) {
                for(int i = position1 + 1; i <= position2; i++) {
                    for(int j = 0; j < board.items.length; j++) {
                        if(board.items[j].getPosition().equals(charArray[indexx] + i)) {
                            piece++;
                        }
                    }
                }
                for(int i = position1 - 1; i >= position2; i--) {
                    for(int j = 0; j < board.items.length; j++) {
                        if(board.items[j].getPosition().equals(charArray[indexx] + i)) {
                            piece++;
                        }
                    }
                }
                if(piece > 2 || piece == 1) {
                    return false;
                }
                if(piece == 0) {
                    return true;
                }
                if(piece == 2) {
                    for(int i = 0; i < black.item.size(); i++) {
                        if(black.item.get(i).getPosition().equals(charArray[indexy] + position2)) {
                            return true;
                        }
                    }
                    return false;
                }
            }
            if (position1 == position2) {
                for(int i = indexx + 1; i <= indexy; i++) {
                    for(int j = 0; j < board.items.length; j++) {
                        if(board.items[j].getPosition().equals(charArray[i] + position1)) {
                            piece++;
                        }
                    }
                }
                for(int i = indexx - 1; i >= indexy; i--) {
                    for(int j = 0; j < board.items.length; j++) {
                        if(board.items[j].getPosition().equals(charArray[i] + position1)) {
                            piece++;
                        }
                    }
                }
                if(piece > 2 || piece == 1) {
                    return false;
                }
                if(piece == 0) {
                    return true;
                }
                if(piece == 2) {
                    for(int i = 0; i < black.item.size(); i++) {
                        if(black.item.get(i).getPosition().equals(charArray[indexy] + position2)) {
                            return true;
                        }
                    }
                    return false;
                }
            }
        }
        else {
            for(int i = 0; i < board.items.length / 2; i++) {
                if(to.equals(black.item.get(i).getPosition())) {
                    return false;
                }
            }
            for(int i = 0; i < board.items.length / 2; i++) {
                if(from.equals(red.item.get(i).getPosition())) {
                    return false;
                }
            }
            if(indexx == indexy) {
                for(int i = position1 + 1; i <= position2; i++) {
                    for(int j = 0; j < board.items.length; j++) {
                        if(board.items[j].getPosition().equals(charArray[indexx] + i)) {
                            piece++;
                        }
                    }
                }
                for(int i = position1 - 1; i >= position2; i--) {
                    for(int j = 0; j < board.items.length; j++) {
                        if(board.items[j].getPosition().equals(charArray[indexx] + i)) {
                            piece++;
                        }
                    }
                }
                if(piece > 2 || piece == 1) {
                    return false;
                }
                if(piece == 0) {
                    return true;
                }
                if(piece == 2) {
                    for(int i = 0; i < red.item.size(); i++) {
                        if(red.item.get(i).getPosition().equals(charArray[indexy] + position2)) {
                            return true;
                        }
                    }
                    return false;
                }
            }
            if(position1 == position2) {
                for(int i = indexx + 1; i <= indexy; i++) {
                    for(int j = 0; j < board.items.length; j++) {
                        if(board.items[j].getPosition().equals(charArray[i] + position1)) {
                            piece++;
                        }
                    }
                }
                for(int i = indexx - 1; i >= indexy; i--) {
                    for(int j = 0; j < board.items.length; j++) {
                        if(board.items[j].getPosition().equals(charArray[i] + position1)) {
                            piece++;
                        }
                    }
                }
                if(piece > 2 || piece == 1) {
                    return false;
                }
                if(piece == 0) {
                    return true;
                }
                if(piece == 2) {
                    for(int i = 0; i < red.item.size(); i++) {
                        if(red.item.get(i).getPosition().equals(charArray[indexy] + position2)) {
                            return true;
                        }
                    }
                    return false;
                }
            }
        }
        return false;
    }
    public boolean Piyon(int index, String from, String to, int indexx, int indexy, int position1, int position2, String[] charArray) {
        if(from.equals("x0")) {
            return false;
        }
        if (count % 2 == 0) {
            for(int i = 0; i < board.items.length / 2; i++) {
                if(to.equals(red.item.get(i).getPosition())) {
                    return false;
                }
            }
            for(int i = 0; i < board.items.length / 2; i++) {
                if(from.equals(black.item.get(i).getPosition())) {
                    return false;
                }
            }
            if (indexx < 5) {
                if (position1 == position2 && indexx - indexy == -1) {
                    return true;
                }
                return false;
            }
            if (indexx > 4) {
                if (indexx == indexy && position1 - position2 == 1) {
                    return true;
                }
                if (indexx - indexy == -1 && position1 == position2) {
                    return true;
                }
                if (indexx == indexy && position1 - position2 == -1) {
                    return true;
                }
                return false;
            }
        }
        else {
            for(int i = 0; i < board.items.length / 2; i++) {
                if(to.equals(black.item.get(i).getPosition())) {
                    return false;
                }
            }
            for(int i = 0; i < board.items.length / 2; i++) {
                if(from.equals(red.item.get(i).getPosition())) {
                    return false;
                }
            }
            if (indexx > 4) {
                if(position1 == position2 && indexx - indexy == 1) {
                    return true;
                }
                return false;
            }
            if (indexx < 5) {
                if(position1 - position2 == 1 && indexx == indexy) {
                    return true;
                }
                if(position1 == position2 && indexx - indexy == 1) {
                    return true;
                }
                if(position1 - position2 == -1 && indexx == indexy) {
                    return true;
                }
                return false;
            }
        }
        return false;
    }
    public boolean areGeneralsLegal(int index, String from, String to, int indexx, int indexy, int position1, int position2, String[] charArray) {
        String general1 = board.items[4].getPosition();
        String general2 = board.items[20].getPosition();
        String betweenPiece = "";
        int pieceNumber = 0;

        int gPosition1 = Integer.parseInt(general1.substring(1, 2));
        int gPosition2 = Integer.parseInt(general2.substring(1, 2));
        String gPositionx = general1.substring(0, 1);
        String gPositiony = general2.substring(0, 1);
        int gIndexx = -1, gIndexy = -1;
        for(int i = 0; i < charArray.length; i++) {
            if (gPositionx.equals(charArray[i])) {
                gIndexx = i;
            }
            if (gPositiony.equals(charArray[i])) {
                gIndexy = i;
            }
        }

        if(gPosition1 == gPosition2) {
            for(int i = gIndexx + 1; i < gIndexy; i++) {
                for(int j = 0; j < board.items.length; j++) {
                    if(board.items[j].getPosition().equals(charArray[i] + gPosition1)) {
                        pieceNumber++;
                        betweenPiece = charArray[i] + gPosition1;
                    }
                }
            }

            if (pieceNumber == 1) {
                if(betweenPiece.equals(from)) {
                    for(int i = gIndexx + 1; i < gIndexy; i++) {
                        if(to.equals(charArray[i] + gPosition1)) {

                            return true;
                        }
                    }
                    return false;
                }
                if(general1.equals(from) || general2.equals(from)) {
                    if(to.equals(betweenPiece)) {
                        return false;
                    }
                    return true;
                }
            }
        }
        return true;

    }
    public boolean isStillCheck() {
        String general1 = board.items[4].getPosition();
        String general2 = board.items[20].getPosition();

        int gPosition1 = Integer.parseInt(general1.substring(1, 2));
        int gPosition2 = Integer.parseInt(general2.substring(1, 2));
        String gPositionx = general1.substring(0, 1);
        String gPositiony = general2.substring(0, 1);
        int gIndexx = -1;
        int gIndexy = -1;
        for(int i = 0; i < charArray.length; i++) {
            if (gPositionx.equals(charArray[i])) {
                gIndexx = i;
            }
            if (gPositiony.equals(charArray[i])) {
                gIndexy = i;
            }
        }
        if (count % 2 == 1) {
            String k1 = board.items[0].getPosition();
            String k2 = board.items[8].getPosition();
            String a1 = board.items[1].getPosition();
            String a2 = board.items[7].getPosition();
            String t1 = board.items[9].getPosition();
            String t2 = board.items[10].getPosition();
            String p1 = board.items[11].getPosition();
            String p2 = board.items[12].getPosition();
            String p3 = board.items[13].getPosition();
            String p4 = board.items[14].getPosition();
            String p5 = board.items[15].getPosition();

            if(k1.equals("xx")) {
                k1 = "x0";
            }
            if(k2.equals("xx")) {
                k2 = "x0";
            }
            if(a1.equals("xx")) {
                a1 = "x0";
            }
            if(a2.equals("xx")) {
                a2 = "x0";
            }
            if(t1.equals("xx")) {
                t1 = "x0";
            }
            if(t2.equals("xx")) {
                t2 = "x0";
            }
            if(p1.equals("xx")) {
                p1 = "x0";
            }
            if(p2.equals("xx")) {
                p2 = "x0";
            }
            if(p3.equals("xx")) {
                p3 = "x0";
            }
            if(p4.equals("xx")) {
                p4 = "x0";
            }
            if(p5.equals("xx")) {
                p5 = "x0";
            }

            int kPosition1 = Integer.parseInt(k1.substring(1, 2));
            int kPosition2 = Integer.parseInt(k2.substring(1, 2));
            String kPositionx = k1.substring(0, 1);
            String kPositiony = k2.substring(0, 1);
            int kIndexx = -1, kIndexy = -1;

            int aPosition1 = Integer.parseInt(a1.substring(1, 2));
            int aPosition2 = Integer.parseInt(a2.substring(1, 2));
            String aPositionx = a1.substring(0, 1);
            String aPositiony = a2.substring(0, 1);
            int aIndexx = -1, aIndexy = -1;

            int tPosition1 = Integer.parseInt(t1.substring(1, 2));
            int tPosition2 = Integer.parseInt(t2.substring(1, 2));
            String tPositionx = t1.substring(0, 1);
            String tPositiony = t2.substring(0, 1);
            int tIndexx = -1, tIndexy = -1;

            int pPosition1 = Integer.parseInt(p1.substring(1, 2));
            int pPosition2 = Integer.parseInt(p2.substring(1, 2));
            int pPosition3 = Integer.parseInt(p3.substring(1, 2));
            int pPosition4 = Integer.parseInt(p4.substring(1, 2));
            int pPosition5 = Integer.parseInt(p5.substring(1, 2));
            String pPositionx = p1.substring(0, 1);
            String pPositiony = p2.substring(0, 1);
            String pPositionz = p3.substring(0, 1);
            String pPositionw = p4.substring(0, 1);
            String pPositiont = p5.substring(0, 1);
            int pIndexx = -1, pIndexy = -1, pIndexz = -1, pIndexw = -1, pIndext = -1;
            for(int i = 0; i < charArray.length; i++) {
                if (kPositionx.equals(charArray[i])) {
                    kIndexx = i;
                }
                if (kPositiony.equals(charArray[i])) {
                    kIndexy = i;
                }
                if (aPositionx.equals(charArray[i])) {
                    aIndexx = i;
                }
                if (aPositiony.equals(charArray[i])) {
                    aIndexy = i;
                }
                if (tPositionx.equals(charArray[i])) {
                    tIndexx = i;
                }
                if (tPositiony.equals(charArray[i])) {
                    tIndexy = i;
                }
                if (pPositionx.equals(charArray[i])) {
                    pIndexx = i;
                }
                if (pPositiony.equals(charArray[i])) {
                    pIndexy = i;
                }
                if (pPositionz.equals(charArray[i])) {
                    pIndexz = i;
                }
                if (pPositionw.equals(charArray[i])) {
                    pIndexw = i;
                }
                if (pPositiont.equals(charArray[i])) {
                    pIndext = i;
                }
            }

            if(KaleKontrol(0, k1, general2, kIndexx, gIndexy, kPosition1, gPosition2, charArray)) {
                return true;
            }
            if(KaleKontrol(8, k2, general2, kIndexy, gIndexy, kPosition2, gPosition2, charArray)) {
                return true;
            }

            if(AtKontrol(1, a1, general2, aIndexx, gIndexy, aPosition1, gPosition2, charArray)) {
                return true;
            }
            if(AtKontrol(7, a2, general2, aIndexy, gIndexy, aPosition2, gPosition2, charArray)) {
                return true;
            }

            if(TopKontrol(9, t1, general2, tIndexx, gIndexy, tPosition1, gPosition2, charArray)) {
                return true;
            }
            if(TopKontrol(10, t2, general2, tIndexy, gIndexy, tPosition2, gPosition2, charArray)) {
                return true;
            }

            if(PiyonKontrol(11, p1, general2, pIndexx, gIndexy, pPosition1, gPosition2, charArray)) {
                return true;
            }
            if(PiyonKontrol(12, p2, general2, pIndexy, gIndexy, pPosition2, gPosition2, charArray)) {
                return true;
            }
            if(PiyonKontrol(13, p3, general2, pIndexz, gIndexy, pPosition3, gPosition2, charArray)) {
                return true;
            }
            if(PiyonKontrol(14, p4, general2, pIndexw, gIndexy, pPosition4, gPosition2, charArray)) {
                return true;
            }
            if(PiyonKontrol(15, p5, general2, pIndext, gIndexy, pPosition5, gPosition2, charArray)) {
                return true;
            }
            return false;
        }
        else {
            String k1 = board.items[16].getPosition();
            String k2 = board.items[24].getPosition();
            String a1 = board.items[17].getPosition();
            String a2 = board.items[23].getPosition();
            String t1 = board.items[25].getPosition();
            String t2 = board.items[26].getPosition();
            String p1 = board.items[27].getPosition();
            String p2 = board.items[28].getPosition();
            String p3 = board.items[29].getPosition();
            String p4 = board.items[30].getPosition();
            String p5 = board.items[31].getPosition();

            if(k1.equals("xx")) {
                k1 = "x0";
            }
            if(k2.equals("xx")) {
                k2 = "x0";
            }
            if(a1.equals("xx")) {
                a1 = "x0";
            }
            if(a2.equals("xx")) {
                a2 = "x0";
            }
            if(t1.equals("xx")) {
                t1 = "x0";
            }
            if(t2.equals("xx")) {
                t2 = "x0";
            }
            if(p1.equals("xx")) {
                p1 = "x0";
            }
            if(p2.equals("xx")) {
                p2 = "x0";
            }
            if(p3.equals("xx")) {
                p3 = "x0";
            }
            if(p4.equals("xx")) {
                p4 = "x0";
            }
            if(p5.equals("xx")) {
                p5 = "x0";
            }

            int kPosition1 = Integer.parseInt(k1.substring(1, 2));
            int kPosition2 = Integer.parseInt(k2.substring(1, 2));
            String kPositionx = k1.substring(0, 1);
            String kPositiony = k2.substring(0, 1);
            int kIndexx = -1, kIndexy = -1;

            int aPosition1 = Integer.parseInt(a1.substring(1, 2));
            int aPosition2 = Integer.parseInt(a2.substring(1, 2));
            String aPositionx = a1.substring(0, 1);
            String aPositiony = a2.substring(0, 1);
            int aIndexx = -1, aIndexy = -1;

            int tPosition1 = Integer.parseInt(t1.substring(1, 2));
            int tPosition2 = Integer.parseInt(t2.substring(1, 2));
            String tPositionx = t1.substring(0, 1);
            String tPositiony = t2.substring(0, 1);
            int tIndexx = -1, tIndexy = -1;

            int pPosition1 = Integer.parseInt(p1.substring(1, 2));
            int pPosition2 = Integer.parseInt(p2.substring(1, 2));
            int pPosition3 = Integer.parseInt(p3.substring(1, 2));
            int pPosition4 = Integer.parseInt(p4.substring(1, 2));
            int pPosition5 = Integer.parseInt(p5.substring(1, 2));
            String pPositionx = p1.substring(0, 1);
            String pPositiony = p2.substring(0, 1);
            String pPositionz = p3.substring(0, 1);
            String pPositionw = p4.substring(0, 1);
            String pPositiont = p5.substring(0, 1);
            int pIndexx = -1, pIndexy = -1, pIndexz = -1, pIndexw = -1, pIndext = -1;

            for(int i = 0; i < charArray.length; i++) {
                if (kPositionx.equals(charArray[i])) {
                    kIndexx = i;
                }
                if (kPositiony.equals(charArray[i])) {
                    kIndexy = i;
                }
                if (aPositionx.equals(charArray[i])) {
                    aIndexx = i;
                }
                if (aPositiony.equals(charArray[i])) {
                    aIndexy = i;
                }
                if (tPositionx.equals(charArray[i])) {
                    tIndexx = i;
                }
                if (tPositiony.equals(charArray[i])) {
                    tIndexy = i;
                }
                if (pPositionx.equals(charArray[i])) {
                    pIndexx = i;
                }
                if (pPositiony.equals(charArray[i])) {
                    pIndexy = i;
                }
                if (pPositionz.equals(charArray[i])) {
                    pIndexz = i;
                }
                if (pPositionw.equals(charArray[i])) {
                    pIndexw = i;
                }
                if (pPositiont.equals(charArray[i])) {
                    pIndext = i;
                }
            }
            if(KaleKontrol(16, k1, general1, kIndexx, gIndexx, kPosition1, gPosition1, charArray)) {
                return true;
            }
            if(KaleKontrol(24, k2, general1, kIndexy, gIndexx, kPosition2, gPosition1, charArray)) {
                return true;
            }

            if(AtKontrol(17, a1, general1, aIndexx, gIndexx, aPosition1, gPosition1, charArray)) {
                return true;
            }
            if(AtKontrol(23, a2, general1, aIndexy, gIndexx, aPosition2, gPosition1, charArray)) {
                return true;
            }

            if(TopKontrol(25, t1, general1, tIndexx, gIndexx, tPosition1, gPosition1, charArray)) {

                return true;
            }
            if(TopKontrol(26, t2, general1, tIndexy, gIndexx, tPosition2, gPosition1, charArray)) {
                return true;
            }

            if(PiyonKontrol(27, p1, general1, pIndexx, gIndexx, pPosition1, gPosition1, charArray)) {
                return true;
            }
            if(PiyonKontrol(28, p2, general1, pIndexy, gIndexx, pPosition2, gPosition1, charArray)) {
                return true;
            }
            if(PiyonKontrol(29, p3, general1, pIndexz, gIndexx, pPosition3, gPosition1, charArray)) {
                return true;
            }
            if(PiyonKontrol(30, p4, general1, pIndexw, gIndexx, pPosition4, gPosition1, charArray)) {
                return true;
            }
            if(PiyonKontrol(31, p5, general1, pIndext, gIndexx, pPosition5, gPosition1, charArray)) {
                return true;
            }
            return false;
        }
    }
    public boolean KaleKontrol(int index, String from, String to, int indexx, int indexy, int position1, int position2, String[] charArray) {
        if(from.equals("x0")) {
            return false;
        }
        int piece = 0;
        if (count % 2 == 1) {
            for(int i = 0; i < board.items.length / 2; i++) {
                if(to.equals(red.item.get(i).getPosition())) {
                    return false;
                }
            }
            for(int i = 0; i < board.items.length / 2; i++) {
                if(from.equals(black.item.get(i).getPosition())) {
                    return false;
                }
            }
            if(indexx == indexy) {
                for(int i = position1 + 1; i <= position2; i++) {
                    for(int j = 0; j < board.items.length; j++) {
                        if(board.items[j].getPosition().equals(charArray[indexx] + i)) {
                            piece++;
                        }
                    }
                }
                for(int i = position1 - 1; i >= position2; i--) {
                    for(int j = 0; j < board.items.length; j++) {
                        if(board.items[j].getPosition().equals(charArray[indexx] + i)) {
                            piece++;
                        }
                    }
                }
                if(piece == 0) {
                    return true;
                }
                if(piece == 1) {
                    for(int i = 0; i < black.item.size(); i++) {
                        if(black.item.get(i).getPosition().equals(charArray[indexy] + position2)) {
                            return true;
                        }
                    }
                }
                return false;
            }
            if (position1 == position2) {
                for(int i = indexx + 1; i <= indexy; i++) {
                    for(int j = 0; j < board.items.length; j++) {
                        if(board.items[j].getPosition().equals(charArray[i] + position1)) {
                            piece++;
                        }
                    }
                }
                for(int i = indexx - 1; i >= indexy; i--) {
                    for(int j = 0; j < board.items.length; j++) {
                        if(board.items[j].getPosition().equals(charArray[i] + position1)) {
                            piece++;
                        }
                    }
                }
                if(piece == 0) {
                    return true;
                }
                if(piece == 1) {
                    for(int i = 0; i < black.item.size(); i++) {
                        if(black.item.get(i).getPosition().equals(charArray[indexy] + position2)) {
                            return true;
                        }
                    }
                }
                return false;
            }

        } else {
            for(int i = 0; i < board.items.length / 2; i++) {
                if(to.equals(black.item.get(i).getPosition())) {
                    return false;
                }
            }
            for(int i = 0; i < board.items.length / 2; i++) {
                if(from.equals(red.item.get(i).getPosition())) {
                    return false;
                }
            }
            if(indexx == indexy) {
                for(int i = position1 + 1; i <= position2; i++) {
                    for(int j = 0; j < board.items.length; j++) {
                        if(board.items[j].getPosition().equals(charArray[indexx] + i)) {
                            piece++;
                        }
                    }
                }
                for(int i = position1 - 1; i >= position2; i--) {
                    for(int j = 0; j < board.items.length; j++) {
                        if(board.items[j].getPosition().equals(charArray[indexx] + i)) {
                            piece++;
                        }
                    }
                }
                if(piece == 0) {
                    return true;
                }
                if(piece == 1) {
                    for(int i = 0; i < red.item.size(); i++) {
                        if(red.item.get(i).getPosition().equals(charArray[indexy] + position2)) {
                            return true;
                        }
                    }
                }
                return false;
            }
            if(position1 == position2) {
                for(int i = indexx + 1; i <= indexy; i++) {
                    for(int j = 0; j < board.items.length; j++) {
                        if(board.items[j].getPosition().equals(charArray[i] + position1)) {
                            piece++;
                        }
                    }
                }
                for(int i = indexx - 1; i >= indexy; i--) {
                    for(int j = 0; j < board.items.length; j++) {
                        if(board.items[j].getPosition().equals(charArray[i] + position1)) {
                            piece++;
                        }
                    }
                }
                if(piece == 0) {
                    return true;
                }
                if(piece == 1) {
                    for(int i = 0; i < red.item.size(); i++) {
                        if(red.item.get(i).getPosition().equals(charArray[indexy] + position2)) {
                            return true;
                        }
                    }
                }
                return false;
            }
        }
        return false;
    }
    public boolean AtKontrol(int index, String from, String to, int indexx, int indexy, int position1, int position2, String[] charArray) {
        if(from.equals("x0")) {
            return false;
        }
        if (count % 2 == 1) {
            for(int i = 0; i < board.items.length / 2; i++) {
                if(to.equals(red.item.get(i).getPosition())) {
                    return false;
                }
            }
            for(int i = 0; i < board.items.length / 2; i++) {
                if(from.equals(black.item.get(i).getPosition())) {
                    return false;
                }
            }
            if((position1 - position2 == -1 || position1 - position2 == 1) && (indexx - indexy == -2)) {
                for(int i = 0; i < board.items.length; i++) {
                    if(board.items[i].getPosition().equals(charArray[indexx + 1] + position1)) {
                        return false;
                    }
                }
                return true;

            }
            if((position1 - position2 == -1 || position1 - position2 == 1) && (indexx - indexy == 2)) {
                for(int i = 0; i < board.items.length; i++) {
                    if(board.items[i].getPosition().equals(charArray[indexx - 1] + position1)) {
                        return false;
                    }
                }
                return true;

            }
            if((position1 - position2 == 2) && (indexx - indexy == 1 || indexx - indexy == -1)) {
                for(int i = 0; i < board.items.length; i++) {
                    if(board.items[i].getPosition().equals(charArray[indexx] + (position1 - 1))) {
                        return false;
                    }
                }
                return true;

            }
            if((position1 - position2 == -2) && (indexx - indexy == 1 || indexx - indexy == -1)) {
                for(int i = 0; i < board.items.length; i++) {
                    if(board.items[i].getPosition().equals(charArray[indexx] + (position1 + 1))) {
                        return false;
                    }
                }
                return true;

            }
        }
        else {
            for(int i = 0; i < board.items.length / 2; i++) {
                if(to.equals(black.item.get(i).getPosition())) {
                    return false;
                }
            }
            for(int i = 0; i < board.items.length / 2; i++) {
                if(from.equals(red.item.get(i).getPosition())) {
                    return false;
                }
            }
            if((position1 - position2 == -1 || position1 - position2 == 1) && (indexx - indexy == -2)) {
                for(int i = 0; i < board.items.length; i++) {
                    if(board.items[i].getPosition().equals(charArray[indexx + 1] + position1)) {
                        return false;
                    }
                }
                return true;

            }
            if((position1 - position2 == -1 || position1 - position2 == 1) && (indexx - indexy == 2)) {
                for(int i = 0; i < board.items.length; i++) {
                    if(board.items[i].getPosition().equals(charArray[indexx - 1] + position1)) {
                        return false;
                    }
                }
                return true;

            }
            if((position1 - position2 == 2) && (indexx - indexy == 1 || indexx - indexy == -1)) {
                for(int i = 0; i < board.items.length; i++) {
                    if(board.items[i].getPosition().equals(charArray[indexx] + (position1 - 1))) {
                        return false;
                    }
                }
                return true;

            }
            if((position1 - position2 == -2) && (indexx - indexy == 1 || indexx - indexy == -1)) {
                for(int i = 0; i < board.items.length; i++) {
                    if(board.items[i].getPosition().equals(charArray[indexx] + (position1 + 1))) {
                        return false;
                    }
                }
                return true;

            }
        }
        return false;

    }
    public boolean TopKontrol(int index, String from, String to, int indexx, int indexy, int position1, int position2, String[] charArray) {
        if(from.equals("x0")) {
            return false;
        }
        int piece = 0;
        if (count % 2 == 1) {
            for(int i = 0; i < board.items.length / 2; i++) {
                if(to.equals(red.item.get(i).getPosition())) {
                    return false;
                }
            }
            for(int i = 0; i < board.items.length / 2; i++) {
                if(from.equals(black.item.get(i).getPosition())) {
                    return false;
                }
            }
            if (indexx == indexy) {
                for(int i = position1 + 1; i <= position2; i++) {
                    for(int j = 0; j < board.items.length; j++) {
                        if(board.items[j].getPosition().equals(charArray[indexx] + i)) {
                            piece++;
                        }
                    }
                }
                for(int i = position1 - 1; i >= position2; i--) {
                    for(int j = 0; j < board.items.length; j++) {
                        if(board.items[j].getPosition().equals(charArray[indexx] + i)) {
                            piece++;
                        }
                    }
                }
                if(piece > 2 || piece == 1) {
                    return false;
                }
                if(piece == 0) {
                    return true;
                }
                if(piece == 2) {
                    for(int i = 0; i < black.item.size(); i++) {
                        if(black.item.get(i).getPosition().equals(charArray[indexy] + position2)) {
                            return true;
                        }
                    }
                    return false;
                }
            }
            if (position1 == position2) {
                for(int i = indexx + 1; i <= indexy; i++) {
                    for(int j = 0; j < board.items.length; j++) {
                        if(board.items[j].getPosition().equals(charArray[i] + position1)) {
                            piece++;
                        }
                    }
                }
                for(int i = indexx - 1; i >= indexy; i--) {
                    for(int j = 0; j < board.items.length; j++) {
                        if(board.items[j].getPosition().equals(charArray[i] + position1)) {
                            piece++;
                        }
                    }
                }
                if(piece > 2 || piece == 1) {
                    return false;
                }
                if(piece == 0) {
                    return true;
                }
                if(piece == 2) {
                    for(int i = 0; i < black.item.size(); i++) {
                        if(black.item.get(i).getPosition().equals(charArray[indexy] + position2)) {
                            return true;
                        }
                    }
                    return false;
                }
            }
        }
        else {
            for(int i = 0; i < board.items.length / 2; i++) {
                if(to.equals(black.item.get(i).getPosition())) {
                    return false;
                }
            }
            for(int i = 0; i < board.items.length / 2; i++) {
                if(from.equals(red.item.get(i).getPosition())) {
                    return false;
                }
            }
            if(indexx == indexy) {
                for(int i = position1 + 1; i <= position2; i++) {
                    for(int j = 0; j < board.items.length; j++) {
                        if(board.items[j].getPosition().equals(charArray[indexx] + i)) {
                            piece++;
                        }
                    }
                }
                for(int i = position1 - 1; i >= position2; i--) {
                    for(int j = 0; j < board.items.length; j++) {
                        if(board.items[j].getPosition().equals(charArray[indexx] + i)) {
                            piece++;
                        }
                    }
                }
                if(piece > 2 || piece == 1) {
                    return false;
                }
                if(piece == 0) {
                    return true;
                }
                if(piece == 2) {
                    for(int i = 0; i < red.item.size(); i++) {
                        if(red.item.get(i).getPosition().equals(charArray[indexy] + position2)) {
                            return true;
                        }
                    }
                    return false;
                }
            }
            if(position1 == position2) {
                for(int i = indexx + 1; i <= indexy; i++) {
                    for(int j = 0; j < board.items.length; j++) {
                        if(board.items[j].getPosition().equals(charArray[i] + position1)) {
                            piece++;
                        }
                    }
                }
                for(int i = indexx - 1; i >= indexy; i--) {
                    for(int j = 0; j < board.items.length; j++) {
                        if(board.items[j].getPosition().equals(charArray[i] + position1)) {
                            piece++;
                        }
                    }
                }
                if(piece > 2 || piece == 1) {
                    return false;
                }
                if(piece == 0) {
                    return true;
                }
                if(piece == 2) {
                    for(int i = 0; i < red.item.size(); i++) {
                        if(red.item.get(i).getPosition().equals(charArray[indexy] + position2)) {
                            return true;
                        }
                    }
                    return false;
                }
            }
        }
        return false;
    }
    public boolean PiyonKontrol(int index, String from, String to, int indexx, int indexy, int position1, int position2, String[] charArray) {
        if(from.equals("x0")) {
            return false;
        }
        if (count % 2 == 1) {
            for(int i = 0; i < board.items.length / 2; i++) {
                if(to.equals(red.item.get(i).getPosition())) {
                    return false;
                }
            }
            for(int i = 0; i < board.items.length / 2; i++) {
                if(from.equals(black.item.get(i).getPosition())) {
                    return false;
                }
            }
            if (indexx < 5) {
                if (position1 == position2 && indexx - indexy == -1) {
                    return true;
                }
                return false;
            }
            if (indexx > 4) {
                if (indexx == indexy && position1 - position2 == 1) {
                    return true;
                }
                if (indexx - indexy == -1 && position1 == position2) {
                    return true;
                }
                if (indexx == indexy && position1 - position2 == -1) {
                    return true;
                }
                return false;
            }
        }
        else {
            for(int i = 0; i < board.items.length / 2; i++) {
                if(to.equals(black.item.get(i).getPosition())) {
                    return false;
                }
            }
            for(int i = 0; i < board.items.length / 2; i++) {
                if(from.equals(red.item.get(i).getPosition())) {
                    return false;
                }
            }
            if (indexx > 4) {
                if(position1 == position2 && indexx - indexy == 1) {
                    return true;
                }
                return false;
            }
            if (indexx < 5) {
                if(position1 - position2 == 1 && indexx == indexy) {
                    return true;
                }
                if(position1 == position2 && indexx - indexy == 1) {
                    return true;
                }
                if(position1 - position2 == -1 && indexx == indexy) {
                    return true;
                }
                return false;
            }
        }
        return false;
    }
    public boolean FilKontrol(int index, String from, String to, int indexx, int indexy, int position1, int position2, String[] charArray) {
        if(from.equals("x0")) {
            return false;
        }
        if (count % 2 == 1) {
            for(int i = 0; i < board.items.length / 2; i++) {
                if(to.equals(red.item.get(i).getPosition())) {
                    return false;
                }
            }
            for(int i = 0; i < board.items.length / 2; i++) {
                if(from.equals(black.item.get(i).getPosition())) {
                    return false;
                }
            }
            if(indexy > 4) {
                return false;
            }
            if (position1 - position2 == 2 && indexx - indexy == -2) {
                for(int i = 0; i < board.items.length; i++) {
                    if(board.items[i].getPosition().equals(charArray[indexx + 1] + (position1 - 1))) {
                        return false;
                    }
                }
                return true;
            }
            if (position1 - position2 == -2 && indexx - indexy == -2) {
                for(int i = 0; i < board.items.length; i++) {
                    if(board.items[i].getPosition().equals(charArray[indexx + 1] + (position1 + 1))) {
                        return false;
                    }
                }
                return true;
            }
            if (position1 - position2 == 2 && indexx - indexy == 2) {
                for(int i = 0; i < board.items.length; i++) {
                    if(board.items[i].getPosition().equals(charArray[indexx - 1] + (position1 - 1))) {
                        return false;
                    }
                }
                return true;
            }
            if (position1 - position2 == -2 && indexx - indexy == 2) {
                for(int i = 0; i < board.items.length; i++) {
                    if(board.items[i].getPosition().equals(charArray[indexx - 1] + (position1 + 1))) {
                        return false;
                    }
                }
                return true;
            }
        }
        else {
            for(int i = 0; i < board.items.length / 2; i++) {
                if(to.equals(black.item.get(i).getPosition())) {
                    return false;
                }
            }
            for(int i = 0; i < board.items.length / 2; i++) {
                if(from.equals(red.item.get(i).getPosition())) {
                    return false;
                }
            }
            if(indexy < 5) {
                return false;
            }
            if (position1 - position2 == 2 && indexx - indexy == -2) {
                for(int i = 0; i < board.items.length; i++) {
                    if(board.items[i].getPosition().equals(charArray[indexx + 1] + (position1 - 1))) {
                        return false;
                    }
                }
                return true;
            }
            if (position1 - position2 == -2 && indexx - indexy == -2) {
                for(int i = 0; i < board.items.length; i++) {
                    if(board.items[i].getPosition().equals(charArray[indexx + 1] + (position1 + 1))) {
                        return false;
                    }
                }
                return true;
            }
            if (position1 - position2 == 2 && indexx - indexy == 2) {
                for(int i = 0; i < board.items.length; i++) {
                    if(board.items[i].getPosition().equals(charArray[indexx - 1] + (position1 - 1))) {
                        return false;
                    }
                }
                return true;
            }
            if (position1 - position2 == -2 && indexx - indexy == 2) {
                for(int i = 0; i < board.items.length; i++) {
                    if(board.items[i].getPosition().equals(charArray[indexx - 1] + (position1 + 1))) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }
    public boolean VezirKontrol(int index, String from, String to, int indexx, int indexy, int position1, int position2, String[] charArray) {
        if(from.equals("x0")) {
            return false;
        }
        if (count % 2 == 1) {
            for(int i = 0; i < board.items.length / 2; i++) {
                if(to.equals(red.item.get(i).getPosition())) {
                    return false;
                }
            }
            for(int i = 0; i < board.items.length / 2; i++) {
                if(from.equals(black.item.get(i).getPosition())) {
                    return false;
                }
            }
            if (indexy > 2 || (position2 > 6 || position2 < 4)) {
                return false;
            }
            if (position1 - position2 == 1 && indexx - indexy == -1) {
                return true;
            }
            if (position1 - position2 == -1 && indexx - indexy == -1) {
                return true;
            }
            if (position1 - position2 == 1 && indexx - indexy == 1) {
                return true;
            }
            if (position1 - position2 == -1 && indexx - indexy == 1) {
                return true;
            }
        }
        else {
            for(int i = 0; i < board.items.length / 2; i++) {
                if(to.equals(black.item.get(i).getPosition())) {
                    return false;
                }
            }
            for(int i = 0; i < board.items.length / 2; i++) {
                if(from.equals(red.item.get(i).getPosition())) {
                    return false;
                }
            }
            if (indexy < 7 || (position2 > 6 || position2 < 4)) {
                return false;
            }
            if (position1 - position2 == 1 && indexx - indexy == -1) {
                return true;
            }
            if (position1 - position2 == -1 && indexx - indexy == -1) {
                return true;
            }
            if (position1 - position2 == 1 && indexx - indexy == 1) {
                return true;
            }
            if (position1 - position2 == -1 && indexx - indexy == 1) {
                return true;
            }
        }
        return false;
    }
    public boolean ŞahKontrol(int index, String from, String to, int indexx, int indexy, int position1, int position2, String[] charArray) {
        if(from.equals("x0")) {
            return false;
        }
        if(count % 2 == 1) {
            for(int i = 0; i < board.items.length / 2; i++) {
                if(to.equals(red.item.get(i).getPosition())) {
                    return false;
                }
            }
            for(int i = 0; i < board.items.length / 2; i++) {
                if(from.equals(black.item.get(i).getPosition())) {
                    return false;
                }
            }
            if (indexy > 2 || (position2 > 6 || position2 < 4)) {
                return false;
            }
            if(indexx == indexy && position1 - position2 == 1) {
                return true;
            }
            if(indexx == indexy && position1 - position2 == -1) {
                return true;
            }
            if(indexx - indexy == -1 && position1 == position2) {
                return true;
            }
            if(indexx - indexy == 1 && position1 == position2) {
                return true;
            }
        }
        else {
            for(int i = 0; i < board.items.length / 2; i++) {
                if(to.equals(black.item.get(i).getPosition())) {
                    return false;
                }
            }
            for(int i = 0; i < board.items.length / 2; i++) {
                if(from.equals(red.item.get(i).getPosition())) {
                    return false;
                }
            }
            if (indexy < 7 || (position2 > 6 || position2 < 4)) {
                return false;
            }
            if(indexx == indexy && position1 - position2 == 1) {
                return true;
            }
            if(indexx == indexy && position1 - position2 == -1) {
                return true;
            }
            if(indexx - indexy == -1 && position1 == position2) {
                return true;
            }
            if(indexx - indexy == 1 && position1 == position2) {
                return true;
            }
        }
        return false;
    }
    public boolean isCheck() {
        String general1 = board.items[4].getPosition();
        String general2 = board.items[20].getPosition();

        int gPosition1 = Integer.parseInt(general1.substring(1, 2));
        int gPosition2 = Integer.parseInt(general2.substring(1, 2));
        String gPositionx = general1.substring(0, 1);
        String gPositiony = general2.substring(0, 1);
        int gIndexx = -1;
        int gIndexy = -1;
        for(int i = 0; i < charArray.length; i++) {
            if (gPositionx.equals(charArray[i])) {
                gIndexx = i;
            }
            if (gPositiony.equals(charArray[i])) {
                gIndexy = i;
            }
        }
        if (count % 2 == 0) {
            String k1 = board.items[0].getPosition();
            String k2 = board.items[8].getPosition();
            String a1 = board.items[1].getPosition();
            String a2 = board.items[7].getPosition();
            String t1 = board.items[9].getPosition();
            String t2 = board.items[10].getPosition();
            String p1 = board.items[11].getPosition();
            String p2 = board.items[12].getPosition();
            String p3 = board.items[13].getPosition();
            String p4 = board.items[14].getPosition();
            String p5 = board.items[15].getPosition();

            if(k1.equals("xx")) {
                k1 = "x0";
            }
            if(k2.equals("xx")) {
                k2 = "x0";
            }
            if(a1.equals("xx")) {
                a1 = "x0";
            }
            if(a2.equals("xx")) {
                a2 = "x0";
            }
            if(t1.equals("xx")) {
                t1 = "x0";
            }
            if(t2.equals("xx")) {
                t2 = "x0";
            }
            if(p1.equals("xx")) {
                p1 = "x0";
            }
            if(p2.equals("xx")) {
                p2 = "x0";
            }
            if(p3.equals("xx")) {
                p3 = "x0";
            }
            if(p4.equals("xx")) {
                p4 = "x0";
            }
            if(p5.equals("xx")) {
                p5 = "x0";
            }

            int kPosition1 = Integer.parseInt(k1.substring(1, 2));
            int kPosition2 = Integer.parseInt(k2.substring(1, 2));
            String kPositionx = k1.substring(0, 1);
            String kPositiony = k2.substring(0, 1);
            int kIndexx = -1, kIndexy = -1;

            int aPosition1 = Integer.parseInt(a1.substring(1, 2));
            int aPosition2 = Integer.parseInt(a2.substring(1, 2));
            String aPositionx = a1.substring(0, 1);
            String aPositiony = a2.substring(0, 1);
            int aIndexx = -1, aIndexy = -1;

            int tPosition1 = Integer.parseInt(t1.substring(1, 2));
            int tPosition2 = Integer.parseInt(t2.substring(1, 2));
            String tPositionx = t1.substring(0, 1);
            String tPositiony = t2.substring(0, 1);
            int tIndexx = -1, tIndexy = -1;

            int pPosition1 = Integer.parseInt(p1.substring(1, 2));
            int pPosition2 = Integer.parseInt(p2.substring(1, 2));
            int pPosition3 = Integer.parseInt(p3.substring(1, 2));
            int pPosition4 = Integer.parseInt(p4.substring(1, 2));
            int pPosition5 = Integer.parseInt(p5.substring(1, 2));
            String pPositionx = p1.substring(0, 1);
            String pPositiony = p2.substring(0, 1);
            String pPositionz = p3.substring(0, 1);
            String pPositionw = p4.substring(0, 1);
            String pPositiont = p5.substring(0, 1);
            int pIndexx = -1, pIndexy = -1, pIndexz = -1, pIndexw = -1, pIndext = -1;
            for(int i = 0; i < charArray.length; i++) {
                if (kPositionx.equals(charArray[i])) {
                    kIndexx = i;
                }
                if (kPositiony.equals(charArray[i])) {
                    kIndexy = i;
                }
                if (aPositionx.equals(charArray[i])) {
                    aIndexx = i;
                }
                if (aPositiony.equals(charArray[i])) {
                    aIndexy = i;
                }
                if (tPositionx.equals(charArray[i])) {
                    tIndexx = i;
                }
                if (tPositiony.equals(charArray[i])) {
                    tIndexy = i;
                }
                if (pPositionx.equals(charArray[i])) {
                    pIndexx = i;
                }
                if (pPositiony.equals(charArray[i])) {
                    pIndexy = i;
                }
                if (pPositionz.equals(charArray[i])) {
                    pIndexz = i;
                }
                if (pPositionw.equals(charArray[i])) {
                    pIndexw = i;
                }
                if (pPositiont.equals(charArray[i])) {
                    pIndext = i;
                }
            }

            if(Kale(0, k1, general2, kIndexx, gIndexy, kPosition1, gPosition2, charArray)) {
                return true;
            }
            if(Kale(8, k2, general2, kIndexy, gIndexy, kPosition2, gPosition2, charArray)) {
                return true;
            }

            if(At(1, a1, general2, aIndexx, gIndexy, aPosition1, gPosition2, charArray)) {
                return true;
            }
            if(At(7, a2, general2, aIndexy, gIndexy, aPosition2, gPosition2, charArray)) {
                return true;
            }

            if(Top(9, t1, general2, tIndexx, gIndexy, tPosition1, gPosition2, charArray)) {
                return true;
            }
            if(Top(10, t2, general2, tIndexy, gIndexy, tPosition2, gPosition2, charArray)) {
                return true;
            }

            if(Piyon(11, p1, general2, pIndexx, gIndexy, pPosition1, gPosition2, charArray)) {
                return true;
            }
            if(Piyon(12, p2, general2, pIndexy, gIndexy, pPosition2, gPosition2, charArray)) {
                return true;
            }
            if(Piyon(13, p3, general2, pIndexz, gIndexy, pPosition3, gPosition2, charArray)) {
                return true;
            }
            if(Piyon(14, p4, general2, pIndexw, gIndexy, pPosition4, gPosition2, charArray)) {
                return true;
            }
            if(Piyon(15, p5, general2, pIndext, gIndexy, pPosition5, gPosition2, charArray)) {
                return true;
            }
            return false;
        }
        else {
            String k1 = board.items[16].getPosition();
            String k2 = board.items[24].getPosition();
            String a1 = board.items[17].getPosition();
            String a2 = board.items[23].getPosition();
            String t1 = board.items[25].getPosition();
            String t2 = board.items[26].getPosition();
            String p1 = board.items[27].getPosition();
            String p2 = board.items[28].getPosition();
            String p3 = board.items[29].getPosition();
            String p4 = board.items[30].getPosition();
            String p5 = board.items[31].getPosition();

            if(k1.equals("xx")) {
                k1 = "x0";
            }
            if(k2.equals("xx")) {
                k2 = "x0";
            }
            if(a1.equals("xx")) {
                a1 = "x0";
            }
            if(a2.equals("xx")) {
                a2 = "x0";
            }
            if(t1.equals("xx")) {
                t1 = "x0";
            }
            if(t2.equals("xx")) {
                t2 = "x0";
            }
            if(p1.equals("xx")) {
                p1 = "x0";
            }
            if(p2.equals("xx")) {
                p2 = "x0";
            }
            if(p3.equals("xx")) {
                p3 = "x0";
            }
            if(p4.equals("xx")) {
                p4 = "x0";
            }
            if(p5.equals("xx")) {
                p5 = "x0";
            }

            int kPosition1 = Integer.parseInt(k1.substring(1, 2));
            int kPosition2 = Integer.parseInt(k2.substring(1, 2));
            String kPositionx = k1.substring(0, 1);
            String kPositiony = k2.substring(0, 1);
            int kIndexx = -1, kIndexy = -1;

            int aPosition1 = Integer.parseInt(a1.substring(1, 2));
            int aPosition2 = Integer.parseInt(a2.substring(1, 2));
            String aPositionx = a1.substring(0, 1);
            String aPositiony = a2.substring(0, 1);
            int aIndexx = -1, aIndexy = -1;

            int tPosition1 = Integer.parseInt(t1.substring(1, 2));
            int tPosition2 = Integer.parseInt(t2.substring(1, 2));
            String tPositionx = t1.substring(0, 1);
            String tPositiony = t2.substring(0, 1);
            int tIndexx = -1, tIndexy = -1;

            int pPosition1 = Integer.parseInt(p1.substring(1, 2));
            int pPosition2 = Integer.parseInt(p2.substring(1, 2));
            int pPosition3 = Integer.parseInt(p3.substring(1, 2));
            int pPosition4 = Integer.parseInt(p4.substring(1, 2));
            int pPosition5 = Integer.parseInt(p5.substring(1, 2));
            String pPositionx = p1.substring(0, 1);
            String pPositiony = p2.substring(0, 1);
            String pPositionz = p3.substring(0, 1);
            String pPositionw = p4.substring(0, 1);
            String pPositiont = p5.substring(0, 1);
            int pIndexx = -1, pIndexy = -1, pIndexz = -1, pIndexw = -1, pIndext = -1;

            for(int i = 0; i < charArray.length; i++) {
                if (kPositionx.equals(charArray[i])) {
                    kIndexx = i;
                }
                if (kPositiony.equals(charArray[i])) {
                    kIndexy = i;
                }
                if (aPositionx.equals(charArray[i])) {
                    aIndexx = i;
                }
                if (aPositiony.equals(charArray[i])) {
                    aIndexy = i;
                }
                if (tPositionx.equals(charArray[i])) {
                    tIndexx = i;
                }
                if (tPositiony.equals(charArray[i])) {
                    tIndexy = i;
                }
                if (pPositionx.equals(charArray[i])) {
                    pIndexx = i;
                }
                if (pPositiony.equals(charArray[i])) {
                    pIndexy = i;
                }
                if (pPositionz.equals(charArray[i])) {
                    pIndexz = i;
                }
                if (pPositionw.equals(charArray[i])) {
                    pIndexw = i;
                }
                if (pPositiont.equals(charArray[i])) {
                    pIndext = i;
                }
            }
            if(Kale(16, k1, general1, kIndexx, gIndexx, kPosition1, gPosition1, charArray)) {
                return true;
            }
            if(Kale(24, k2, general1, kIndexy, gIndexx, kPosition2, gPosition1, charArray)) {
                return true;
            }

            if(At(17, a1, general1, aIndexx, gIndexx, aPosition1, gPosition1, charArray)) {
                return true;
            }
            if(At(23, a2, general1, aIndexy, gIndexx, aPosition2, gPosition1, charArray)) {
                return true;
            }

            if(Top(25, t1, general1, tIndexx, gIndexx, tPosition1, gPosition1, charArray)) {

                return true;
            }
            if(Top(26, t2, general1, tIndexy, gIndexx, tPosition2, gPosition1, charArray)) {
                return true;
            }

            if(Piyon(27, p1, general1, pIndexx, gIndexx, pPosition1, gPosition1, charArray)) {
                return true;
            }
            if(Piyon(28, p2, general1, pIndexy, gIndexx, pPosition2, gPosition1, charArray)) {
                return true;
            }
            if(Piyon(29, p3, general1, pIndexz, gIndexx, pPosition3, gPosition1, charArray)) {
                return true;
            }
            if(Piyon(30, p4, general1, pIndexw, gIndexx, pPosition4, gPosition1, charArray)) {
                return true;
            }
            if(Piyon(31, p5, general1, pIndext, gIndexx, pPosition5, gPosition1, charArray)) {
                return true;
            }
            return false;
        }
    }
    public int isStillAndStillCheck() {
        int numberOfPossibilities = 0;
        if(count % 2 == 0) {

                String k1 = board.items[16].getPosition();
                String k2 = board.items[24].getPosition();
                String a1 = board.items[17].getPosition();
                String a2 = board.items[23].getPosition();
                String f1 = board.items[18].getPosition();
                String f2 = board.items[22].getPosition();
                String v1 = board.items[19].getPosition();
                String v2 = board.items[21].getPosition();
                String s1 = board.items[20].getPosition();
                String t1 = board.items[25].getPosition();
                String t2 = board.items[26].getPosition();
                String p1 = board.items[27].getPosition();
                String p2 = board.items[28].getPosition();
                String p3 = board.items[29].getPosition();
                String p4 = board.items[30].getPosition();
                String p5 = board.items[31].getPosition();

                if(k1.equals("xx")) {
                    k1 = "x0";
                }
                if(k2.equals("xx")) {
                    k2 = "x0";
                }
                if(a1.equals("xx")) {
                    a1 = "x0";
                }
                if(a2.equals("xx")) {
                    a2 = "x0";
                }
                if(t1.equals("xx")) {
                    t1 = "x0";
                }
                if(t2.equals("xx")) {
                    t2 = "x0";
                }
                if(p1.equals("xx")) {
                    p1 = "x0";
                }
                if(p2.equals("xx")) {
                    p2 = "x0";
                }
                if(p3.equals("xx")) {
                    p3 = "x0";
                }
                if(p4.equals("xx")) {
                    p4 = "x0";
                }
                if(p5.equals("xx")) {
                    p5 = "x0";
                }
                if(f1.equals("xx")) {
                    f1 = "x0";
                }
                if(f2.equals("xx")) {
                    f2 = "x0";
                }
                if(v1.equals("xx")) {
                    v1 = "x0";
                }
                if(v2.equals("xx")) {
                    v2 = "x0";
                }
                if(s1.equals("xx")) {
                    s1 = "x0";
                }

                int kPosition1 = Integer.parseInt(k1.substring(1, 2));
                int kPosition2 = Integer.parseInt(k2.substring(1, 2));
                String kPositionx = k1.substring(0, 1);
                String kPositiony = k2.substring(0, 1);
                int kIndexx = -1, kIndexy = -1;

                int aPosition1 = Integer.parseInt(a1.substring(1, 2));
                int aPosition2 = Integer.parseInt(a2.substring(1, 2));
                String aPositionx = a1.substring(0, 1);
                String aPositiony = a2.substring(0, 1);
                int aIndexx = -1, aIndexy = -1;

                int fPosition1 = Integer.parseInt(f1.substring(1, 2));
                int fPosition2 = Integer.parseInt(f2.substring(1, 2));
                String fPositionx = f1.substring(0, 1);
                String fPositiony = f2.substring(0, 1);
                int fIndexx = -1, fIndexy = -1;

                int vPosition1 = Integer.parseInt(v1.substring(1, 2));
                int vPosition2 = Integer.parseInt(v2.substring(1, 2));
                String vPositionx = v1.substring(0, 1);
                String vPositiony = v2.substring(0, 1);
                int vIndexx = -1, vIndexy = -1;

                int sPosition1 = Integer.parseInt(s1.substring(1, 2));
                String sPositionx = s1.substring(0, 1);
                int sIndexx = -1, sIndexy = -1;

                int tPosition1 = Integer.parseInt(t1.substring(1, 2));
                int tPosition2 = Integer.parseInt(t2.substring(1, 2));
                String tPositionx = t1.substring(0, 1);
                String tPositiony = t2.substring(0, 1);
                int tIndexx = -1, tIndexy = -1;

                int pPosition1 = Integer.parseInt(p1.substring(1, 2));
                int pPosition2 = Integer.parseInt(p2.substring(1, 2));
                int pPosition3 = Integer.parseInt(p3.substring(1, 2));
                int pPosition4 = Integer.parseInt(p4.substring(1, 2));
                int pPosition5 = Integer.parseInt(p5.substring(1, 2));
                String pPositionx = p1.substring(0, 1);
                String pPositiony = p2.substring(0, 1);
                String pPositionz = p3.substring(0, 1);
                String pPositionw = p4.substring(0, 1);
                String pPositiont = p5.substring(0, 1);
                int pIndexx = -1, pIndexy = -1, pIndexz = -1, pIndexw = -1, pIndext = -1;

                for(int i = 0; i < charArray.length; i++) {
                    if (kPositionx.equals(charArray[i])) {
                        kIndexx = i;
                    }
                    if (kPositiony.equals(charArray[i])) {
                        kIndexy = i;
                    }
                    if (aPositionx.equals(charArray[i])) {
                        aIndexx = i;
                    }
                    if (aPositiony.equals(charArray[i])) {
                        aIndexy = i;
                    }
                    if (tPositionx.equals(charArray[i])) {
                        tIndexx = i;
                    }
                    if (tPositiony.equals(charArray[i])) {
                        tIndexy = i;
                    }
                    if (pPositionx.equals(charArray[i])) {
                        pIndexx = i;
                    }
                    if (pPositiony.equals(charArray[i])) {
                        pIndexy = i;
                    }
                    if (pPositionz.equals(charArray[i])) {
                        pIndexz = i;
                    }
                    if (pPositionw.equals(charArray[i])) {
                        pIndexw = i;
                    }
                    if (pPositiont.equals(charArray[i])) {
                        pIndext = i;
                    }
                    if (fPositionx.equals(charArray[i])) {
                        fIndexx = i;
                    }
                    if (fPositiony.equals(charArray[i])) {
                        fIndexy = i;
                    }
                    if (vPositionx.equals(charArray[i])) {
                        vIndexx = i;
                    }
                    if (vPositiony.equals(charArray[i])) {
                        vIndexy = i;
                    }
                    if (sPositionx.equals(charArray[i])) {
                        sIndexx = i;
                    }
                }
                for(int i = 0; i <= 9; i++) {
                    for(int j = 1; j <= 9; j++) {
                        if(KaleKontrol(0, k1, charArray[i] + j, kIndexx, i, kPosition1, j, charArray)) {
                            boolean isEaten = false;
                            int eatenIndex = -1;
                            board.items[16].setPosition(charArray[i] + j);
                            black.item.add(0, board.items[16]);
                            black.item.remove(1);
                            for(int k = 0; k < board.items.length / 2; k++) {
                                if((charArray[i] + j).equals(red.item.get(k).getPosition())) {
                                    isEaten = true;
                                    eatenIndex = k;
                                    board.items[k].setPosition("xx");
                                    red.item.add(k, board.items[k]);
                                    red.item.remove(k + 1);
                                }
                            }
                            if(isCheck()) {
                                board.items[16].setPosition(k1);
                                black.item.add(0, board.items[16]);
                                black.item.remove(1);
                                if(isEaten) {
                                    board.items[eatenIndex].setPosition(charArray[i] + j);
                                    red.item.add(eatenIndex, board.items[eatenIndex]);
                                    red.item.remove(eatenIndex + 1);
                                }
                            }
                            else {
                                board.items[16].setPosition(k1);
                                black.item.add(0, board.items[16]);
                                black.item.remove(1);
                                if(isEaten) {
                                    board.items[eatenIndex].setPosition(charArray[i] + j);
                                    red.item.add(eatenIndex, board.items[eatenIndex]);
                                    red.item.remove(eatenIndex + 1);
                                }
                               numberOfPossibilities++;
                            }
                        }
                        if(AtKontrol(0, a1, charArray[i] + j, aIndexx, i, aPosition1, j, charArray)) {
                            boolean isEaten = false;
                            int eatenIndex = -1;
                            board.items[17].setPosition(charArray[i] + j);
                            black.item.add(1, board.items[17]);
                            black.item.remove(2);
                            for(int k = 0; k < board.items.length / 2; k++) {
                                if((charArray[i] + j).equals(red.item.get(k).getPosition())) {
                                    isEaten = true;
                                    eatenIndex = k;
                                    board.items[k].setPosition("xx");
                                    red.item.add(k, board.items[k]);
                                    red.item.remove(k + 1);
                                }
                            }
                            if(isCheck()) {
                                board.items[17].setPosition(a1);
                                black.item.add(1, board.items[17]);
                                black.item.remove(2);
                                if(isEaten) {
                                    board.items[eatenIndex].setPosition(charArray[i] + j);
                                    red.item.add(eatenIndex, board.items[eatenIndex]);
                                    red.item.remove(eatenIndex + 1);
                                }
                            } else {
                            board.items[17].setPosition(a1);
                            black.item.add(1, board.items[17]);
                            black.item.remove(2);
                            if(isEaten) {
                                board.items[eatenIndex].setPosition(charArray[i] + j);
                                red.item.add(eatenIndex, board.items[eatenIndex]);
                                red.item.remove(eatenIndex + 1);
                            }  numberOfPossibilities++; }
                        }
                        if(FilKontrol(0, f1, charArray[i] + j, fIndexx, i, fPosition1, j, charArray)) {
                            boolean isEaten = false;
                            int eatenIndex = -1;
                            board.items[18].setPosition(charArray[i] + j);
                            black.item.add(2, board.items[18]);
                            black.item.remove(3);
                            for(int k = 0; k < board.items.length / 2; k++) {
                                if((charArray[i] + j).equals(red.item.get(k).getPosition())) {
                                    isEaten = true;
                                    eatenIndex = k;
                                    board.items[k].setPosition("xx");
                                    red.item.add(k, board.items[k]);
                                    red.item.remove(k + 1);
                                }
                            }
                            if(isCheck()) {
                                board.items[18].setPosition(f1);
                                black.item.add(2, board.items[18]);
                                black.item.remove(3);
                                if(isEaten) {
                                    board.items[eatenIndex].setPosition(charArray[i] + j);
                                    red.item.add(eatenIndex, board.items[eatenIndex]);
                                    red.item.remove(eatenIndex + 1);
                                }
                            } else {
                            board.items[18].setPosition(f1);
                            black.item.add(2, board.items[18]);
                            black.item.remove(3);
                            if(isEaten) {
                                board.items[eatenIndex].setPosition(charArray[i] + j);
                                red.item.add(eatenIndex, board.items[eatenIndex]);
                                red.item.remove(eatenIndex + 1);
                            }  numberOfPossibilities++; }
                        }
                        if(VezirKontrol(0, v1, charArray[i] + j, vIndexx, i, vPosition1, j, charArray)) {
                            boolean isEaten = false;
                            int eatenIndex = -1;
                            board.items[19].setPosition(charArray[i] + j);
                            black.item.add(3, board.items[19]);
                            black.item.remove(4);
                            for(int k = 0; k < board.items.length / 2; k++) {
                                if((charArray[i] + j).equals(red.item.get(k).getPosition())) {
                                    isEaten = true;
                                    eatenIndex = k;
                                    board.items[k].setPosition("xx");
                                    red.item.add(k, board.items[k]);
                                    red.item.remove(k + 1);
                                }
                            }
                            if(isCheck()) {
                                board.items[19].setPosition(v1);
                                black.item.add(3, board.items[19]);
                                black.item.remove(4);
                                if(isEaten) {
                                    board.items[eatenIndex].setPosition(charArray[i] + j);
                                    red.item.add(eatenIndex, board.items[eatenIndex]);
                                    red.item.remove(eatenIndex + 1);
                                }
                            }
                            else {
                                board.items[19].setPosition(v1);
                                black.item.add(3, board.items[19]);
                                black.item.remove(4);
                                if(isEaten) {
                                    board.items[eatenIndex].setPosition(charArray[i] + j);
                                    red.item.add(eatenIndex, board.items[eatenIndex]);
                                    red.item.remove(eatenIndex + 1);
                                }
                                numberOfPossibilities++;
                            }
                        }
                        if(ŞahKontrol(0, s1, charArray[i] + j, sIndexx, i, sPosition1, j, charArray)) {
                            boolean isEaten = false;
                            int eatenIndex = -1;
                            board.items[20].setPosition(charArray[i] + j);
                            black.item.add(4, board.items[20]);
                            black.item.remove(5);
                            for(int k = 0; k < board.items.length / 2; k++) {
                                if((charArray[i] + j).equals(red.item.get(k).getPosition())) {
                                    isEaten = true;
                                    eatenIndex = k;
                                    board.items[k].setPosition("xx");
                                    red.item.add(k, board.items[k]);
                                    red.item.remove(k + 1);
                                }
                            }
                            if(isCheck()) {
                                board.items[20].setPosition(s1);
                                black.item.add(4, board.items[20]);
                                black.item.remove(5);
                                if(isEaten) {
                                    board.items[eatenIndex].setPosition(charArray[i] + j);
                                    red.item.add(eatenIndex, board.items[eatenIndex]);
                                    red.item.remove(eatenIndex + 1);
                                }
                            }
                            else {
                                board.items[20].setPosition(s1);
                                black.item.add(4, board.items[20]);
                                black.item.remove(5);
                                if(isEaten) {
                                    board.items[eatenIndex].setPosition(charArray[i] + j);
                                    red.item.add(eatenIndex, board.items[eatenIndex]);
                                    red.item.remove(eatenIndex + 1);
                                }
                                numberOfPossibilities++;
                            }
                        }
                        if(TopKontrol(0, t1, charArray[i] + j, tIndexx, i, tPosition1, j, charArray)) {
                            boolean isEaten = false;
                            int eatenIndex = -1;
                            board.items[25].setPosition(charArray[i] + j);
                            black.item.add(9, board.items[25]);
                            black.item.remove(10);
                            for(int k = 0; k < board.items.length / 2; k++) {
                                if((charArray[i] + j).equals(red.item.get(k).getPosition())) {
                                    isEaten = true;
                                    eatenIndex = k;
                                    board.items[k].setPosition("xx");
                                    red.item.add(k, board.items[k]);
                                    red.item.remove(k + 1);
                                }
                            }
                            if(isCheck()) {
                                board.items[25].setPosition(t1);
                                black.item.add(9, board.items[25]);
                                black.item.remove(10);
                                if(isEaten) {
                                    board.items[eatenIndex].setPosition(charArray[i] + j);
                                    red.item.add(eatenIndex, board.items[eatenIndex]);
                                    red.item.remove(eatenIndex + 1);
                                }
                            }
                            else {
                                board.items[25].setPosition(t1);
                                black.item.add(9, board.items[25]);
                                black.item.remove(10);
                                if(isEaten) {
                                    board.items[eatenIndex].setPosition(charArray[i] + j);
                                    red.item.add(eatenIndex, board.items[eatenIndex]);
                                    red.item.remove(eatenIndex + 1);
                                }
                                numberOfPossibilities++;
                            }
                        }
                        if(PiyonKontrol(0, p1, charArray[i] + j, pIndexx, i, pPosition1, j, charArray)) {
                            boolean isEaten = false;
                            int eatenIndex = -1;
                            board.items[27].setPosition(charArray[i] + j);
                            black.item.add(11, board.items[27]);
                            black.item.remove(12);
                            for(int k = 0; k < board.items.length / 2; k++) {
                                if((charArray[i] + j).equals(red.item.get(k).getPosition())) {
                                    isEaten = true;
                                    eatenIndex = k;
                                    board.items[k].setPosition("xx");
                                    red.item.add(k, board.items[k]);
                                    red.item.remove(k + 1);
                                }
                            }
                            if(isCheck()) {
                                board.items[27].setPosition(p1);
                                black.item.add(11, board.items[27]);
                                black.item.remove(12);
                                if(isEaten) {
                                    board.items[eatenIndex].setPosition(charArray[i] + j);
                                    red.item.add(eatenIndex, board.items[eatenIndex]);
                                    red.item.remove(eatenIndex + 1);
                                }
                            }
                            else {
                                board.items[27].setPosition(p1);
                                black.item.add(11, board.items[27]);
                                black.item.remove(12);
                                if(isEaten) {
                                    board.items[eatenIndex].setPosition(charArray[i] + j);
                                    red.item.add(eatenIndex, board.items[eatenIndex]);
                                    red.item.remove(eatenIndex + 1);
                                }
                                numberOfPossibilities++;
                            }
                        }
                        if(KaleKontrol(8, k2, charArray[i] + j, kIndexy, i, kPosition2, j, charArray)) {
                            boolean isEaten = false;
                            int eatenIndex = -1;
                            board.items[24].setPosition(charArray[i] + j);
                            black.item.add(8, board.items[24]);
                            black.item.remove(9);
                            for(int k = 0; k < board.items.length / 2; k++) {
                                if((charArray[i] + j).equals(red.item.get(k).getPosition())) {
                                    isEaten = true;
                                    eatenIndex = k;
                                    board.items[k].setPosition("xx");
                                    red.item.add(k, board.items[k]);
                                    red.item.remove(k + 1);
                                }
                            }
                            if(isCheck()) {
                                board.items[24].setPosition(k2);
                                black.item.add(8, board.items[24]);
                                black.item.remove(9);
                                if(isEaten) {
                                    board.items[eatenIndex].setPosition(charArray[i] + j);
                                    red.item.add(eatenIndex, board.items[eatenIndex]);
                                    red.item.remove(eatenIndex + 1);
                                }
                            }
                            else {
                                board.items[24].setPosition(k2);
                                black.item.add(8, board.items[24]);
                                black.item.remove(9);
                                if(isEaten) {
                                    board.items[eatenIndex].setPosition(charArray[i] + j);
                                    red.item.add(eatenIndex, board.items[eatenIndex]);
                                    red.item.remove(eatenIndex + 1);
                                }
                                numberOfPossibilities++;
                            }
                        }
                        if(AtKontrol(7, a2, charArray[i] + j, aIndexy, i, aPosition2, j, charArray)) {
                            boolean isEaten = false;
                            int eatenIndex = -1;
                            board.items[23].setPosition(charArray[i] + j);
                            black.item.add(7, board.items[23]);
                            black.item.remove(8);
                            for(int k = 0; k < board.items.length / 2; k++) {
                                if((charArray[i] + j).equals(red.item.get(k).getPosition())) {
                                    isEaten = true;
                                    eatenIndex = k;
                                    board.items[k].setPosition("xx");
                                    red.item.add(k, board.items[k]);
                                    red.item.remove(k + 1);
                                }
                            }
                            if(isCheck()) {
                                board.items[23].setPosition(a2);
                                black.item.add(7, board.items[23]);
                                black.item.remove(8);
                                if(isEaten) {
                                    board.items[eatenIndex].setPosition(charArray[i] + j);
                                    red.item.add(eatenIndex, board.items[eatenIndex]);
                                    red.item.remove(eatenIndex + 1);
                                }
                            }
                            else {
                                board.items[23].setPosition(a2);
                                black.item.add(7, board.items[23]);
                                black.item.remove(8);
                                if(isEaten) {
                                    board.items[eatenIndex].setPosition(charArray[i] + j);
                                    red.item.add(eatenIndex, board.items[eatenIndex]);
                                    red.item.remove(eatenIndex + 1);
                                }
                                numberOfPossibilities++;
                            }
                        }
                        if(FilKontrol(6, f2, charArray[i] + j, fIndexy, i, fPosition2, j, charArray)) {
                            boolean isEaten = false;
                            int eatenIndex = -1;
                            board.items[22].setPosition(charArray[i] + j);
                            black.item.add(6, board.items[22]);
                            black.item.remove(7);
                            for(int k = 0; k < board.items.length / 2; k++) {
                                if((charArray[i] + j).equals(red.item.get(k).getPosition())) {
                                    isEaten = true;
                                    eatenIndex = k;
                                    board.items[k].setPosition("xx");
                                    red.item.add(k, board.items[k]);
                                    red.item.remove(k + 1);
                                }
                            }
                            if(isCheck()) {
                                board.items[22].setPosition(f2);
                                black.item.add(6, board.items[22]);
                                black.item.remove(7);
                                if(isEaten) {
                                    board.items[eatenIndex].setPosition(charArray[i] + j);
                                    red.item.add(eatenIndex, board.items[eatenIndex]);
                                    red.item.remove(eatenIndex + 1);
                                }
                            }
                            else {
                                board.items[22].setPosition(f2);
                                black.item.add(6, board.items[22]);
                                black.item.remove(7);
                                if(isEaten) {
                                    board.items[eatenIndex].setPosition(charArray[i] + j);
                                    red.item.add(eatenIndex, board.items[eatenIndex]);
                                    red.item.remove(eatenIndex + 1);
                                }
                                numberOfPossibilities++;
                            }
                        }
                        if(VezirKontrol(5, v2, charArray[i] + j, vIndexy, i, vPosition2, j, charArray)) {
                            boolean isEaten = false;
                            int eatenIndex = -1;
                            board.items[21].setPosition(charArray[i] + j);
                            black.item.add(5, board.items[21]);
                            black.item.remove(6);
                            for(int k = 0; k < board.items.length / 2; k++) {
                                if((charArray[i] + j).equals(red.item.get(k).getPosition())) {
                                    isEaten = true;
                                    eatenIndex = k;
                                    board.items[k].setPosition("xx");
                                    red.item.add(k, board.items[k]);
                                    red.item.remove(k + 1);
                                }
                            }
                            if(isCheck()) {
                                board.items[21].setPosition(v2);
                                black.item.add(5, board.items[21]);
                                black.item.remove(6);
                                if(isEaten) {
                                    board.items[eatenIndex].setPosition(charArray[i] + j);
                                    red.item.add(eatenIndex, board.items[eatenIndex]);
                                    red.item.remove(eatenIndex + 1);
                                }
                            }
                            else {
                                board.items[21].setPosition(v2);
                                black.item.add(5, board.items[21]);
                                black.item.remove(6);
                                if(isEaten) {
                                    board.items[eatenIndex].setPosition(charArray[i] + j);
                                    red.item.add(eatenIndex, board.items[eatenIndex]);
                                    red.item.remove(eatenIndex + 1);
                                }
                                numberOfPossibilities++;
                            }
                        }
                        if(TopKontrol(10, t2, charArray[i] + j, tIndexy, i, tPosition2, j, charArray)) {
                            boolean isEaten = false;
                            int eatenIndex = -1;
                            board.items[26].setPosition(charArray[i] + j);
                            black.item.add(10, board.items[26]);
                            black.item.remove(11);
                            for(int k = 0; k < board.items.length / 2; k++) {
                                if((charArray[i] + j).equals(red.item.get(k).getPosition())) {
                                    isEaten = true;
                                    eatenIndex = k;
                                    board.items[k].setPosition("xx");
                                    red.item.add(k, board.items[k]);
                                    red.item.remove(k + 1);
                                }
                            }
                            if(isCheck()) {
                                board.items[26].setPosition(t2);
                                black.item.add(10, board.items[26]);
                                black.item.remove(11);
                                if(isEaten) {
                                    board.items[eatenIndex].setPosition(charArray[i] + j);
                                    red.item.add(eatenIndex, board.items[eatenIndex]);
                                    red.item.remove(eatenIndex + 1);
                                }
                            }
                            else {
                                board.items[26].setPosition(t2);
                                black.item.add(10, board.items[26]);
                                black.item.remove(11);
                                if(isEaten) {
                                    board.items[eatenIndex].setPosition(charArray[i] + j);
                                    red.item.add(eatenIndex, board.items[eatenIndex]);
                                    red.item.remove(eatenIndex + 1);
                                }
                                numberOfPossibilities++;
                            }
                        }
                        if(PiyonKontrol(12, p2, charArray[i] + j, pIndexy, i, pPosition2, j, charArray)) {
                            boolean isEaten = false;
                            int eatenIndex = -1;
                            board.items[28].setPosition(charArray[i] + j);
                            black.item.add(12, board.items[28]);
                            black.item.remove(13);
                            for(int k = 0; k < board.items.length / 2; k++) {
                                if((charArray[i] + j).equals(red.item.get(k).getPosition())) {
                                    isEaten = true;
                                    eatenIndex = k;
                                    board.items[k].setPosition("xx");
                                    red.item.add(k, board.items[k]);
                                    red.item.remove(k + 1);
                                }
                            }
                            if(isCheck()) {
                                board.items[28].setPosition(p2);
                                black.item.add(12, board.items[28]);
                                black.item.remove(13);
                                if(isEaten) {
                                    board.items[eatenIndex].setPosition(charArray[i] + j);
                                    red.item.add(eatenIndex, board.items[eatenIndex]);
                                    red.item.remove(eatenIndex + 1);
                                }
                            }
                            else {
                                board.items[28].setPosition(p2);
                                black.item.add(12, board.items[28]);
                                black.item.remove(13);
                                if(isEaten) {
                                    board.items[eatenIndex].setPosition(charArray[i] + j);
                                    red.item.add(eatenIndex, board.items[eatenIndex]);
                                    red.item.remove(eatenIndex + 1);
                                }
                                numberOfPossibilities++;
                            }
                        }
                        if(PiyonKontrol(13, p3, charArray[i] + j, pIndexz, i, pPosition3, j, charArray)) {
                            boolean isEaten = false;
                            int eatenIndex = -1;
                            board.items[29].setPosition(charArray[i] + j);
                            black.item.add(13, board.items[29]);
                            black.item.remove(14);
                            for(int k = 0; k < board.items.length / 2; k++) {
                                if((charArray[i] + j).equals(red.item.get(k).getPosition())) {
                                    isEaten = true;
                                    eatenIndex = k;
                                    board.items[k].setPosition("xx");
                                    red.item.add(k, board.items[k]);
                                    red.item.remove(k + 1);
                                }
                            }
                            if(isCheck()) {
                                board.items[29].setPosition(p3);
                                black.item.add(13, board.items[29]);
                                black.item.remove(14);
                                if(isEaten) {
                                    board.items[eatenIndex].setPosition(charArray[i] + j);
                                    red.item.add(eatenIndex, board.items[eatenIndex]);
                                    red.item.remove(eatenIndex + 1);
                                }
                            }
                            else {
                                board.items[29].setPosition(p3);
                                black.item.add(13, board.items[29]);
                                black.item.remove(14);
                                if(isEaten) {
                                    board.items[eatenIndex].setPosition(charArray[i] + j);
                                    red.item.add(eatenIndex, board.items[eatenIndex]);
                                    red.item.remove(eatenIndex + 1);
                                }
                                numberOfPossibilities++;
                            }
                        }
                        if(PiyonKontrol(14, p4, charArray[i] + j, pIndexw, i, pPosition4, j, charArray)) {
                            boolean isEaten = false;
                            int eatenIndex = -1;
                            board.items[30].setPosition(charArray[i] + j);
                            black.item.add(14, board.items[30]);
                            black.item.remove(15);
                            for(int k = 0; k < board.items.length / 2; k++) {
                                if((charArray[i] + j).equals(red.item.get(k).getPosition())) {
                                    isEaten = true;
                                    eatenIndex = k;
                                    board.items[k].setPosition("xx");
                                    red.item.add(k, board.items[k]);
                                    red.item.remove(k + 1);
                                }
                            }
                            if(isCheck()) {
                                board.items[30].setPosition(p4);
                                black.item.add(14, board.items[30]);
                                black.item.remove(15);
                                if(isEaten) {
                                    board.items[eatenIndex].setPosition(charArray[i] + j);
                                    red.item.add(eatenIndex, board.items[eatenIndex]);
                                    red.item.remove(eatenIndex + 1);
                                }
                            }
                            else {
                                board.items[30].setPosition(p4);
                                black.item.add(14, board.items[30]);
                                black.item.remove(15);
                                if(isEaten) {
                                    board.items[eatenIndex].setPosition(charArray[i] + j);
                                    red.item.add(eatenIndex, board.items[eatenIndex]);
                                    red.item.remove(eatenIndex + 1);
                                }
                                numberOfPossibilities++;
                            }
                        }
                        if(PiyonKontrol(15, p5, charArray[i] + j, pIndext, i, pPosition5, j, charArray)) {
                            boolean isEaten = false;
                            int eatenIndex = -1;
                            board.items[31].setPosition(charArray[i] + j);
                            black.item.add(15, board.items[31]);
                            black.item.remove(16);
                            for(int k = 0; k < board.items.length / 2; k++) {
                                if((charArray[i] + j).equals(red.item.get(k).getPosition())) {
                                    isEaten = true;
                                    eatenIndex = k;
                                    board.items[k].setPosition("xx");
                                    red.item.add(k, board.items[k]);
                                    red.item.remove(k + 1);
                                }
                            }
                            if(isCheck()) {
                                board.items[31].setPosition(p5);
                                black.item.add(15, board.items[31]);
                                black.item.remove(16);
                                if(isEaten) {
                                    board.items[eatenIndex].setPosition(charArray[i] + j);
                                    red.item.add(eatenIndex, board.items[eatenIndex]);
                                    red.item.remove(eatenIndex + 1);
                                }
                            }
                            else {
                                board.items[31].setPosition(p5);
                                black.item.add(15, board.items[31]);
                                black.item.remove(16);
                                if(isEaten) {
                                    board.items[eatenIndex].setPosition(charArray[i] + j);
                                    red.item.add(eatenIndex, board.items[eatenIndex]);
                                    red.item.remove(eatenIndex + 1);
                                }
                                numberOfPossibilities++;
                            }
                        }
                    }

            }
                return numberOfPossibilities;
        }
        else {
            String k1 = board.items[0].getPosition();
            String k2 = board.items[8].getPosition();
            String a1 = board.items[1].getPosition();
            String a2 = board.items[7].getPosition();
            String f1 = board.items[2].getPosition();
            String f2 = board.items[6].getPosition();
            String v1 = board.items[3].getPosition();
            String v2 = board.items[5].getPosition();
            String s1 = board.items[4].getPosition();
            String t1 = board.items[9].getPosition();
            String t2 = board.items[10].getPosition();
            String p1 = board.items[11].getPosition();
            String p2 = board.items[12].getPosition();
            String p3 = board.items[13].getPosition();
            String p4 = board.items[14].getPosition();
            String p5 = board.items[15].getPosition();

            if(k1.equals("xx")) {
                k1 = "x0";
            }
            if(k2.equals("xx")) {
                k2 = "x0";
            }
            if(a1.equals("xx")) {
                a1 = "x0";
            }
            if(a2.equals("xx")) {
                a2 = "x0";
            }
            if(t1.equals("xx")) {
                t1 = "x0";
            }
            if(t2.equals("xx")) {
                t2 = "x0";
            }
            if(p1.equals("xx")) {
                p1 = "x0";
            }
            if(p2.equals("xx")) {
                p2 = "x0";
            }
            if(p3.equals("xx")) {
                p3 = "x0";
            }
            if(p4.equals("xx")) {
                p4 = "x0";
            }
            if(p5.equals("xx")) {
                p5 = "x0";
            }
            if(f1.equals("xx")) {
                f1 = "x0";
            }
            if(f2.equals("xx")) {
                f2 = "x0";
            }
            if(v1.equals("xx")) {
                v1 = "x0";
            }
            if(v2.equals("xx")) {
                v2 = "x0";
            }
            if(s1.equals("xx")) {
                s1 = "x0";
            }

            int kPosition1 = Integer.parseInt(k1.substring(1, 2));
            int kPosition2 = Integer.parseInt(k2.substring(1, 2));
            String kPositionx = k1.substring(0, 1);
            String kPositiony = k2.substring(0, 1);
            int kIndexx = -1, kIndexy = -1;

            int aPosition1 = Integer.parseInt(a1.substring(1, 2));
            int aPosition2 = Integer.parseInt(a2.substring(1, 2));
            String aPositionx = a1.substring(0, 1);
            String aPositiony = a2.substring(0, 1);
            int aIndexx = -1, aIndexy = -1;

            int fPosition1 = Integer.parseInt(f1.substring(1, 2));
            int fPosition2 = Integer.parseInt(f2.substring(1, 2));
            String fPositionx = f1.substring(0, 1);
            String fPositiony = f2.substring(0, 1);
            int fIndexx = -1, fIndexy = -1;

            int vPosition1 = Integer.parseInt(v1.substring(1, 2));
            int vPosition2 = Integer.parseInt(v2.substring(1, 2));
            String vPositionx = v1.substring(0, 1);
            String vPositiony = v2.substring(0, 1);
            int vIndexx = -1, vIndexy = -1;

            int sPosition1 = Integer.parseInt(s1.substring(1, 2));
            String sPositionx = s1.substring(0, 1);
            int sIndexx = -1, sIndexy = -1;

            int tPosition1 = Integer.parseInt(t1.substring(1, 2));
            int tPosition2 = Integer.parseInt(t2.substring(1, 2));
            String tPositionx = t1.substring(0, 1);
            String tPositiony = t2.substring(0, 1);
            int tIndexx = -1, tIndexy = -1;

            int pPosition1 = Integer.parseInt(p1.substring(1, 2));
            int pPosition2 = Integer.parseInt(p2.substring(1, 2));
            int pPosition3 = Integer.parseInt(p3.substring(1, 2));
            int pPosition4 = Integer.parseInt(p4.substring(1, 2));
            int pPosition5 = Integer.parseInt(p5.substring(1, 2));
            String pPositionx = p1.substring(0, 1);
            String pPositiony = p2.substring(0, 1);
            String pPositionz = p3.substring(0, 1);
            String pPositionw = p4.substring(0, 1);
            String pPositiont = p5.substring(0, 1);
            int pIndexx = -1, pIndexy = -1, pIndexz = -1, pIndexw = -1, pIndext = -1;

            for(int i = 0; i < charArray.length; i++) {
                if (kPositionx.equals(charArray[i])) {
                    kIndexx = i;
                }
                if (kPositiony.equals(charArray[i])) {
                    kIndexy = i;
                }
                if (aPositionx.equals(charArray[i])) {
                    aIndexx = i;
                }
                if (aPositiony.equals(charArray[i])) {
                    aIndexy = i;
                }
                if (tPositionx.equals(charArray[i])) {
                    tIndexx = i;
                }
                if (tPositiony.equals(charArray[i])) {
                    tIndexy = i;
                }
                if (pPositionx.equals(charArray[i])) {
                    pIndexx = i;
                }
                if (pPositiony.equals(charArray[i])) {
                    pIndexy = i;
                }
                if (pPositionz.equals(charArray[i])) {
                    pIndexz = i;
                }
                if (pPositionw.equals(charArray[i])) {
                    pIndexw = i;
                }
                if (pPositiont.equals(charArray[i])) {
                    pIndext = i;
                }
                if (fPositionx.equals(charArray[i])) {
                    fIndexx = i;
                }
                if (fPositiony.equals(charArray[i])) {
                    fIndexy = i;
                }
                if (vPositionx.equals(charArray[i])) {
                    vIndexx = i;
                }
                if (vPositiony.equals(charArray[i])) {
                    vIndexy = i;
                }
                if (sPositionx.equals(charArray[i])) {
                    sIndexx = i;
                }
            }
            for(int i = 0; i <= 9; i++) {
                for(int j = 1; j <= 9; j++) {
                    if(KaleKontrol(0, k1, charArray[i] + j, kIndexx, i, kPosition1, j, charArray)) {
                        boolean isEaten = false;
                        int eatenIndex = -1;
                        board.items[0].setPosition(charArray[i] + j);
                        red.item.add(0, board.items[0]);
                        red.item.remove(1);
                        for(int k = 0; k < board.items.length / 2; k++) {
                            if((charArray[i] + j).equals(black.item.get(k).getPosition())) {
                                isEaten = true;
                                eatenIndex = k;
                                board.items[k + 16].setPosition("xx");
                                black.item.add(k, board.items[k + 16]);
                                black.item.remove(k + 1);
                            }
                        }
                        if(isCheck()) {
                            board.items[0].setPosition(k1);
                            red.item.add(0, board.items[0]);
                            red.item.remove(1);
                            if(isEaten) {
                                board.items[eatenIndex + 16].setPosition(charArray[i] + j);
                                black.item.add(eatenIndex, board.items[eatenIndex + 16]);
                                black.item.remove(eatenIndex + 1);
                            }
                        }
                        else {
                            board.items[0].setPosition(k1);
                            red.item.add(0, board.items[0]);
                            red.item.remove(1);
                            if(isEaten) {
                                board.items[eatenIndex + 16].setPosition(charArray[i] + j);
                                black.item.add(eatenIndex, board.items[eatenIndex + 16]);
                                black.item.remove(eatenIndex + 1);
                            }
                            numberOfPossibilities++;
                        }
                    }
                    if(AtKontrol(0, a1, charArray[i] + j, aIndexx, i, aPosition1, j, charArray)) {
                        boolean isEaten = false;
                        int eatenIndex = -1;
                        board.items[1].setPosition(charArray[i] + j);
                        red.item.add(1, board.items[1]);
                        red.item.remove(2);
                        for(int k = 0; k < board.items.length / 2; k++) {
                            if((charArray[i] + j).equals(black.item.get(k).getPosition())) {
                                isEaten = true;
                                eatenIndex = k;
                                board.items[k + 16].setPosition("xx");
                                black.item.add(k, board.items[k + 16]);
                                black.item.remove(k + 1);
                            }
                        }
                        if(isCheck()) {
                            board.items[1].setPosition(a1);
                            red.item.add(1, board.items[1]);
                            red.item.remove(2);
                            if(isEaten) {
                                board.items[eatenIndex + 16].setPosition(charArray[i] + j);
                                black.item.add(eatenIndex, board.items[eatenIndex + 16]);
                                black.item.remove(eatenIndex + 1);
                            }
                        } else {
                            board.items[1].setPosition(a1);
                            red.item.add(1, board.items[1]);
                            red.item.remove(2);
                            if(isEaten) {
                                board.items[eatenIndex + 16].setPosition(charArray[i] + j);
                                black.item.add(eatenIndex, board.items[eatenIndex + 16]);
                                black.item.remove(eatenIndex + 1);
                            }  numberOfPossibilities++; }
                    }
                    if(FilKontrol(0, f1, charArray[i] + j, fIndexx, i, fPosition1, j, charArray)) {
                        boolean isEaten = false;
                        int eatenIndex = -1;
                        board.items[2].setPosition(charArray[i] + j);
                        red.item.add(2, board.items[2]);
                        red.item.remove(3);
                        for(int k = 0; k < board.items.length / 2; k++) {
                            if((charArray[i] + j).equals(black.item.get(k).getPosition())) {
                                isEaten = true;
                                eatenIndex = k;
                                board.items[k + 16].setPosition("xx");
                                black.item.add(k, board.items[k + 16]);
                                black.item.remove(k + 1);
                            }
                        }
                        if(isCheck()) {
                            board.items[2].setPosition(f1);
                            red.item.add(2, board.items[2]);
                            red.item.remove(3);
                            if(isEaten) {
                                board.items[eatenIndex + 16].setPosition(charArray[i] + j);
                                black.item.add(eatenIndex, board.items[eatenIndex + 16]);
                                black.item.remove(eatenIndex + 1);
                            }
                        } else {
                            board.items[2].setPosition(f1);
                            red.item.add(2, board.items[2]);
                            red.item.remove(3);
                            if(isEaten) {
                                board.items[eatenIndex + 16].setPosition(charArray[i] + j);
                                black.item.add(eatenIndex, board.items[eatenIndex + 16]);
                                black.item.remove(eatenIndex + 1);
                            }  numberOfPossibilities++; }
                    }
                    if(VezirKontrol(0, v1, charArray[i] + j, vIndexx, i, vPosition1, j, charArray)) {
                        boolean isEaten = false;
                        int eatenIndex = -1;
                        board.items[3].setPosition(charArray[i] + j);
                        red.item.add(3, board.items[3]);
                        red.item.remove(4);
                        for(int k = 0; k < board.items.length / 2; k++) {
                            if((charArray[i] + j).equals(black.item.get(k).getPosition())) {
                                isEaten = true;
                                eatenIndex = k;
                                board.items[k + 16].setPosition("xx");
                                black.item.add(k, board.items[k + 16]);
                                black.item.remove(k + 1);
                            }
                        }
                        if(isCheck()) {
                            board.items[3].setPosition(v1);
                            red.item.add(3, board.items[3]);
                            red.item.remove(4);
                            if(isEaten) {
                                board.items[eatenIndex + 16].setPosition(charArray[i] + j);
                                black.item.add(eatenIndex, board.items[eatenIndex + 16]);
                                black.item.remove(eatenIndex + 1);
                            }
                        }
                        else {
                            board.items[3].setPosition(v1);
                            red.item.add(3, board.items[3]);
                            red.item.remove(4);
                            if(isEaten) {
                                board.items[eatenIndex + 16].setPosition(charArray[i] + j);
                                black.item.add(eatenIndex, board.items[eatenIndex + 16]);
                                black.item.remove(eatenIndex + 1);
                            }
                            numberOfPossibilities++;
                        }
                    }
                    if(ŞahKontrol(0, s1, charArray[i] + j, sIndexx, i, sPosition1, j, charArray)) {
                        boolean isEaten = false;
                        int eatenIndex = -1;
                        board.items[4].setPosition(charArray[i] + j);
                        red.item.add(4, board.items[4]);
                        red.item.remove(5);
                        for(int k = 0; k < board.items.length / 2; k++) {
                            if((charArray[i] + j).equals(black.item.get(k).getPosition())) {
                                isEaten = true;
                                eatenIndex = k;
                                board.items[k + 16].setPosition("xx");
                                black.item.add(k, board.items[k + 16]);
                                black.item.remove(k + 1);
                            }
                        }
                        if(isCheck()) {
                            board.items[4].setPosition(s1);
                            red.item.add(4, board.items[4]);
                            red.item.remove(5);
                            if(isEaten) {
                                board.items[eatenIndex + 16].setPosition(charArray[i] + j);
                                black.item.add(eatenIndex, board.items[eatenIndex + 16]);
                                black.item.remove(eatenIndex + 1);
                            }
                        }
                        else {
                            board.items[4].setPosition(s1);
                            red.item.add(4, board.items[4]);
                            red.item.remove(5);
                            if(isEaten) {
                                board.items[eatenIndex + 16].setPosition(charArray[i] + j);
                                black.item.add(eatenIndex, board.items[eatenIndex + 16]);
                                black.item.remove(eatenIndex + 1);
                            }
                            numberOfPossibilities++;
                        }
                    }
                    if(TopKontrol(0, t1, charArray[i] + j, tIndexx, i, tPosition1, j, charArray)) {
                        boolean isEaten = false;
                        int eatenIndex = -1;
                        board.items[9].setPosition(charArray[i] + j);
                        red.item.add(9, board.items[9]);
                        red.item.remove(10);
                        for(int k = 0; k < board.items.length / 2; k++) {
                            if((charArray[i] + j).equals(black.item.get(k).getPosition())) {
                                isEaten = true;
                                eatenIndex = k;
                                board.items[k + 16].setPosition("xx");
                                black.item.add(k, board.items[k + 16]);
                                black.item.remove(k + 1);
                            }
                        }
                        if(isCheck()) {
                            board.items[9].setPosition(t1);
                            red.item.add(9, board.items[9]);
                            red.item.remove(10);
                            if(isEaten) {
                                board.items[eatenIndex + 16].setPosition(charArray[i] + j);
                                black.item.add(eatenIndex, board.items[eatenIndex + 16]);
                                black.item.remove(eatenIndex + 1);
                            }
                        }
                        else {
                            board.items[9].setPosition(t1);
                            red.item.add(9, board.items[9]);
                            red.item.remove(10);
                            if(isEaten) {
                                board.items[eatenIndex + 16].setPosition(charArray[i] + j);
                                black.item.add(eatenIndex, board.items[eatenIndex + 16]);
                                black.item.remove(eatenIndex + 1);
                            }
                            numberOfPossibilities++;
                        }
                    }
                    if(PiyonKontrol(0, p1, charArray[i] + j, pIndexx, i, pPosition1, j, charArray)) {
                        boolean isEaten = false;
                        int eatenIndex = -1;
                        board.items[11].setPosition(charArray[i] + j);
                        red.item.add(11, board.items[11]);
                        red.item.remove(12);
                        for(int k = 0; k < board.items.length / 2; k++) {
                            if((charArray[i] + j).equals(black.item.get(k).getPosition())) {
                                isEaten = true;
                                eatenIndex = k;
                                board.items[k + 16].setPosition("xx");
                                black.item.add(k, board.items[k + 16]);
                                black.item.remove(k + 1);
                            }
                        }
                        if(isCheck()) {
                            board.items[11].setPosition(p1);
                            red.item.add(11, board.items[11]);
                            red.item.remove(12);
                            if(isEaten) {
                                board.items[eatenIndex + 16].setPosition(charArray[i] + j);
                                black.item.add(eatenIndex, board.items[eatenIndex + 16]);
                                black.item.remove(eatenIndex + 1);
                            }
                        }
                        else {
                            board.items[11].setPosition(p1);
                            red.item.add(11, board.items[11]);
                            red.item.remove(12);
                            if(isEaten) {
                                board.items[eatenIndex + 16].setPosition(charArray[i] + j);
                                black.item.add(eatenIndex, board.items[eatenIndex + 16]);
                                black.item.remove(eatenIndex + 1);
                            }
                            numberOfPossibilities++;
                        }
                    }
                    if(KaleKontrol(8, k2, charArray[i] + j, kIndexy, i, kPosition2, j, charArray)) {
                        boolean isEaten = false;
                        int eatenIndex = -1;
                        board.items[8].setPosition(charArray[i] + j);
                        red.item.add(8, board.items[8]);
                        red.item.remove(9);
                        for(int k = 0; k < board.items.length / 2; k++) {
                            if((charArray[i] + j).equals(black.item.get(k).getPosition())) {
                                isEaten = true;
                                eatenIndex = k;
                                board.items[k + 16].setPosition("xx");
                                black.item.add(k, board.items[k + 16]);
                                black.item.remove(k + 1);
                            }
                        }
                        if(isCheck()) {
                            board.items[8].setPosition(k2);
                            red.item.add(8, board.items[8]);
                            red.item.remove(9);
                            if(isEaten) {
                                board.items[eatenIndex + 16].setPosition(charArray[i] + j);
                                black.item.add(eatenIndex, board.items[eatenIndex + 16]);
                                black.item.remove(eatenIndex + 1);
                            }
                        }
                        else {
                            board.items[8].setPosition(k2);
                            red.item.add(8, board.items[8]);
                            red.item.remove(9);
                            if(isEaten) {
                                board.items[eatenIndex + 16].setPosition(charArray[i] + j);
                                black.item.add(eatenIndex, board.items[eatenIndex + 16]);
                                black.item.remove(eatenIndex + 1);
                            }
                            numberOfPossibilities++;
                        }
                    }
                    if(AtKontrol(7, a2, charArray[i] + j, aIndexy, i, aPosition2, j, charArray)) {
                        boolean isEaten = false;
                        int eatenIndex = -1;
                        board.items[7].setPosition(charArray[i] + j);
                        red.item.add(7, board.items[7]);
                        red.item.remove(8);
                        for(int k = 0; k < board.items.length / 2; k++) {
                            if((charArray[i] + j).equals(black.item.get(k).getPosition())) {
                                isEaten = true;
                                eatenIndex = k;
                                board.items[k + 16].setPosition("xx");
                                black.item.add(k, board.items[k + 16]);
                                black.item.remove(k + 1);
                            }
                        }
                        if(isCheck()) {
                            board.items[7].setPosition(a2);
                            red.item.add(7, board.items[7]);
                            red.item.remove(8);
                            if(isEaten) {
                                board.items[eatenIndex + 16].setPosition(charArray[i] + j);
                                black.item.add(eatenIndex, board.items[eatenIndex + 16]);
                                black.item.remove(eatenIndex + 1);
                            }
                        }
                        else {
                            board.items[7].setPosition(a2);
                            red.item.add(7, board.items[7]);
                            red.item.remove(8);
                            if(isEaten) {
                                board.items[eatenIndex + 16].setPosition(charArray[i] + j);
                                black.item.add(eatenIndex, board.items[eatenIndex + 16]);
                                black.item.remove(eatenIndex + 1);
                            }
                            numberOfPossibilities++;
                        }
                    }
                    if(FilKontrol(6, f2, charArray[i] + j, fIndexy, i, fPosition2, j, charArray)) {
                        boolean isEaten = false;
                        int eatenIndex = -1;
                        board.items[6].setPosition(charArray[i] + j);
                        red.item.add(6, board.items[6]);
                        red.item.remove(7);
                        for(int k = 0; k < board.items.length / 2; k++) {
                            if((charArray[i] + j).equals(black.item.get(k).getPosition())) {
                                isEaten = true;
                                eatenIndex = k;
                                board.items[k + 16].setPosition("xx");
                                black.item.add(k, board.items[k + 16]);
                                black.item.remove(k + 1);
                            }
                        }
                        if(isCheck()) {
                            board.items[6].setPosition(f2);
                            red.item.add(6, board.items[6]);
                            red.item.remove(7);
                            if(isEaten) {
                                board.items[eatenIndex + 16].setPosition(charArray[i] + j);
                                black.item.add(eatenIndex, board.items[eatenIndex + 16]);
                                black.item.remove(eatenIndex + 1);
                            }
                        }
                        else {
                            board.items[6].setPosition(f2);
                            red.item.add(6, board.items[6]);
                            red.item.remove(7);
                            if(isEaten) {
                                board.items[eatenIndex + 16].setPosition(charArray[i] + j);
                                black.item.add(eatenIndex, board.items[eatenIndex + 16]);
                                black.item.remove(eatenIndex + 1);
                            }
                            numberOfPossibilities++;
                        }
                    }
                    if(VezirKontrol(5, v2, charArray[i] + j, vIndexy, i, vPosition2, j, charArray)) {
                        boolean isEaten = false;
                        int eatenIndex = -1;
                        board.items[5].setPosition(charArray[i] + j);
                        red.item.add(5, board.items[5]);
                        red.item.remove(6);
                        for(int k = 0; k < board.items.length / 2; k++) {
                            if((charArray[i] + j).equals(black.item.get(k).getPosition())) {
                                isEaten = true;
                                eatenIndex = k;
                                board.items[k + 16].setPosition("xx");
                                black.item.add(k, board.items[k + 16]);
                                black.item.remove(k + 1);
                            }
                        }
                        if(isCheck()) {
                            board.items[5].setPosition(v2);
                            red.item.add(5, board.items[5]);
                            red.item.remove(6);
                            if(isEaten) {
                                board.items[eatenIndex + 16].setPosition(charArray[i] + j);
                                black.item.add(eatenIndex, board.items[eatenIndex + 16]);
                                black.item.remove(eatenIndex + 1);
                            }
                        }
                        else {
                            board.items[5].setPosition(v2);
                            red.item.add(5, board.items[5]);
                            red.item.remove(6);
                            if(isEaten) {
                                board.items[eatenIndex + 16].setPosition(charArray[i] + j);
                                black.item.add(eatenIndex, board.items[eatenIndex + 16]);
                                black.item.remove(eatenIndex + 1);
                            }
                            numberOfPossibilities++;
                        }
                    }
                    if(TopKontrol(10, t2, charArray[i] + j, tIndexy, i, tPosition2, j, charArray)) {
                        boolean isEaten = false;
                        int eatenIndex = -1;
                        board.items[10].setPosition(charArray[i] + j);
                        red.item.add(10, board.items[10]);
                        red.item.remove(11);
                        for(int k = 0; k < board.items.length / 2; k++) {
                            if((charArray[i] + j).equals(black.item.get(k).getPosition())) {
                                isEaten = true;
                                eatenIndex = k;
                                board.items[k + 16].setPosition("xx");
                                black.item.add(k, board.items[k + 16]);
                                black.item.remove(k + 1);
                            }
                        }
                        if(isCheck()) {
                            board.items[10].setPosition(t2);
                            red.item.add(10, board.items[10]);
                            red.item.remove(11);
                            if(isEaten) {
                                board.items[eatenIndex + 16].setPosition(charArray[i] + j);
                                black.item.add(eatenIndex, board.items[eatenIndex + 16]);
                                black.item.remove(eatenIndex + 1);
                            }
                        }
                        else {
                            board.items[10].setPosition(t2);
                            red.item.add(10, board.items[10]);
                            red.item.remove(11);
                            if(isEaten) {
                                board.items[eatenIndex + 16].setPosition(charArray[i] + j);
                                black.item.add(eatenIndex, board.items[eatenIndex + 16]);
                                black.item.remove(eatenIndex + 1);
                            }
                            numberOfPossibilities++;
                        }
                    }
                    if(PiyonKontrol(12, p2, charArray[i] + j, pIndexy, i, pPosition2, j, charArray)) {
                        boolean isEaten = false;
                        int eatenIndex = -1;
                        board.items[12].setPosition(charArray[i] + j);
                        red.item.add(12, board.items[12]);
                        red.item.remove(13);
                        for(int k = 0; k < board.items.length / 2; k++) {
                            if((charArray[i] + j).equals(black.item.get(k).getPosition())) {
                                isEaten = true;
                                eatenIndex = k;
                                board.items[k + 16].setPosition("xx");
                                black.item.add(k, board.items[k + 16]);
                                black.item.remove(k + 1);
                            }
                        }
                        if(isCheck()) {
                            board.items[12].setPosition(p2);
                            red.item.add(12, board.items[12]);
                            red.item.remove(13);
                            if(isEaten) {
                                board.items[eatenIndex + 16].setPosition(charArray[i] + j);
                                black.item.add(eatenIndex, board.items[eatenIndex + 16]);
                                black.item.remove(eatenIndex + 1);
                            }
                        }
                        else {
                            board.items[12].setPosition(p2);
                            red.item.add(12, board.items[12]);
                            red.item.remove(13);
                            if(isEaten) {
                                board.items[eatenIndex + 16].setPosition(charArray[i] + j);
                                black.item.add(eatenIndex, board.items[eatenIndex + 16]);
                                black.item.remove(eatenIndex + 1);
                            }
                            numberOfPossibilities++;
                        }
                    }
                    if(PiyonKontrol(13, p3, charArray[i] + j, pIndexz, i, pPosition3, j, charArray)) {
                        boolean isEaten = false;
                        int eatenIndex = -1;
                        board.items[13].setPosition(charArray[i] + j);
                        red.item.add(13, board.items[13]);
                        red.item.remove(14);
                        for(int k = 0; k < board.items.length / 2; k++) {
                            if((charArray[i] + j).equals(black.item.get(k).getPosition())) {
                                isEaten = true;
                                eatenIndex = k;
                                board.items[k + 16].setPosition("xx");
                                black.item.add(k, board.items[k + 16]);
                                black.item.remove(k + 1);
                            }
                        }
                        if(isCheck()) {
                            board.items[13].setPosition(p3);
                            red.item.add(13, board.items[13]);
                            red.item.remove(14);
                            if(isEaten) {
                                board.items[eatenIndex + 16].setPosition(charArray[i] + j);
                                black.item.add(eatenIndex, board.items[eatenIndex + 16]);
                                black.item.remove(eatenIndex + 1);
                            }
                        }
                        else {
                            board.items[13].setPosition(p3);
                            red.item.add(13, board.items[13]);
                            red.item.remove(14);
                            if(isEaten) {
                                board.items[eatenIndex + 16].setPosition(charArray[i] + j);
                                black.item.add(eatenIndex, board.items[eatenIndex + 16]);
                                black.item.remove(eatenIndex + 1);
                            }
                            numberOfPossibilities++;
                        }
                    }
                    if(PiyonKontrol(14, p4, charArray[i] + j, pIndexw, i, pPosition4, j, charArray)) {
                        boolean isEaten = false;
                        int eatenIndex = -1;
                        board.items[14].setPosition(charArray[i] + j);
                        red.item.add(14, board.items[14]);
                        red.item.remove(15);
                        for(int k = 0; k < board.items.length / 2; k++) {
                            if((charArray[i] + j).equals(black.item.get(k).getPosition())) {
                                isEaten = true;
                                eatenIndex = k;
                                board.items[k + 16].setPosition("xx");
                                black.item.add(k, board.items[k + 16]);
                                black.item.remove(k + 1);
                            }
                        }
                        if(isCheck()) {
                            board.items[14].setPosition(p4);
                            red.item.add(14, board.items[14]);
                            red.item.remove(15);
                            if(isEaten) {
                                board.items[eatenIndex + 16].setPosition(charArray[i] + j);
                                black.item.add(eatenIndex, board.items[eatenIndex + 16]);
                                black.item.remove(eatenIndex + 1);
                            }
                        }
                        else {
                            board.items[14].setPosition(p4);
                            red.item.add(14, board.items[14]);
                            red.item.remove(15);
                            if(isEaten) {
                                board.items[eatenIndex + 16].setPosition(charArray[i] + j);
                                black.item.add(eatenIndex, board.items[eatenIndex + 16]);
                                black.item.remove(eatenIndex + 1);
                            }
                            numberOfPossibilities++;
                        }
                    }
                    if(PiyonKontrol(15, p5, charArray[i] + j, pIndext, i, pPosition5, j, charArray)) {
                        boolean isEaten = false;
                        int eatenIndex = -1;
                        board.items[15].setPosition(charArray[i] + j);
                        red.item.add(15, board.items[15]);
                        red.item.remove(16);
                        for(int k = 0; k < board.items.length / 2; k++) {
                            if((charArray[i] + j).equals(black.item.get(k).getPosition())) {
                                isEaten = true;
                                eatenIndex = k;
                                board.items[k + 16].setPosition("xx");
                                black.item.add(k, board.items[k + 16]);
                                black.item.remove(k + 1);
                            }
                        }
                        if(isCheck()) {
                            board.items[15].setPosition(p5);
                            red.item.add(15, board.items[15]);
                            red.item.remove(16);
                            if(isEaten) {
                                board.items[eatenIndex + 16].setPosition(charArray[i] + j);
                                black.item.add(eatenIndex, board.items[eatenIndex + 16]);
                                black.item.remove(eatenIndex + 1);
                            }
                        }
                        else {
                            board.items[15].setPosition(p5);
                            red.item.add(15, board.items[15]);
                            red.item.remove(16);
                            if(isEaten) {
                                board.items[eatenIndex + 16].setPosition(charArray[i] + j);
                                black.item.add(eatenIndex, board.items[eatenIndex + 16]);
                                black.item.remove(eatenIndex + 1);
                            }
                            numberOfPossibilities++;
                        }
                    }
                }

            }
            return numberOfPossibilities;
        }
    }
}
