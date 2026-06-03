package dev.apehum.elytrabonk.mixin;

import dev.apehum.elytrabonk.HurtSoundSuppression;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.protocol.game.ClientboundSoundPacket;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPacketListener.class)
public abstract class ClientPacketListenerMixin {
	@Inject(at = @At("HEAD"), method = "handleSoundEvent", cancellable = true)
	private void elytrabonk$muteReplacedHurtSound(ClientboundSoundPacket packet, CallbackInfo ci) {
		if (packet.getSource() != SoundSource.PLAYERS) {
			return;
		}

		SoundEvent sound = packet.getSound().value();
		if (sound != SoundEvents.PLAYER_HURT) {
			return;
		}

		Level level = Minecraft.getInstance().level;
		if (level == null) {
			return;
		}

		if (HurtSoundSuppression.shouldSuppress(packet.getX(), packet.getY(), packet.getZ(), level.getGameTime())) {
			ci.cancel();
		}
	}
}
