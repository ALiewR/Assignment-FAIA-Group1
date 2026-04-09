//My Imports for this 
import java.util.ArrayList;
import java.util.List;
 
public class GameCompletion {
    GameCompletionUI gameCompletionUI;

    public GameCompletion() {
        gameCompletionUI = new GameCompletionUI(new ConsolePrinter()); 
    }

    public GameCompletion(Printer printer) {
        gameCompletionUI = new GameCompletionUI(printer);
    }

    public void handleVictory(BattleContext battleContext){
        List<String> enemiesLeftNames = new ArrayList<>();

        for (Combatant enemy: battleContext.getEnemies()){
            if (enemy.isAlive()){
                enemiesLeftNames.add(enemy.getName());
            }
        }

        gameCompletionUI.displayBattleVerdict(
            true,
            battleContext.getPlayers(),
            enemiesLeftNames,
            battleContext.getFinalRoundCount(),
            battleContext.getItems()
        );
    }

    public NEXT_GAME_OPTION_TYPE handleDefeat(BattleContext battleContext) {
        List<String> enemiesLeftNames = new ArrayList<>();

        for (Combatant enemy: battleContext.getEnemies()){
            if (enemy.isAlive()){
                enemiesLeftNames.add(enemy.getName());
            }
        }

        gameCompletionUI.displayBattleVerdict(
            false,
            battleContext.getPlayers(),
            enemiesLeftNames,
            battleContext.getFinalRoundCount(),
            battleContext.getItems()
        );

        return gameCompletionUI.selectNextGameOption();
    }
}