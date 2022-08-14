package net.smileycorp.hordes.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.world.entity.monster.Zombie;
import net.smileycorp.hordes.common.CommonConfigHandler;

@Mixin(Zombie.class)
public abstract class MixinZombie {

	@Inject(at=@At("HEAD"), method = "isSunSensitive()Z", cancellable = true)
	public void isSunSensitive(CallbackInfoReturnable<Boolean> callback) {
		callback.setReturnValue(CommonConfigHandler.zombiesBurn.get());
		callback.cancel();
	}

}
