package net.smileycorp.hordes.common.infection;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.smileycorp.hordes.common.Constants;
import net.smileycorp.hordes.common.entities.DrownedPlayer;
import net.smileycorp.hordes.common.entities.ZombiePlayer;

public class HordesInfection {

	public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, Constants.MODID);
	public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, Constants.MODID);

	public static RegistryObject<MobEffect> INFECTED = EFFECTS.register("infected", () -> new InfectedEffect());

	public static RegistryObject<EntityType<ZombiePlayer>> ZOMBIE_PLAYER = ENTITIES.register("zombie_player", () ->
	EntityType.Builder.<ZombiePlayer>of(ZombiePlayer::new, MobCategory.MONSTER).sized(0.6F, 1.95F).clientTrackingRange(8).build("zombie_player"));
	public static RegistryObject<EntityType<DrownedPlayer>> DROWNED_PLAYER = ENTITIES.register("drowned_player", () ->
	EntityType.Builder.<DrownedPlayer>of(DrownedPlayer::new, MobCategory.MONSTER).sized(0.6F, 1.95F).clientTrackingRange(8).build("drowned_player"));

	public static DamageSource INFECTION_DAMAGE = new DamageSourceInfection();

}
