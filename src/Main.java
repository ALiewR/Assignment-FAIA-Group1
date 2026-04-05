import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        //Loading Screen 
        Printer printer=new Printer();
        LoadingScreen loadingScreen= new LoadingScreen(printer);
        loadingScreen.showWelcome();
        Player player=loadingScreen.choosePlayer();
        List<Combatant> players= new ArrayList<>();
        players.add(player);

        List<Item> items=loadingScreen.chooseItems();
        Level level=loadingScreen.chooseLevel();
        TurnOrderStrategy turnOrderStrategy = new TurnOrderStrategy();

        // TEMP
        BattleContext battleContext = new BattleContext(
            players,
            items,
            level,
            turnOrderStrategy
        );
        BattleEngine battleEngine = new BattleEngine(battleContext, printer);

        GameCompletion gameCompletion = new GameCompletion(printer);

        // player won, exit game
        boolean isGameWon = battleEngine.executeBattle();
        while (!isGameWon) {
            NEXT_GAME_OPTION_TYPE nextGameOption = gameCompletion.handleGameResult(false, battleContext);
            switch(nextGameOption) {
                case EXIT: return;
                case REPLAY: {
                    isGameWon = battleEngine.executeBattle();
                    break;
                }
                case START_NEW: {
                    // TODO: go back to LoadingScreen
                    break;
                }
            }
        }
        if (isGameWon) gameCompletion.handleGameResult(true, battleContext);
    }
}
