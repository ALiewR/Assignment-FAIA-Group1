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
                initialEnemies.add(new Enemy());
                break;
            }
            case 2: { // med
                initialEnemies.add(new Enemy());
                initialEnemies.add(new Enemy());
                backupEnemies.add(new Enemy());
                break;
            }
            case 3: { // dif
                initialEnemies.add(new Enemy());
                initialEnemies.add(new Enemy());
                initialEnemies.add(new Enemy());
                backupEnemies.add(new Enemy());
                backupEnemies.add(new Enemy());
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
