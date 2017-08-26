package geolaxia.geolaxia.Services.Implementation;

import geolaxia.geolaxia.Activities.AttackActivity;
import geolaxia.geolaxia.Activities.BaseAttackActivity;
import geolaxia.geolaxia.Model.Attack;
import geolaxia.geolaxia.Services.Interface.IAttackService;
import geolaxia.geolaxia.Services.Interface.IRestService;

/**
 * Created by uriel on 13/8/2017.
 */

public class AttackService implements IAttackService {
    private final IRestService restService = RestService.getInstance();

    @Override
    public void Attack(String username, String token, BaseAttackActivity context, Attack attack) {
        restService.Attack(username, token, context, attack);
    }
}
