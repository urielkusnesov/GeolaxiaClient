package geolaxia.geolaxia.Services.Implementation;

import org.json.JSONException;

import geolaxia.geolaxia.Activities.AttackActivity;
import geolaxia.geolaxia.Activities.DefenseActivity;
import geolaxia.geolaxia.Activities.DefenseQuestionActivity;
import geolaxia.geolaxia.Activities.HomeActivity;
import geolaxia.geolaxia.Services.Interface.IDefenseService;
import geolaxia.geolaxia.Services.Interface.IPlanetService;
import geolaxia.geolaxia.Services.Interface.IRestService;

public class DefenseService implements IDefenseService {
    private final IRestService restService = RestService.getInstance();

    @Override
    public void GetShieldStatus(String username, String token, DefenseActivity context, int planetId) {
        restService.GetShieldStatus(username, token, context, planetId);
    }

    @Override
    public void GetCannons(String username, String token, DefenseActivity context, int planetId) {
        restService.GetCannons(username, token, context, planetId);
    }

    @Override
    public void BuildCannons(String username, String token, DefenseActivity context, int planetId, int cant) {
        restService.BuildCannons(username, token, context, planetId, cant);
    }

    @Override
    public void IsBuildingCannons(String username, String token, DefenseActivity context, int planetId) {
        restService.IsBuildingCannons(username, token, context, planetId);
    }

    @Override
    public void Get3RandomQuestions(String username, String token, DefenseQuestionActivity context) {
        restService.Get3RandomQuestions(username, token, context);
    }

    /*@Override
    public void Get3RandomQuestions(String username, String token, DefenseQuestionActivity context, int attackId) {
        restService.Get3RandomQuestions(username, token, context, attackId);
    }*/

    @Override
    public void ObtenerAtaqueMasProximoNoDefendido(String username, String token, DefenseActivity context, int planetId) {
        restService.ObtenerAtaqueMasProximoNoDefendido(username, token, context, planetId);
    }

    @Override
    public void DefenseFromAttack(String username, String token, DefenseQuestionActivity context, int attackId, int idPregunta1, int idPregunta2, int idPregunta3, int cantidadCorrectas) {
        restService.DefenseFromAttack(username, token, context, attackId, idPregunta1, idPregunta2, idPregunta3, cantidadCorrectas);
    }
}
