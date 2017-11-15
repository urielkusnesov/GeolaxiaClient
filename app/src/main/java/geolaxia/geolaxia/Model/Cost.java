package geolaxia.geolaxia.Model;

/**
 * Created by uriel on 24/9/2017.
 */

public class Cost {
    private int Id;
    private int CrystalCost;
    private int MetalCost;
    private int DarkMatterCost;
    private String Element;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getCrystalCost() {
        return CrystalCost;
    }

    public void setCrystalCost(int crystalCost) {
        CrystalCost = crystalCost;
    }

    public int getMetalCost() {
        return MetalCost;
    }

    public void setMetalCost(int metalCost) {
        MetalCost = metalCost;
    }

    public int getDarkMatterCost() {
        return DarkMatterCost;
    }

    public void setDarkMatterCost(int darkMatterCost) {
        DarkMatterCost = darkMatterCost;
    }

    public String getElement() {
        return Element;
    }

    public void setElement(String element) {
        Element = element;
    }
}
