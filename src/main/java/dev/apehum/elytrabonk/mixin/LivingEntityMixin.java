package dev.apehum.elytrabonk.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import dev.apehum.elytrabonk.HurtSoundSuppression;
import dev.apehum.elytrabonk.ElytraBonk;
import net.minecraft.client.Minecraft;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
	@WrapOperation(
			method = "handleDamageEvent",
			at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;playSound(Lnet/minecraft/sounds/SoundEvent;FF)V")
	)
	private void elytrabonk$bonkInsteadOfHurt(
			LivingEntity self,
			SoundEvent sound,
			float volume,
			float pitch,
			Operation<Void> original,
			@Local(argsOnly = true) DamageSource source
	) {
		if (!source.is(DamageTypes.FLY_INTO_WALL) || !(self instanceof Player)) {
			original.call(self, sound, volume, pitch);
			return;
		}

		double x = self.getX(), y = self.getY(), z = self.getZ();
		self.level().playLocalSound(x, y, z, ElytraBonk.BONK_SOUND, SoundSource.PLAYERS, volume, 1.0F, false);

		if (self != Minecraft.getInstance().player) {
			HurtSoundSuppression.expectHurtSound(x, y, z, self.level().getGameTime());
		}
	}
}
