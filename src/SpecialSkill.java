public abstract class SpecialSkill extends Action 
{
    public SpecialSkill(String name)
    {
        super(name,"Special Skill",ACTION_TYPE.SPECIAL_SKILL,1);
        this.targetType=TARGET_TYPE.ENEMIES;
    }
}
