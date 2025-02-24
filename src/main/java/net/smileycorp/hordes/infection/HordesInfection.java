package net.smileycorp.hordes.infection;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.smileycorp.hordes.common.Constants;

import java.util.List;
import java.util.stream.Collectors;

public class HordesInfection {

	public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, Constants.MODID);

	public static final TagKey<EntityType<?>> INFECTION_ENTITIES_TAG = TagKey.create(Registries.ENTITY_TYPE, Constants.loc("infection_entities"));
	public static final TagKey<Item> INFECTION_CURES_TAG = TagKey.create(Registries.ITEM, Constants.loc("infection_cures"));

	public static final RegistryObject<MobEffect> INFECTED = EFFECTS.register("infected", InfectedEffect::new);
	public static final RegistryObject<MobEffect> IMMUNITY = EFFECTS.register("immunity", ImmuneEffect::new);

	public static final ResourceKey<DamageType> INFECTION_DAMAGE = ResourceKey.create(Registries.DAMAGE_TYPE, Constants.loc("infection"));

	public static DamageSource getInfectionDamage(LivingEntity entity) {
		return entity.damageSources().source(INFECTION_DAMAGE);
	}

    public static List<ItemStack> getCureList() {
        return ForgeRegistries.ITEMS.tags().getTag(INFECTION_CURES_TAG)
                .stream().map(ItemStack::new).collect(Collectors.toList());
    }

	public static boolean isCure(ItemStack stack) {
		return stack.is(INFECTION_CURES_TAG);
	}

	public static boolean canCauseInfection(LivingEntity entity) {
		if (entity == null) return false;
		return entity.getType().is(INFECTION_ENTITIES_TAG);
	}

}
