package dev.apehum.elytrabonk;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvent;

public class ElytraBonkClient implements ClientModInitializer {
	public static final Identifier BONK_ID = Identifier.fromNamespaceAndPath("elytrabonk", "bonk");
	public static final SoundEvent BONK_SOUND = SoundEvent.createVariableRangeEvent(BONK_ID);

	@Override
	public void onInitializeClient() {
		Registry.register(BuiltInRegistries.SOUND_EVENT, BONK_ID, BONK_SOUND);
	}
}
