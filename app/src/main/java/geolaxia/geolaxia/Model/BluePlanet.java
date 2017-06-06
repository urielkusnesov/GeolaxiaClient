package geolaxia.geolaxia.Model;

import geolaxia.geolaxia.R;

/**
 * Created by uriel on 23/5/2017.
 */

public class BluePlanet extends Planet {
    public BluePlanet(int id, String name, Player conqueror, int metal, int crystal, int darkMatter, int energy, boolean isOrigin, geolaxia.geolaxia.Model.SolarSystem solarSystem, int positionX, int positionY, int positionZ, int planetType) {
        super(id, name, conqueror, metal, crystal, darkMatter, energy, isOrigin, solarSystem, positionX, positionY, positionZ, planetType);
    }

    public BluePlanet(Planet planet){
        super(planet.getId(), planet.getName(), planet.getConqueror(), planet.getMetal(), planet.getCrystal(), planet.getDarkMatter(), planet.getEnergy(),
                planet.isOrigin(), planet.getSolarSystem(), planet.getPositionX(), planet.getPositionY(), planet.getPositionZ(), planet.getPlanetType());
    }

    @Override
    public int getImage(){
        return R.drawable.planeta_azul;
    }
}
