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
            List<Combatant> enemies=battleContext.getEnemies();
            List<Combatant>allCombatants=new ArrayList<>();
            allCombatants.addAll(players);
            allCombatants.addAll(enemies);

            List<Combatant> turnOrder= turnOrderStrategy.determineOrder(allCombatants);

            loadingScreen.showBattleSetup(player,items,level,enemies,turnOrder);
            loadingScreen.showEnteringBattleMessage();


            GameCompletion gameCompletion= new GameCompletion(printer);
            BattleEngine battleEngine=new BattleEngine (battleContext,printer);
        

            boolean stayInSameSetting=true;

            while(stayInSameSetting){
                boolean isGameWon=battleEngine.executeBattle();

                if (isGameWon){
                    gameCompletion.handleVictory(battleContext);
                    return;//end after winning
                } else{
                    NEXT_GAME_OPTION_TYPE nextGameOption=gameCompletion.handleDefeat(battleContext);

                    switch(nextGameOption){
                        case EXIT:
                            return;
                        case REPLAY:
                            break; //replay with the same settings
                        case START_NEW:
                            stayInSameSetting=false;
                            break;
                    }
                }
            }
        }
    }
}
              
                
            
                  
            
    
        