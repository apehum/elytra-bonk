package dev.apehum.elytrabonk;

import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvent;

public final class ElytraBonk {
	public static final Identifier BONK_ID = Identifier.fromNamespaceAndPath("elytrabonk", "bonk");
	public static final SoundEvent BONK_SOUND = SoundEvent.createVariableRangeEvent(BONK_ID);

	private ElytraBonk() {
	}
}
