import java.util.ArrayList;

public class Player {

    String name;
    float puan; // her taş yedikçe oyuncunun puanı taşın puanına göre artar.
    ArrayList<Item> item = new ArrayList<>();

    public Player(String name, float puan, ArrayList<Item> item) {
        this.name = name;
        this.puan = puan;
        this.item = item;
    }
}
