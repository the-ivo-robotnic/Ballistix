package ballistix.common.blast;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion.Mode;
import net.minecraft.world.World;

public class BlastObsidian extends Blast {

	public BlastObsidian(World world, BlockPos position) {
		super(world, position);
	}

	@Override
	public void doPreExplode() {
	}

	@Override
	public void doExplode(int callCount) {
		if (!world.isRemote) {
			world.createExplosion(null, position.getX() + 0.5, position.getY() + 0.5, position.getZ() + 0.5, 10f, Mode.BREAK);
		}
	}

	@Override
	public void doPostExplode() {
	}

}
