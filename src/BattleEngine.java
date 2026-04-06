import java.util.ArrayList;
import java.util.List;

public class BattleEngine {
    private BattleContext currentBattleContext;
    private BattleEngineUI battleEngineUI;
    private int roundCount = 0; // gets added to at the START of the round --> avoid ending round 1 with 2 as roundCount

    public BattleEngine(BattleContext battleContext) {
        currentBattleContext = battleContext;
        battleEngineUI = new BattleEngineUI(new Printer()); // use default printer
    }
    public BattleEngine(BattleContext battleContext, Printer printer) {
        currentBattleContext = battleContext;
        battleEngineUI = new BattleEngineUI(printer);
    }

    // called by main
        // returns isVictory
    public boolean executeBattle() {
        resetBattle(); // called from the start to make sure starting with clean slate
            // useful for "replay with same settings" since that goes straight to BattleEngine

        while (!checkBattleComplete()) {
            executeRound();
        }
        currentBattleContext.setFinalRoundCount(roundCount);

        // battle complete. now figure out whose victory
        return !currentBattleContext.isAllPlayersDefeated();
    }

    /*
     * used in executeBattle to facilitate battle
     */
    private void resetBattle() {
        roundCount = 0;
        currentBattleContext.reset();
    }
    private void trySpawnBackup() {
        // if no enemies left, try and spawn backup
        boolean hasSuccessfullySpawnedBackup = false;
        if (currentBattleContext.isAllEnemiesDefeated()) {
            hasSuccessfullySpawnedBackup = currentBattleContext.spawnBackup();
        }
        // print that new enemies spawned if did spawn
        if (hasSuccessfullySpawnedBackup)
            battleEngineUI.displayBackupSpawnedInfo(currentBattleContext.getBackupEnemies());
    }
    private boolean checkBattleComplete() {
        return currentBattleContext.isAllPlayersDefeated() || currentBattleContext.isAllEnemiesDefeated();
    }
    private void executeRound() {
        roundCount++;
        battleEngineUI.displayRoundHeader(roundCount);

        // filter the dead out of the combatant lists
        currentBattleContext.refreshCombatantList();

        // every combatant gets a turn based on turn order strategy
        for (Combatant eachCombatant: currentBattleContext.getCombatantsInTurnOrder()) {
            executeTurn(eachCombatant);
            // once turn is up, deplete duration of status effect
            // TODO: adjust according to how to update duration and check if expired
            for (String eachStatusEffectName: eachCombatant.updateStatusEffectDuration()) {
                battleEngineUI.printStatusEffectExpires(eachStatusEffectName);
            }
            battleEngineUI.displayLineMessage(""); // to move on to next line in printing
            // and reduce skill cooldown TODO: adjust according to how cooldown works
            eachCombatant.depleteCooldown();
        }
        // once everyone has had a turn, deplete item duration
        for (Item eachItem: currentBattleContext.getActiveItems()) {
            eachItem.depleteDuration();
            if (eachItem.itemType == ITEM_TYPE.SMOKE_BOMB && eachItem.currentDurationLeft <= 0
                    && currentBattleContext.getIsSmokeBombActive()) currentBattleContext.deactivateSmokeBomb();
        }

        // see if can spawn back up if needed (placed here for order of printing)
        trySpawnBackup();

        // display end of turn info
        battleEngineUI.displayEndOfRoundInfo(currentBattleContext, roundCount,
                currentBattleContext.getPlayers().get(0).currentSkillMaxCooldown); // only takes first player's cooldown

    }
    private void executeTurn(Combatant actor) {
        // if eliminated, turn skipped (different message printed)
        // TODO: edit health check depending on how Combatant class is structured
        if (actor.currentHP <= 0) {
            battleEngineUI.displayTurnEliminated(actor.name);
            return;
        }
        // if stunned, turn skipped
        for (StatusEffect eachStatusEffect: actor.afflictedStatusEffects) {
            if (eachStatusEffect.statusEffectType == STATUS_EFFECT_TYPE.STUNNED) {
                battleEngineUI.displayTurnStunned(actor.name);
                return;
            }
        }

        // else, take turn
            // TODO: only used if Combatant has tag to say whether it is player or enemy
        switch(actor.combatantType) {
            case PLAYER: {
                executePlayerTurn(actor);
                break;
            }
            case ENEMY: {
                executeEnemyTurn(actor);
                break;
            }
        }
    }
    private void executePlayerTurn(Combatant player) {
        // user selects action to take
        List<Action> availableActions = player.availableActions; // TODO: shift to use getAvailableActions while passing in items
        Action actionToTake = battleEngineUI.selectAction(player.name, availableActions);
        // TODO: check if the action is valid (eg no item but wanna use item) -- should be done within player to return only list of available actions
        //  -- ADJUST ACCORDING TO HOW OTHER PARTS ARE DONE

        // selects targets
        List<Combatant> targets = new ArrayList<>();
        // TODO: adjust based on target type accessibility in Action class
        switch(actionToTake.targetType) {
            case ENEMIES: {
                List<Combatant> possibleTargets = new ArrayList<>(currentBattleContext.getEnemies()); // copy so you won't edit original list
                // if num of targets >= num of possible targets, auto use on possible targets (no need select)
                if (actionToTake.getNumOfTargets() >= possibleTargets.size()) targets = possibleTargets;
                else {
                    // user selects targets
                    // TODO: adjust how to get num of targets based on how other parts are done
                    for (int i = 0; i < actionToTake.getNumOfTargets() && !possibleTargets.isEmpty(); i++) {
                        Combatant target = battleEngineUI.selectTarget(actionToTake.getName(), possibleTargets);
                        targets.add(target);
                        possibleTargets.remove(target);
                    }
                }
                break;
            }
            case SELF: {
                targets.add(player);
                break;
            }
        }

        // for each target, execute action
        actionToTake.execute(player, targets, currentBattleContext);

        // print turn outcome (different based on if smoke bomb is active)
        battleEngineUI.displayTurnOutcome(player, actionToTake, targets, actionToTake.doesInflictStatusEffect(), false, false); //smoke bomb doesn't matter here since it only shields players

        // if action taken is special skill, print cooldown update
        // TODO: adjust based on accessibility from other classes
        if (actionToTake.actionType == ACTION_TYPE.SPECIAL_SKILL) battleEngineUI.printSpecialSkillCooldown(player.specialSkillMaxCooldown);
    }
    private void executeEnemyTurn(Combatant enemy) {
        // TODO: change if using EnemyStrategy class
        if (enemy.availableActions.isEmpty()) return;
        Action actionToTake = enemy.availableActions.get(0);

        // auto pick targets based on first in player list
        List<Combatant> targets = new ArrayList<>();
        for (int i = 0; i < actionToTake.getNumOfTargets()
                && i < currentBattleContext.getPlayers().size(); i++) {
            targets.add(currentBattleContext.getPlayers().get(i));
        }

        // execute action (but not if is an attack with smoke bomb active)
        if (!currentBattleContext.getIsSmokeBombActive() &&
                (actionToTake.actionType == ACTION_TYPE.ATTACK || actionToTake.actionType == ACTION_TYPE.SPECIAL_SKILL))
            actionToTake.execute(enemy, targets, currentBattleContext);
        // print turn outcome
        battleEngineUI.displayTurnOutcome(enemy, actionToTake, targets, actionToTake.doesInflictStatusEffect(),
                currentBattleContext.getIsSmokeBombActive(), currentBattleContext.getIsSmokeBombExpiringThisTurn());
    }
}
