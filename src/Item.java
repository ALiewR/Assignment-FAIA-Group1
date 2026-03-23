public class Item {
    boolean isUsed = false;
    public String name = "item";
    public int currentDurationLeft = 0;
    public final int maxDuration = 2;
    public void use() { isUsed = true; currentDurationLeft = maxDuration; }
    public void resetUse() { isUsed = false; }
    public boolean getIsUsed() { return isUsed; }
    public void depleteDuration() {
        if (isUsed) currentDurationLeft--;
    }
}
