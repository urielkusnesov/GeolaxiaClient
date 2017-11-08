package geolaxia.geolaxia.Services.Interface;

import geolaxia.geolaxia.Activities.ColonizeActivity;

public interface IColonizeService {
    void GetColonizers(String username, String token, ColonizeActivity context, int planetId);
    //void IsBuildingCannons(String username, String token, ColonizeActivity context, int planetId);
}