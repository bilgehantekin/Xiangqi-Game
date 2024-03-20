public abstract class AbstractBoard implements BoardInterface{

    Item [] items = new Item[32];

    public Item[] getItems() {
        return this.items;
    }

    public void setItems(Item[] items) {
        this.items = items;
    }
    public void firstBoard() {
        for(int i = 0; i < items.length; i++) {
            items[i] = new Item("a1");
        }
        items[1].setPosition("a2");
        items[2].setPosition("a3");
        items[3].setPosition("a4");
        items[4].setPosition("a5");
        items[5].setPosition("a6");
        items[6].setPosition("a7");
        items[7].setPosition("a8");
        items[8].setPosition("a9");
        items[9].setPosition("c2");
        items[10].setPosition("c8");
        items[11].setPosition("d1");
        items[12].setPosition("d3");
        items[13].setPosition("d5");
        items[14].setPosition("d7");
        items[15].setPosition("d9");
        items[16].setPosition("j1");
        items[17].setPosition("j2");
        items[18].setPosition("j3");
        items[19].setPosition("j4");
        items[20].setPosition("j5");
        items[21].setPosition("j6");
        items[22].setPosition("j7");
        items[23].setPosition("j8");
        items[24].setPosition("j9");
        items[25].setPosition("h2");
        items[26].setPosition("h8");
        items[27].setPosition("g1");
        items[28].setPosition("g3");
        items[29].setPosition("g5");
        items[30].setPosition("g7");
        items[31].setPosition("g9");
        /* for(int i = 0; i < items.length; i++) {
            System.out.println(items[i].getPosition());
        }*/

    }




}
