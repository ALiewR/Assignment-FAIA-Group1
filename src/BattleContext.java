import java.util.ArrayList;
import java.util.List;

public class BattleContext {
    private List<Combatant> players = new ArrayList<>();
    private List<Combatant> enemies = new ArrayList<>();
    private List<Combatant> backupEnemies = new ArrayList<>(); // only used for printing
    private List<Item> items = new ArrayList<>();
    private Level level;
    private TurnOrderStrategy turnOrderStrategy;
    private boolean isSmokeBombActive = false;

    // TEMP
    public BattleContext() {
        players.add(new Player());
        items.add(new Item("smoke bomb"));
        level = new Level(2);
        enemies = spawnEnemies(level, false);
        turnOrderStrategy = new TurnOrderStrategy();
    }

    public BattleContext(List<Combatant> selectedPlayers, List<Item> selectedItems, Level selectedLevel, TurnOrderStrategy selectedTurnOrderStrategy) {
        players = new ArrayList<>(selectedPlayers);
            // create a copy where players' stats will be modified
            // while players passed in from "options" will remain untouched

        items = new ArrayList<>(selectedItems);

        // from level, spawn inital wave of enemies -- TODO: adjust according to how spawner and level is implemented
        level = selectedLevel;
        enemies = spawnEnemies(level, false);

        turnOrderStrategy = selectedTurnOrderStrategy;
    }

    private List<Combatant> spawnEnemies(Level lvl, boolean isBackup) {
        if (isBackup) return new ArrayList<>(lvl.spawnBackup());
        else return new ArrayList<>(lvl.getInitialEnemies());
            // uses copy so when changing stats (eg HP),
        // the original "options" are left untouched (good for replay game with same settings)
    }

    /*
    * getters used by BattleEngine
    */
    public boolean getIsSmokeBombActive() { return isSmokeBombActive; }
    public boolean getIsSmokeBombExpiringThisTurn() {
        if (!isSmokeBombActive) return false; // not active, can't expire
        for (Item eachItem: items) {
            if (eachItem.getIsUsed() && eachItem.itemType == ITEM_TYPE.SMOKE_BOMB && eachItem.currentDurationLeft == 1) return true;
        }
        return false;
    }
    public void activateSmokeBomb() { isSmokeBombActive = true; }
    public void deactivateSmokeBomb() { isSmokeBombActive = false; }
    public List<Combatant> getPlayers() { return players; }
    public List<Combatant> getEnemies() { return enemies; }
    public List<Combatant> getBackupEnemies() { return backupEnemies; } // only used for printing
    public List<Combatant> getCombatantsInTurnOrder() {
        List<Combatant> combatants = new ArrayList<>(); // new list combines both
        combatants.addAll(players);
        combatants.addAll(enemies);

        return turnOrderStrategy.determineOrder(combatants);
    }
    public List<Item> getItems() { return items; }
    public List<Item> getActiveItems() {
        List<Item> activeItems = new ArrayList<>();
        for (Item eachItem: items) {
            // item has been used but duration is still ongoing
            if (eachItem.getIsUsed() && eachItem.currentDurationLeft > 0) activeItems.add(eachItem);
        }
        return activeItems;
    }
    public boolean isAllPlayersDefeated() {
        return isAllCombatantDefeated(players);
    }
    public boolean isAllEnemiesDefeated() {
        return isAllCombatantDefeated(enemies);
    }
    private boolean isAllCombatantDefeated(List<Combatant> combatants) {
        for (Combatant eachCombatant: combatants) {
            if (eachCombatant.currentHP > 0) return false;
        }
        return true;
    }
    public boolean spawnBackup() {
        if (level.hasBackupToSpawn() && !level.getBackupSpawned()) {
            // ADD to enemies list instead of replace
            // (so end of round printing can still print old enemies as dead AND new enemies as alive)
            backupEnemies = spawnEnemies(level, true);
            for(Combatant eachEnemy: backupEnemies) enemies.add(eachEnemy);
            return true;
        }
        else return false;
    }
    // used to filter out the dead. called AT START OF ROUND so the dead can still be printed at the end of the round they die in
    public void refreshCombatantList() {
        players = getAliveCombatants(players);
        enemies = getAliveCombatants(enemies);
    }
    private List<Combatant> getAliveCombatants(List<Combatant> combatantsToFilter) {
        List<Combatant> aliveCombatants = new ArrayList<>();
        // TODO: adjust according to accessibility in Combatant class
        for (Combatant eachCombatant: combatantsToFilter) {
            if (eachCombatant.currentHP > 0) aliveCombatants.add(eachCombatant);
        }
        return aliveCombatants;
    }
    // used when player wants to restart game with same setting
    public void reset() {
        isSmokeBombActive = false;

        // reset player's stats n status effects
        for(Combatant eachPlayer: players) eachPlayer.resetCondition();

        // despawn enemies, spawn new wave based on lvl
        enemies = spawnEnemies(level, false);
        level.resetLevel();

        // reset items used count -- TODO: depending on how Items is designed, might also need reset item effect duration
        for(Item eachItem: items) eachItem.resetUse();
    }

}
