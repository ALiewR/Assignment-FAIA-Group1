public abstract class Item {
    protected String name = "item";
    protected boolean used;
    public String description = "Power Stone does not affect cooldown";
    public int currentDurationLeft = 0;
    public final int maxDuration = 2;
    public ITEM_TYPE itemType = ITEM_TYPE.SMOKE_BOMB;
    public Item(String name)
    {
        this.name=name;
        this.used=false;
    }
    public String getName() {
        return name;
    }
    public boolean isUsed() {
        return used;
        
    }
    public void markAsUsed() {
        used=true;
        currentDurationLeft = maxDuration;
    }
    public void resetItem() {
            used=false;
    }
    public abstract void use(Combatant user, Combatant target);
    public void resetUse() { used = false; currentDurationLeft = 0; }
    public boolean getIsUsed() { return used; }
    public void depleteDuration() {
        if (used) currentDurationLeft--;
    }
}
