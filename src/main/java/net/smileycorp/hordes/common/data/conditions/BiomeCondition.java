package net.smileycorp.hordes.common.data.conditions;

import com.google.common.collect.Lists;
import com.google.gson.JsonElement;
import com.mojang.datafixers.util.Either;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.smileycorp.hordes.common.HordesLogger;

import java.util.List;

public class BiomeCondition implements Condition {
	
	protected final List<Either<TagKey<Biome>, ResourceLocation>> biomes;
	
	public BiomeCondition(List<Either<TagKey<Biome>, ResourceLocation>> biomes) {
		this.biomes = biomes;
	}

	@Override
	public boolean apply(Level level, LivingEntity entity, ServerPlayer player, RandomSource rand) {
		Holder<Biome> biome = level.getBiomeManager().getBiome(player.blockPosition());
		for (Either<TagKey<Biome>, ResourceLocation> either : biomes) if (either.map(biome::is, biome::is)) return true;
		return false;
	}

	public static BiomeCondition deserialize(JsonElement json) {
		try {
			if (json.isJsonArray()) {
				List<Either<TagKey<Biome>, ResourceLocation>> biomes = Lists.newArrayList();
				for (JsonElement element : json.getAsJsonArray()) biomes.add(either(element.getAsString()));
				return new BiomeCondition(biomes);
			}
			return new BiomeCondition(Lists.newArrayList(either(json.getAsString())));
		} catch(Exception e) {
			HordesLogger.logError("Incorrect parameters for condition hordes:biome", e);
		}
		return null;
	}
	
	private static Either<TagKey<Biome>, ResourceLocation> either(String string) {
		return string.contains("#") ? Either.left(TagKey.create(Registries.BIOME, new ResourceLocation(string.replace("#", ""))))
				: Either.right(new ResourceLocation(string));
	}
	
}
