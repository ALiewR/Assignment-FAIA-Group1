public enum COMBATANT_TYPE {
    PLAYER("Player"),
    ENEMY("Enemy");

    private final String displayName;

    COMBATANT_TYPE(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}