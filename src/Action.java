import java.util.List;

public abstract class Action {
    protected String name;
    protected String description;
    protected ACTION_TYPE actionType;
    protected int numOfTargets;
    protected boolean doesInflictStatusEffectOnTarget;
    protected TARGET_TYPE targetType;
    
    public Action(String name, String desc, ACTION_TYPE aType, int numT) {
        this.name = name;
        this.description = desc;
        this.actionType = aType;
        this.numOfTargets = numT;
        this.doesInflictStatusEffectOnTarget=false;
        this.targetType=TARGET_TYPE.ENEMIES;
    }
    public abstract void execute(Combatant actor, List<Combatant> targets, BattleContext battleContext);

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
