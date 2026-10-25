package dev.apehum.elytrabonk;

import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvent;

public final class ElytraBonk {
	//? if >=1.21 {
	public static final Identifier BONK_ID = Identifier.fromNamespaceAndPath("elytrabonk", "bonk");
	//?} else {
	/*public static final Identifier BONK_ID = new Identifier("elytrabonk", "bonk");
	*///?}
	public static final SoundEvent BONK_SOUND = SoundEvent.createVariableRangeEvent(BONK_ID);

	private ElytraBonk() {
	}
}
