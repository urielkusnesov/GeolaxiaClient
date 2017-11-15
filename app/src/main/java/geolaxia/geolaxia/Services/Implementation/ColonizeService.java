package geolaxia.geolaxia.Services.Implementation;

import geolaxia.geolaxia.Activities.ColonizeActivity;
import geolaxia.geolaxia.Services.Interface.IColonizeService;
import geolaxia.geolaxia.Services.Interface.IRestService;

public class ColonizeService implements IColonizeService {
    private final IRestService restService = RestService.getInstance();

    @Override
    public void GetColonizers(String username, String token, ColonizeActivity act, ColonizeActivity.ColonizeFragment context, int planetId) {
        restService.GetColonizers(username, token, act, context, planetId);
    }

    @Override
    public void IsSendingColonizer(String username, String token, ColonizeActivity act, ColonizeActivity.ColonizeFragment context, int planetId){
        restService.IsSendingColonizer(username, token, act, context, planetId);
    }

    @Override
    public void SendColonizer(String username, String token, ColonizeActivity act, ColonizeActivity.ColonizeFragment context, int planetId, int planetIdTarget, long time) {
        restService.SendColonizer(username, token, act, context, planetId, planetIdTarget, time);
    }

    @Override
    public void GetColonizers(String username, String token, ColonizeActivity act, ColonizeActivity.CoordinatesFragment context, int planetId) {
        restService.GetColonizers(username, token, act, context, planetId);
    }

    @Override
    public void IsSendingColonizer(String username, String token, ColonizeActivity act, ColonizeActivity.CoordinatesFragment context, int planetId) {
        restService.IsSendingColonizer(username, token, act, context, planetId);
    }

    @Override
    public void SendColonizer(String username, String token, ColonizeActivity act, ColonizeActivity.CoordinatesFragment context, int planetId, int planetIdTarget, long time) {
        restService.SendColonizer(username, token, act, context, planetId, planetIdTarget, time);
    }
}
