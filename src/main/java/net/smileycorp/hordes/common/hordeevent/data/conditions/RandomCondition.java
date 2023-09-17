package net.smileycorp.hordes.common.hordeevent.data.conditions;

import com.google.gson.JsonElement;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.smileycorp.hordes.common.HordesLogger;

public class RandomCondition implements Condition {

	protected float chance;

	public RandomCondition(float chance) {
		this.chance = chance;
	}

	@Override
	public boolean apply(Level level, Player player, RandomSource rand) {
		return rand.nextFloat() <= chance;
	}

	public static RandomCondition deserialize(JsonElement json) {
		try {
			return new RandomCondition(json.getAsFloat());
		} catch(Exception e) {
			HordesLogger.logError("Incorrect parameters for condition hordes:random", e);
		}
		return null;
	}

}
