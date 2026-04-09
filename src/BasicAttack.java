import java.util.List;
public class BasicAttack extends Action
{
    public BasicAttack()
    {
        super("Basic Attack", "Deals standard damage to one enemy", ACTION_TYPE.ATTACK,1);
        this.targetType=TARGET_TYPE.ENEMIES;

    }
    @Override
    public void execute(Combatant actor, List<Combatant> targets, BattleContext battleContext) {
        for (Combatant eachTarget: targets) {
            eachTarget.savePreviousStats();
            int damage = Math.max(0, actor.atk - eachTarget.defence);
            eachTarget.takeDamage(damage);
        }
}
}
