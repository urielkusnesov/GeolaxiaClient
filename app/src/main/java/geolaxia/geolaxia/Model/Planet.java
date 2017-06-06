package geolaxia.geolaxia.Model;

import java.io.Serializable;

/**
 * Created by uriel on 13/5/2017.
 */

public class Planet implements Serializable{

    private int Id;
    private String Name;
    private Player Conqueror;
    private int Metal;
    private int Crystal;
    private int DarkMatter;
    private int Energy;
    private boolean IsOrigin;
    private SolarSystem SolarSystem;
    private int PositionX;
    private int PositionY;
    private int PositionZ;
    private int PlanetType;

    public Planet(int id, String name, Player conqueror, int metal, int crystal, int darkMatter, int energy, boolean isOrigin,
                  SolarSystem solarSystem, int positionX, int positionY, int positionZ, int planetType){
        this.Id = id;
        this.Name = name;
        this.Conqueror = conqueror;
        this.Metal = metal;
        this.Crystal = crystal;
        this.DarkMatter = darkMatter;
        this.Energy = energy;
        this.IsOrigin = isOrigin;
        this.SolarSystem = solarSystem;
        this.PositionX = positionX;
        this.PositionY = positionY;
        this.PositionZ = positionZ;
        this.PlanetType = planetType;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public Player getConqueror() {
        return Conqueror;
    }

    public void setConqueror(Player conqueror) {
        Conqueror = conqueror;
    }

    public int getMetal() {
        return Metal;
    }

    public void setMetal(int metal) {
        Metal = metal;
    }

    public int getCrystal() {
        return Crystal;
    }

    public void setCrystal(int crystal) {
        Crystal = crystal;
    }

    public int getDarkMatter() {
        return DarkMatter;
    }

    public void setDarkMatter(int darkMatter) {
        DarkMatter = darkMatter;
    }

    public int getEnergy() {
        return Energy;
    }

    public void setEnergy(int energy) {
        Energy = energy;
    }

    public boolean isOrigin() {
        return IsOrigin;
    }

    public void setOrigin(boolean origin) {
        IsOrigin = origin;
    }

    public SolarSystem getSolarSystem() {
        return SolarSystem;
    }

    public void setSolarSystem(SolarSystem solarSystem) {
        SolarSystem = solarSystem;
    }

    public int getPositionX() {
        return PositionX;
    }

    public void setPositionX(int positionX) {
        PositionX = positionX;
    }

    public int getPositionY() {
        return PositionY;
    }

    public void setPositionY(int positionY) {
        PositionY = positionY;
    }

    public int getPositionZ() {
        return PositionZ;
    }

    public void setPositionZ(int positionZ) {
        PositionZ = positionZ;
    }

    public int getPlanetType() {
        return PlanetType;
    }

    public void setPlanetType(int planetType) {
        PlanetType = planetType;
    }

    public int getImage(){
        return 0;
    }

    @Override
    public String toString()
    {
        return Name;
    }
}
