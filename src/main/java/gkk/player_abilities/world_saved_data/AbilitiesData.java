package gkk.player_abilities.world_saved_data;

import gkk.player_abilities.AbilityContainer;
import gkk.player_abilities.Player_abilities;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.storage.WorldSavedData;

import java.util.HashMap;
import java.util.UUID;

public class AbilitiesData extends WorldSavedData {
    public static final String DATA_NAME = Player_abilities.MOD_ID + "_player_data";
    private HashMap<UUID, HashMap<String, AbilityContainer>> map = new HashMap<>();

    public AbilitiesData() {
        super(DATA_NAME);
    }

    public static AbilitiesData getData(World world) {
        WorldSavedData data = world.getMapStorage().getOrLoadData(AbilitiesData.class, DATA_NAME);
        if (data == null) {
            data = new AbilitiesData();
            world.getMapStorage().setData(DATA_NAME, data);
        }
        return (AbilitiesData) data;
    }

    public boolean hasAbility(UUID playerID, String name) {
        return map.containsKey(playerID) && map.get(playerID).containsKey(name);
    }

    //todo unFinished

    @Override
    public void readFromNBT(NBTTagCompound nbt) {

    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        return null;
    }

}
