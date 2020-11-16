package io.github.haykam821.lightningprevention.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.fabricmc.fabric.api.tag.TagRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LightningEntity;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

@Mixin(LightningEntity.class)
public class LightningEntityMixin {
	@Unique
	private static final Identifier LIGHTNING_FIRE_BASE_BLOCKS_ID = new Identifier("lightningprevention", "lightning_fire_base_blocks");

	@Unique
	private static final Tag<Block> LIGHTNING_FIRE_BASE_BLOCKS = TagRegistry.block(LIGHTNING_FIRE_BASE_BLOCKS_ID);

	@Redirect(method = "spawnFire", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;setBlockState(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;)Z"))
	private boolean preventSpawningFireOnNonWhitelistedBases(World world, BlockPos pos, BlockState state) {
		if (world.getBlockState(pos.down()).isIn(LIGHTNING_FIRE_BASE_BLOCKS)) {
			return world.setBlockState(pos, state);
		}
		return false;
	}
}