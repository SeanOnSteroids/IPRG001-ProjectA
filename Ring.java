import java.util.*;

public class Ring
{
    private final int BUFF_POWER_DEDUCTION_RATE = 12;
    private final double BASE_BUFF_DAMAGE_MULTIPLIER = 5.36;
    private final double BASE_CRITICAL_DAMAGE_RATE = 0.75;
    private final double ADDITONAL_CRITICAL_DAMAGE_RATE = 12.65;

    private String ringName;
    private int currentBuffPowerPoints;
    private boolean isBuffPowerDepleted;

    public Ring(String rName, int buffPowerPoints)
    {
        ringName = rName;
        currentBuffPowerPoints = buffPowerPoints;
        isBuffPowerDepleted = false;
    }

    public int getBuffPowerDeductionRate() { return BUFF_POWER_DEDUCTION_RATE; }
    public double getBaseBuffDamageMultiplier() { return BASE_BUFF_DAMAGE_MULTIPLIER; } 
    public double getBaseCriticalDamageRate() { return BASE_CRITICAL_DAMAGE_RATE; }
    public double getAdditionalCriticalDamageRate() { return ADDITONAL_CRITICAL_DAMAGE_RATE; }

    public String getRingName() { return ringName; }
    public void setRingName(String newRingName) { ringName = newRingName; }

    public int getCurrentBuffPowerPoints() { return currentBuffPowerPoints; }
    public void setCurrentBuffPowerPoints(int newBuffPowerPoints) { currentBuffPowerPoints = newBuffPowerPoints; }
 
    public boolean getIsBuffPowerDepleted() { return isBuffPowerDepleted; }
    public void setIsBuffPowerDepleted(boolean newState) { isBuffPowerDepleted = newState; }

    public double chooseAppliableBuff(Scanner scn)
    {
        System.out.println("Choose the type of buff to apply to the attack (1 - Blood Drain, 2 - Enchanted Critical Hit, Other - Exit Game):");
        int buffType = scn.nextInt();
        
        if(buffType == 1)
            return applyBloodDrainBuff();
        else if (buffType == 2)
            return applyEnchantedCrticalHitBuff();
        else 
            return 999999.0;
    }

    private double applyBloodDrainBuff()
    {
        deductBuffPowerPointsAndSetState();
        
        int maxRandomDamageApplierLimit = 6;
        int minRandomDamageApplierLimit = 1;

        int randomDamageApplier = new Random().nextInt(maxRandomDamageApplierLimit - minRandomDamageApplierLimit + 1) + minRandomDamageApplierLimit;
        double finalDamageApplied = randomDamageApplier * BASE_BUFF_DAMAGE_MULTIPLIER;
        
        return finalDamageApplied;
    }

    private double applyEnchantedCrticalHitBuff()
    {
        deductBuffPowerPointsAndSetState();
        
        int maxRandomDamageApplierLimit = 9;
        int minRandomDamageApplierLimit = 3;

        int randomDamageApplier = new Random().nextInt(maxRandomDamageApplierLimit - minRandomDamageApplierLimit + 1) + minRandomDamageApplierLimit;
        double baseDamage = randomDamageApplier * BASE_BUFF_DAMAGE_MULTIPLIER;

        double criticalHitChance = new Random().nextDouble(); 
        double finalDamageCritcalHitChanceInclusive = baseDamage + (criticalHitChance * (BASE_CRITICAL_DAMAGE_RATE + ADDITONAL_CRITICAL_DAMAGE_RATE));

        return finalDamageCritcalHitChanceInclusive; 
    }

    private void deductBuffPowerPointsAndSetState()
    {
        currentBuffPowerPoints -= BUFF_POWER_DEDUCTION_RATE;
        isBuffPowerDepleted = (currentBuffPowerPoints <= 0);
    }

    public String toString()
    {
        return ringName + " - Buff Powers: " + currentBuffPowerPoints + " | Is buff power depleted: " + isBuffPowerDepleted + " | Buff power deduction: " + BUFF_POWER_DEDUCTION_RATE +
        " | Base buff damage multiplier: " + BASE_BUFF_DAMAGE_MULTIPLIER + " | Base critical damage: " + BASE_CRITICAL_DAMAGE_RATE + " | Additional critical damage: " + ADDITONAL_CRITICAL_DAMAGE_RATE;
    }
}
