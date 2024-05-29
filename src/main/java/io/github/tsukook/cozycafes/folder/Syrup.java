package io.github.tsukook.cozycafes.folder;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.effect.MobEffectInstance;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
public class Syrup {
    private final ArrayList<MobEffectInstance> mobEffectInstances = new ArrayList<>();

    public Syrup(ArrayList<MobEffectInstance> mobEffectInstances) {
        this.mobEffectInstances.addAll(mobEffectInstances);
    }

    public Syrup(MobEffectInstance mobEffectInstance) {
        this((ArrayList<MobEffectInstance>) Collections.singletonList(mobEffectInstance));
    }

    public Syrup() {}

    public static Syrup readFromNBT(CompoundTag compoundTag) {
        if (compoundTag != null && !compoundTag.isEmpty() && compoundTag.contains("PotionEffects")) {
            ArrayList<MobEffectInstance> mobEffectInstanceList = new ArrayList<>();

            ListTag listTag = compoundTag.getList("PotionEffects", ListTag.TAG_COMPOUND);
            listTag.forEach(tag -> {
                MobEffectInstance mobEffectInstance = MobEffectInstance.load((CompoundTag) tag);
                mobEffectInstanceList.add(mobEffectInstance);
            });

            return new Syrup(mobEffectInstanceList);
        }

        return new Syrup();
    }

    public CompoundTag writeToNBT(CompoundTag compoundTag) {
        ListTag listTag = new ListTag();
        for (MobEffectInstance effect : this.mobEffectInstances) {
            listTag.add(effect.save(new CompoundTag()));
        }

        compoundTag.put("PotionEffects", listTag);

        return compoundTag;
    }

    public void addEffect(MobEffectInstance mobEffectInstance) {
        this.mobEffectInstances.add(mobEffectInstance);
    }

    public void addEffects(Collection<MobEffectInstance> mobEffectInstanceCollection) {
        this.mobEffectInstances.addAll(mobEffectInstanceCollection);
    }

    public ArrayList<MobEffectInstance> getEffects() {
        return this.mobEffectInstances;
    }

    public boolean hasEffects() {
        return !this.mobEffectInstances.isEmpty();
    }
}
