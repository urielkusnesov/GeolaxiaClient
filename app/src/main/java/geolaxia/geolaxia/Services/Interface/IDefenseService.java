package geolaxia.geolaxia.Services.Interface;

import org.json.JSONException;

import geolaxia.geolaxia.Activities.AttackActivity;
import geolaxia.geolaxia.Activities.DefenseActivity;
import geolaxia.geolaxia.Activities.DefenseQuestionActivity;
import geolaxia.geolaxia.Activities.HomeActivity;

public interface IDefenseService {
    void GetShieldStatus(String username, String token, DefenseActivity context, int planetId);
    void GetCannons(String username, String token, DefenseActivity context, int planetId);
    void BuildCannons(String username, String token, DefenseActivity context, int planetId, int cant);
    void IsBuildingCannons(String username, String token, DefenseActivity context, int planetId);
    void Get3RandomQuestions(String username, String token, DefenseQuestionActivity context);
    //void Get3RandomQuestions(String username, String token, DefenseQuestionActivity context, int attackId);
    void ObtenerAtaqueMasProximoNoDefendido(String username, String token, DefenseActivity context, int planetId);
    void DefenseFromAttack(String username, String token, DefenseQuestionActivity context, int attackId, int idPregunta1, int idPregunta2, int idPregunta3, int cantidadCorrectas);
}