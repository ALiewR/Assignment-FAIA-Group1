import java.util.List;

public class Action {
    public String name = "action";
    public String description = "can do this";
    public ACTION_TYPE actionType = ACTION_TYPE.ATTACK;
    public int numOfTargets = 1;
    public boolean doesInflictStatusEffectOnTarget = false;
    public TARGET_TYPE targetType = TARGET_TYPE.ENEMIES;
    public Action() {};
    public Action(boolean inflictSE) {
        doesInflictStatusEffectOnTarget = inflictSE;
        actionType = ACTION_TYPE.SPECIAL_SKILL;
        name = "shield bash";
    }
    public Action(String name, String desc, ACTION_TYPE aType, int numT) {
        this.name = name;
        description = desc;
        actionType = aType;
        numOfTargets = numT;
    };
    public void execute(Combatant actor, List<Combatant> targets, BattleContext battleContext) {
        for (Combatant eachTarget: targets) {
            eachTarget.oldHP = eachTarget.currentHP;
            eachTarget.currentHP -= actor.atk - eachTarget.defence;
            if (doesInflictStatusEffectOnTarget) eachTarget.afflictedStatusEffects.add(new StatusEffect());
        }
    }
}
