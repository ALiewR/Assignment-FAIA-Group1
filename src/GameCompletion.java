//My Imports for this 
import java.util.ArrayList;
import java.util.List;
 
public class GameCompletion {
    GameCompletionUI gameCompletionUI;

    public GameCompletion() {
        gameCompletionUI = new GameCompletionUI(new Printer()); // use default printer
    }
    public GameCompletion(Printer printer) {
        gameCompletionUI = new GameCompletionUI(printer);
    }
    public NEXT_GAME_OPTION_TYPE handleGameResult(boolean isVictory, BattleContext battleContext) {
        List<String> enemiesLeftNames = new ArrayList<>();
        for (Combatant enemy: battleContext.getEnemies()) {
            if (enemy.currentHP > 0) enemiesLeftNames.add(enemy.name);
        }
        gameCompletionUI.displayBattleVerdict(isVictory, battleContext.getPlayers(), enemiesLeftNames, battleContext.getFinalRoundCount(), battleContext.getItems());

        if (isVictory) return NEXT_GAME_OPTION_TYPE.EXIT;

        // let player pick next option
        return gameCompletionUI.selectNextGameOption(); 
    }
}
