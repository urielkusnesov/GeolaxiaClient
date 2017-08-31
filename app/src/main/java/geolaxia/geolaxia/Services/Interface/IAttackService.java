package geolaxia.geolaxia.Services.Interface;

import geolaxia.geolaxia.Activities.AttackActivity;
import geolaxia.geolaxia.Model.Attack;

/**
 * Created by uriel on 13/8/2017.
 */

public interface IAttackService {
    void Attack(String username, String token, AttackActivity context, Attack attack);
}
