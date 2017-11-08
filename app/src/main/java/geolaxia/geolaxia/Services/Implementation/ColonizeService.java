package geolaxia.geolaxia.Services.Implementation;

import geolaxia.geolaxia.Activities.ColonizeActivity;
import geolaxia.geolaxia.Services.Interface.IColonizeService;
import geolaxia.geolaxia.Services.Interface.IRestService;

public class ColonizeService implements IColonizeService {
    private final IRestService restService = RestService.getInstance();

    @Override
    public void GetColonizers(String username, String token, ColonizeActivity context, int planetId) {
        restService.GetColonizers(username, token, context, planetId);
    }

//    @Override
//    public void IsBuildingCannons(String username, String token, ColonizeActivity context, int planetId) {
//        restService.IsBuildingCannons(username, token, context, planetId);
//    }
}
