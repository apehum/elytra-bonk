package dev.apehum.elytrabonk;

import net.minecraft.world.phys.Vec3;

import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;

public final class HurtSoundSuppression {
	private record Pending(Vec3 position, long tick) {}

	private static final List<Pending> PENDING = new ArrayList<>();
	private static final double MAX_DISTANCE_SQ = 16.0;
	private static final long MAX_TICK_AGE = 1;

	private HurtSoundSuppression() {
	}

	public static void expectHurtSound(double x, double y, double z, long tick) {
		prune(tick);
		PENDING.add(new Pending(new Vec3(x, y, z), tick));
	}

	public static boolean shouldSuppress(double x, double y, double z, long tick) {
		prune(tick);

		Vec3 suppressAt = new Vec3(x, y, z);
		Iterator<Pending> it = PENDING.iterator();
		while (it.hasNext()) {
			Pending pending = it.next();
			if (suppressAt.distanceToSqr(pending.position) <= MAX_DISTANCE_SQ) {
				it.remove();
				return true;
			}
		}

		return false;
	}

	private static void prune(long now) {
		PENDING.removeIf(pending -> {
			long age = now - pending.tick;
			return age < 0 || age > MAX_TICK_AGE;
		});
	}
}
