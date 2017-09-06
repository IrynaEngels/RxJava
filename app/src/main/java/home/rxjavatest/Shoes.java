package home.rxjavatest;

/**
 * Created by java on 06.09.2017.
 */

public class Shoes {
    private int size;
    private String color;
    private int heelHeight;
    private boolean zip;
    private boolean button;

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getHeelHeight() {
        return heelHeight;
    }

    public void setHeelHeight(int heelHeight) {
        this.heelHeight = heelHeight;
    }

    public boolean isZip() {
        return zip;
    }

    public void setZip(boolean zip) {
        this.zip = zip;
    }

    public boolean isButton() {
        return button;
    }

    public void setButton(boolean button) {
        this.button = button;
    }

    public Shoes(int size, String color, int heelHeight, boolean zip, boolean button) {

        this.size = size;
        this.color = color;
        this.heelHeight = heelHeight;
        this.zip = zip;
        this.button = button;
    }
}
