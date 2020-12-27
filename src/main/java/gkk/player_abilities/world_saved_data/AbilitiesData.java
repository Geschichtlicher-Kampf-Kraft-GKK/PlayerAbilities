package gkk.player_abilities.world_saved_data;

import gkk.player_abilities.AbilityContainer;
import gkk.player_abilities.Player_abilities;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraft.world.storage.WorldSavedData;
import net.minecraftforge.common.util.Constants;

import java.util.HashMap;
import java.util.Map;
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

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        if (nbt.hasKey("list")) {
            NBTTagList list = nbt.getTagList("list", Constants.NBT.TAG_LIST);
            for (NBTBase nbtBase : list) {
                if (nbtBase instanceof NBTTagCompound) {
                    NBTTagCompound nbtTagCompound = (NBTTagCompound) nbtBase;
                    UUID player = UUID.fromString(nbtTagCompound.getString("player"));
                    NBTTagList abilities = nbtTagCompound.getTagList("abilities", Constants.NBT.TAG_LIST);
                    HashMap<String, AbilityContainer> abilityContainerHashMap = new HashMap<>();

                    for (NBTBase ability : abilities) {
                        if (ability instanceof NBTTagCompound) {
                            NBTTagCompound tagCompound = (NBTTagCompound) ability;
                            String abilityName = tagCompound.getString("ability");
                            //todo algorithm
//                            abilityContainerHashMap.put(abilityName, AbilityContainer.deserializeNBT(tagCompound.getCompoundTag("data"), ));
                        }
                    }
                    map.put(player, abilityContainerHashMap);
                }
            }
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        NBTTagList list = new NBTTagList();

        for (Map.Entry<UUID, HashMap<String, AbilityContainer>> uuidHashMapEntry : map.entrySet()) {
            HashMap<String, AbilityContainer> abilityContainerHashMap = uuidHashMapEntry.getValue();

            NBTTagCompound nbtTagCompound = new NBTTagCompound();
            nbtTagCompound.setString("player", uuidHashMapEntry.getKey().toString());

            NBTTagList abilityList = new NBTTagList();
            for (Map.Entry<String, AbilityContainer> stringAbilityContainerEntry : abilityContainerHashMap.entrySet()) {
                NBTTagCompound abilityTag = new NBTTagCompound();
                abilityTag.setString("ability", stringAbilityContainerEntry.getKey());
                abilityTag.setTag("data", stringAbilityContainerEntry.getValue().serializeNBT());
                abilityList.appendTag(abilityTag);
            }

            nbtTagCompound.setTag("abilities", abilityList);

            list.appendTag(nbtTagCompound);
        }
        compound.setTag("list", compound);
        return compound;
    }

}
