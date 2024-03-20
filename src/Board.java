public class Board extends AbstractBoard {
    private String[] charArray = new String[]{"a", "b", "c", "d", "e", "f", "g", "h", "i", "j"};

    @Override
    public void print() {
        // TODO Auto-generated method stub
        String[] array = new String[90];
        int number = 0;
        for (int i = 0; i < array.length; i++) {
            array[i] = "-";
        }
        for (int i = 0; i <= 9; i++) {
            for (int j = 1; j <= 9; j++) {
                for (int k = 0; k < getItems().length; k++) {
                    if (getItems()[k].getPosition().equals(charArray[i] + j)) {
                        if (k == 0 || k == 8) {
                            array[number] = "k";
                        }
                        if (k == 1 || k == 7) {
                            array[number] = "a";
                        }
                        if (k == 2 || k == 6) {
                            array[number] = "f";
                        }
                        if (k == 3 || k == 5) {
                            array[number] = "v";
                        }
                        if (k == 4) {
                            array[number] = "ş";
                        }
                        if (k == 9 || k == 10) {
                            array[number] = "t";
                        }
                        if (k == 11 || k == 12 || k == 13 || k == 14 || k == 15) {
                            array[number] = "p";
                        }

                        if (k == 16 || k == 24) {
                            array[number] = "K";
                        }
                        if (k == 17 || k == 23) {
                            array[number] = "A";
                        }
                        if (k == 18 || k == 22) {
                            array[number] = "F";
                        }
                        if (k == 19 || k == 21) {
                            array[number] = "V";
                        }
                        if (k == 20) {
                            array[number] = "Ş";
                        }
                        if (k == 25 || k == 26) {
                            array[number] = "T";
                        }
                        if (k == 27 || k == 28 || k == 29 || k == 30 || k == 31) {
                            array[number] = "P";
                        }
                    }
                }
                number++;
            }
        }
        System.out.println("j\t" + array[81] + "--" + array[82] + "--" + array[83] + "--" + array[84] + "--" + array[85] + "--" + array[86] + "--" +array[87] + "--" + array[88] + "--" +array[89]);
        System.out.println(" \t|  |  |  |\\ | /|  |  |  |");
        System.out.println("i\t" + array[72] + "--" + array[73] + "--" + array[74] + "--" + array[75] + "--" + array[76] + "--" + array[77] + "--" +array[78] + "--" + array[79] + "--" +array[80]);
        System.out.println(" \t|  |  |  |/ | \\|  |  |  |");
        System.out.println("h\t" + array[63] + "--" + array[64] + "--" + array[65] + "--" + array[66] + "--" + array[67] + "--" + array[68] + "--" +array[69] + "--" + array[70] + "--" +array[71]);
        System.out.println(" \t|  |  |  |  |  |  |  |  |");
        System.out.println("g\t" + array[54] + "--" + array[55] + "--" + array[56] + "--" + array[57] + "--" + array[58] + "--" + array[59] + "--" +array[60] + "--" + array[61] + "--" +array[62]);
        System.out.println(" \t|  |  |  |  |  |  |  |  |");
        System.out.println("f\t" + array[45] + "--" + array[46] + "--" + array[47] + "--" + array[48] + "--" + array[49] + "--" + array[50] + "--" +array[51] + "--" + array[52] + "--" +array[53]);
        System.out.println(" \t|                       |");
        System.out.println("e\t" + array[36] + "--" + array[37] + "--" + array[38] + "--" + array[39] + "--" + array[40] + "--" + array[41] + "--" +array[42] + "--" + array[43] + "--" +array[44]);
        System.out.println(" \t|  |  |  |  |  |  |  |  |");
        System.out.println("d\t" + array[27] + "--" + array[28] + "--" + array[29] + "--" + array[30] + "--" + array[31] + "--" + array[32] + "--" +array[33] + "--" + array[34] + "--" +array[35]);
        System.out.println(" \t|  |  |  |  |  |  |  |  |");
        System.out.println("c\t" + array[18] + "--" + array[19] + "--" + array[20] + "--" + array[21] + "--" + array[22] + "--" + array[23] + "--" +array[24] + "--" + array[25] + "--" +array[26]);
        System.out.println(" \t|  |  |  |\\ | /|  |  |  |");
        System.out.println("b\t" + array[9] + "--" + array[10] + "--" + array[11] + "--" + array[12] + "--" + array[13] + "--" + array[14] + "--" +array[15] + "--" + array[16] + "--" +array[17]);
        System.out.println(" \t|  |  |  |/ | \\|  |  |  |");
        System.out.println("a\t" + array[0] + "--" + array[1] + "--" + array[2] + "--" + array[3] + "--" + array[4] + "--" + array[5] + "--" +array[6] + "--" + array[7] + "--" +array[8]);
        System.out.println();
        System.out.println(" \t1--2--3--4--5--6--7--8--9");
    }
}
