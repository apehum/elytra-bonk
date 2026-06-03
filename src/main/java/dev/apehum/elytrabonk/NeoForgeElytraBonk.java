//? if neoforge {
/*package dev.apehum.elytrabonk;

import net.minecraft.core.registries.Registries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.registries.RegisterEvent;

@Mod("elytrabonk")
public class NeoForgeElytraBonk {
	public NeoForgeElytraBonk(IEventBus modBus) {
		modBus.addListener(this::onRegister);
	}

	private void onRegister(RegisterEvent event) {
		event.register(
	        Registries.SOUND_EVENT,
	        registry -> registry.register(ElytraBonk.BONK_ID, ElytraBonk.BONK_SOUND)
        );
	}
}
*///?}
