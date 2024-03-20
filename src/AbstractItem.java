public abstract class AbstractItem implements ItemInterface {

    private String position;  // tahtadaki konumu gösterir. Örneğin, a1

    public  String getPosition() {
        return this.position;
    }

    public String getPositionAgain() {
        return this.position;

    }
    public void setPosition(String position) {
        this.position = position;
    }

}
