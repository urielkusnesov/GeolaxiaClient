package geolaxia.geolaxia.Services.Interface;

import geolaxia.geolaxia.Activities.ColonizeActivity;

public interface IColonizeService {
    void GetColonizers(String username, String token, ColonizeActivity act, ColonizeActivity.ColonizeFragment context, int planetId);
    void IsSendingColonizer(String username, String token, ColonizeActivity act, ColonizeActivity.ColonizeFragment context, int planetId);
    void SendColonizer(String username, String token, ColonizeActivity act, ColonizeActivity.ColonizeFragment context, int planetId, int planetIdTarget, long time);

    void GetColonizers(String username, String token, ColonizeActivity act, ColonizeActivity.CoordinatesFragment context, int planetId);
    void IsSendingColonizer(String username, String token, ColonizeActivity act, ColonizeActivity.CoordinatesFragment context, int planetId);
    void SendColonizer(String username, String token, ColonizeActivity act, ColonizeActivity.CoordinatesFragment context, int planetId, int planetIdTarget, long time);
}