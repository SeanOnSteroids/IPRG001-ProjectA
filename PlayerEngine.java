import java.util.*;

public class PlayerEngine
{
    private Ring ringArtefact;
    private Spellbook spellbookArtefact;
    
    public PlayerEngine()
    {
        Scanner scn = new Scanner(System.in);
        
        ringArtefact = new Ring("Ring of The Ruined King", 100);
        spellbookArtefact = new Spellbook("Sorcerer's Lost Spellbook", 100.0);
        
        System.out.println("Choose your starting artefact (1 - " + ringArtefact.getRingName() + ", 2 - " +  spellbookArtefact.getSpellbookName() + ", Other - Exit Game):");
        int currentArtefact = scn.nextInt();

        if(currentArtefact <= 2) //if user chose a valid option
            runMainLoop(scn, currentArtefact);
        else
            System.out.println("Exiting the game...");
    }

    private void runMainLoop(Scanner scn, int currentArtefact)
    {
        double demonKingHealth = 100.0;
        
        for(int runtimeCounter = 0; runtimeCounter < 100; runtimeCounter++) //running the loop 100 times/steps including the 0th index and excluding the 100th index
        {
            int cachedRuntimeCounter = runtimeCounter;

            if(demonKingHealth > 0) //if the demon king is alive
            {
                if(runtimeCounter != 0) //if its not the start of the loop
                    currentArtefact = chooseAttackArtefact(scn);

                double damageDealtOrOptionalFlag = 0;

                if(currentArtefact == 1) //if current artefact is a ring
                {
                    if(!ringArtefact.getIsBuffPowerDepleted())
                        damageDealtOrOptionalFlag = ringArtefact.chooseAppliableBuff(scn);
                    else
                    {
                        System.out.println(ringArtefact.getRingName() + " has no buff powers left to use. Skipping this turn.");
                        runtimeCounter = getNextRuntimeCounterIndex(runtimeCounter); //Reinventing "the continue" keyword. Similar to return, but continues into next loop index.
                    }
                }
                else if (currentArtefact == 2) //if current artefact is a spellbook
                {
                    if(spellbookArtefact.getCurrentMana() > 0)
                        damageDealtOrOptionalFlag = spellbookArtefact.chooseAndCastSpell(scn);
                    else
                    {
                        System.out.println(spellbookArtefact.getSpellbookName() + " has no mana left to cast spells. Skipping this turn.");
                        runtimeCounter = getNextRuntimeCounterIndex(runtimeCounter); //Reinventing "the continue" keyword. Similar to return, but continues into next loop index.
                    }
                }
                else //if the user chose to exit the game when choosing the artefact
                    runtimeCounter = getExitGameFlag(); //set the runtimeCounter to max(99), so it will forcibly end the loop

                if(cachedRuntimeCounter == runtimeCounter) //if the local cachedRuntimeCounter is still equal to the current runtimeCounter variable. (Checking if the runtimeCounter hasn't been altered)
                {
                    if(damageDealtOrOptionalFlag == 999999) //if user chose to exit the game when choosing the artefact action
                        runtimeCounter = getExitGameFlag(); //set the runtimeCounter to max(99), so it will forcibly end the loop
                    else
                    {
                        demonKingHealth = dealDamageToDemonKing(demonKingHealth, damageDealtOrOptionalFlag);
                        displayStats(demonKingHealth);
                    }
                }
            }
            else if(demonKingHealth <= 0) //if the demon king is dead
            {
                System.out.println("Victory! You've killed the demon king! You have saved the world!");
                runtimeCounter = getExitGameFlag(); //set the runtimeCounter to max, so it will forcibly end the loop
            }
        }
    }

    private int chooseAttackArtefact(Scanner scn)
    {
        System.out.println("");
        System.out.println("Choose the artefact you are going use this turn (1 - " + ringArtefact.getRingName() + ", 2 - " +  spellbookArtefact.getSpellbookName() + ", Other - Exit Game):");
        return scn.nextInt();
    }

    private double dealDamageToDemonKing(double demonKingHealth, double damageDealt)
    {
        demonKingHealth -= damageDealt;
                        
        if(demonKingHealth < 0)
            demonKingHealth = 0;
            
        System.out.println("You have dealt " + damageDealt + " damage to the demon king.");

        return demonKingHealth;
    }

    private void displayStats(double demonKingHealth)
    {
        System.out.println("Demon king now has health of " + demonKingHealth);
        System.out.println(ringArtefact.toString());
        System.out.println(spellbookArtefact.toString());
    }

    private int getExitGameFlag()
    {
        System.out.println("Exiting the game...");
        return 99;
    }

    private int getNextRuntimeCounterIndex(int currentRuntimeCounter)
    {
        return currentRuntimeCounter++;
    }
}
