package ballistix.common.item;

import ballistix.DeferredRegisters;
import ballistix.References;
import ballistix.common.block.BlockExplosive;
import ballistix.common.entity.EntityMissile;
import electrodynamics.common.blockitem.BlockItemDescriptable;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

public class ItemRocketLauncher extends Item {

    public ItemRocketLauncher() {
	super(new Item.Properties().tab(References.BALLISTIXTAB).stacksTo(1));
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
	return UseAnim.NONE;
    }

    @Override
    public int getUseDuration(ItemStack stack) {
	return Integer.MAX_VALUE;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
	ItemStack itemstack = playerIn.getItemInHand(handIn);
	playerIn.startUsingItem(handIn);
	return new InteractionResultHolder<>(InteractionResult.SUCCESS, itemstack);
    }

    @Override
    public void releaseUsing(ItemStack stack, Level world, LivingEntity entityLiving, int timeLeft) {
	if (!world.isClientSide && entityLiving instanceof Player) {
	    Player pl = (Player) entityLiving;
	    int blastOrdinal = 0;
	    boolean hasExplosive = false;
	    boolean hasRange = false;
	    ItemStack ex = ItemStack.EMPTY;
	    ItemStack missile = ex;
	    for (ItemStack st : pl.inventory.items) {
		Item it = st.getItem();
		if (!hasExplosive && it instanceof BlockItemDescriptable) {
		    Block bl = ((BlockItemDescriptable) it).getBlock();
		    if (bl instanceof BlockExplosive) {
			blastOrdinal = ((BlockExplosive) bl).explosive.ordinal();
			hasExplosive = true;
			ex = st;
		    }
		}
		if (!hasRange && it == DeferredRegisters.ITEM_MISSILECLOSERANGE.get()) {
		    hasRange = true;
		    missile = st;
		}
		if (hasRange && hasExplosive) {
		    break;
		}
	    }
	    if (hasExplosive && hasRange) {
		ex.shrink(1);
		missile.shrink(1);
		EntityMissile miss = new EntityMissile(world);
		miss.moveTo(entityLiving.getX(), entityLiving.getY() + entityLiving.getEyeHeight() * 0.8, entityLiving.getZ(), entityLiving.yRot,
			entityLiving.xRot);
		miss.setDeltaMovement(entityLiving.getLookAngle().x * 2, entityLiving.getLookAngle().y * 2, entityLiving.getLookAngle().z * 2);
		miss.blastOrdinal = blastOrdinal;
		miss.range = 0;
		miss.isItem = true;
		world.addFreshEntity(miss);
	    }
	}
    }
}