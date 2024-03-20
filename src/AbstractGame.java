public abstract class AbstractGame {

    Board board;
    Player red;
    Player black;


    /*
     * from pozisyonundaki taşı to pozisyonuna taşır.
     * Eğer hareket kural dışı ise, ekrana "hatali hareket" mesajı ekrana yazılır ve oyuncunun tekrar oynaması için sırayı değiştirmez.
     * Eğer hareket sonucu biri oyunu kazandı ise, "ŞAH MAT! X oyunu kazandı. X'in puanı: A, Y'nin puanı: B" yazar. X ve Y oyuncuların ismidir. A ve B aldıkları puanlardır.
     * Eğer hareket sonucu pat oldu ise (şahın hiç bir yere hareket edememesi ve başka yapacak hareketinin olmaması durumu), "PAT" mesajı ekrana yazılır ve oyun sonlanır.
     * */
    abstract void play(String from, String to);


    /*
     * Oyunun o anki hali belirtilen dosyaya binary olarak kaydedilir.
     * */
    abstract void save_binary(String address);

    /*
     * Oyunun o anki hali belirtilen dosyaya metin dosyası olarak kaydedilir.
     * */
    abstract void save_text(String address);

    /*
     * Belirtilen adreste kaydedilen metin dosyasına göre oyunu yükler ve oyun kaldığı yerden devam eder.
     * Dosyanın doğru dosya olduğunu varsayabilirsiniz.
     * */
    abstract void load_text(String address);


    /*
     * Belirtilen adreste kaydedilen binary dosyaya göre oyunu yükler ve oyun kaldığı yerden devam eder.
     * Dosyanın doğru dosya olduğunu varsayabilirsiniz.
     *
     * */
    abstract void load_binary(String address);



}
