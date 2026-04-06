import java.util.List;

public class Action {
    protected String name;
    protected String description;
    protected ACTION_TYPE actionType;
    protected int numOfTargets;
    protected boolean doesInflictStatusEffectOnTarget;
    protected TARGET_TYPE targetType;
    public Action()
    {
        this.name="Basic Action";
        this.description="Performs a basic action";
        this.actionType=ACTION_TYPE.ATTACK;
        this.numOfTargets=1;
        this.doesInflictStatusEffectOnTarget=false;
        this.targetType=TARGET_TYPE.ENEMIES;
    }
        public Action(boolean inflictSE)
    {
        this.name = "shield bash";
        this.description="Deals damage and applies a status effect";
        this.actionType=ACTION_TYPE.SPECIAL_SKILL;
        this.numOfTargets=1;
        this.doesInflictStatusEffectOnTarget = inflictSE;
        this.targetType = TARGET_TYPE.ENEMIES;
        
    }
    public Action(String name, String desc, ACTION_TYPE aType, int numT) {
        this.name = name;
        this.description = desc;
        this.actionType = aType;
        this.numOfTargets = numT;
        this.doesInflictStatusEffectOnTarget=false;
        this.targetType=TARGET_TYPE.ENEMIES;
    };
    public void execute(Combatant actor, List<Combatant> targets, BattleContext battleContext) {
        for (Combatant eachTarget: targets) {
            eachTarget.oldHP = eachTarget.currentHP;
            int damage=actor.atk - eachTarget.defence;
            eachTarget.currentHP-=Math.max(damage,0);
            if (doesInflictStatusEffectOnTarget) eachTarget.afflictedStatusEffects.add(new StatusEffect());
        }
    }
    public String getName()
    {
        return name;
    }
    public int getNumOfTargets()
    {
        return numOfTargets;
    }
    public TARGET_TYPE getTargetType()
    {
        return targetType;
    }
    public boolean doesInflictStatusEffect()
    {
        return doesInflictStatusEffectOnTarget;
    }
}
