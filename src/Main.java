import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
    
        Printer printer=new ConsolePrinter();
        while (true) {
            //loading screen
            LoadingScreen loadingScreen=new LoadingScreen(printer);
            loadingScreen.showWelcome();
            Player player=loadingScreen.choosePlayer();
            List<Combatant> players=new ArrayList<>();
            players.add(player);

            List<Item> items=loadingScreen.chooseItems();
            Level level=loadingScreen.chooseLevel();

            TurnOrderStrategy turnOrderStrategy=new SpeedBasedTurnOrderStrategy();

            
            BattleContext battleContext=new BattleContext(
                players,
                items,
                level,
                turnOrderStrategy
            );
            GameCompletion gameCompletion= new GameCompletion(printer);
            BattleEngine battleEngine=new BattleEngine (battleContext,printer);
        

            boolean stayInSameSetting=true;

            while(stayInSameSetting){
                boolean isGameWon=battleEngine.executeBattle();
                NEXT_GAME_OPTION_TYPE nextGameOption=gameCompletion.handleGameResult(isGameWon,battleContext);
                switch(nextGameOption){
                    case EXIT:
                        return;
                    case REPLAY:
                        break;//same settings
                    case START_NEW://leave inner loop, outer loop restarts loading screen
                        stayInSameSetting=false;
                        break;

                }
            }
        }
    }
}
            
                  
            
    
        