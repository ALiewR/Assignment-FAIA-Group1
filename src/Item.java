public abstract class Item {
    protected String name = "item";
    protected boolean used;
    protected String description = "Power Stone does not affect cooldown";
    protected int currentDurationLeft = 0;
    protected final int maxDuration = 2;
    protected ITEM_TYPE itemType = ITEM_TYPE.SMOKE_BOMB;
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
    public void resetUse() { used = false; currentDurationLeft = 0; }
    public boolean getIsUsed() { return used; }
    public void markAsUsed() {
        used=true;
        currentDurationLeft = maxDuration;
    }
    public void resetItem() {
            used=false;
            currentDurationLeft=0;
    }
    public abstract void use(Combatant user, Combatant target);
    public void depleteDuration() {
        if (used) currentDurationLeft--;
    }
}
