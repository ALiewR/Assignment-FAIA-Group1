import java.util.ArrayList;
import java.util.List;

public class Level {
    private int levelNumber;
    private List<Combatant> initialEnemies = new ArrayList<>();
    private List<Combatant> backupEnemies = new ArrayList<>();
    private boolean backupSpawned = false;

    public Level(int levelNum) {
        levelNumber = levelNum;
        switch(levelNumber) {
            case 1: { // easy
                initialEnemies.add(new Goblin("A"));
                break;
            }
            case 2: { // med
                initialEnemies.add(new Goblin("A"));
                initialEnemies.add(new Goblin("B"));
                backupEnemies.add(new Goblin("C"));
                break;
            }
            case 3: { // dif
                initialEnemies.add(new Wolf("A"));
                initialEnemies.add(new Wolf("B"));
                initialEnemies.add(new Goblin("A"));
                backupEnemies.add(new Goblin("B"));
                backupEnemies.add(new Wolf("C"));
                break;
            }
            default: {} // no enemies spawned
        }
    }

    public List<Combatant> getInitialEnemies() { return initialEnemies; }
    public boolean hasBackupToSpawn() {
        return !backupEnemies.isEmpty();
    }
    public List<Combatant> spawnBackup() {
        backupSpawned = true;
        return backupEnemies;
    }
    public boolean getBackupSpawned() { return backupSpawned; }
    public void resetLevel() { backupSpawned = false; }
}
