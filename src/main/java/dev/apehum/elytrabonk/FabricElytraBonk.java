//? if fabric {
package dev.apehum.elytrabonk;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;

public class FabricElytraBonk implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		Registry.register(BuiltInRegistries.SOUND_EVENT, ElytraBonk.BONK_ID, ElytraBonk.BONK_SOUND);
	}
}
//?}
