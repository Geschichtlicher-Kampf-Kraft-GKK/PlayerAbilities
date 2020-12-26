package gkk.player_abilities;

import net.minecraft.nbt.NBTTagCompound;

import java.util.function.Function;

public class AbilityContainer {

    private final Function<Integer, Integer> upgradeFunc;
    private int current;
    private int max;
    private int min;
    private int currentExp;

    public AbilityContainer(int current, int max, int min, int currentExp, Function<Integer, Integer> upgradeFunc) {
        this.current = current;
        this.max = max;
        this.min = min;
        this.currentExp = currentExp;
        this.upgradeFunc = upgradeFunc;
    }

    public static AbilityContainer deserializeNBT(NBTTagCompound nbt, Function<Integer, Integer> upgradeFunc) {
        return new AbilityContainer(nbt.getInteger("current"), nbt.getInteger("max"), nbt.getInteger("min"), nbt.getInteger("currentExp"), upgradeFunc);
    }

    public boolean hasMaxValue() {
        return max != -1;
    }

    public boolean hasMinValue() {
        return min != -1;
    }

    public boolean atMaxValue() {
        return current >= max;
    }

    public boolean atMinValue() {
        return current <= min;
    }

    public int getCurrentExp() {
        return currentExp;
    }

    public void changeExp(int add) {
        if (!atMaxValue()) {
            currentExp += add;
            current = upgradeFunc.apply(currentExp);
        }
    }

    public void setExp(int exp) {
        currentExp = exp;
        int level = upgradeFunc.apply(exp);
        if (level > max) {
            level = max;
        } else if (level < min) {
            level = min;
        } else {
            current = level;
        }
    }

    public NBTTagCompound serializeNBT() {
        NBTTagCompound nbtTagCompound = new NBTTagCompound();
        nbtTagCompound.setInteger("current", current);
        nbtTagCompound.setInteger("max", max);
        nbtTagCompound.setInteger("min", min);
        nbtTagCompound.setInteger("currentExp", currentExp);
        return null;
    }
}
