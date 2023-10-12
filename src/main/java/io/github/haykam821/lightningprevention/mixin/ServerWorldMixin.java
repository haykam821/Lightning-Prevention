package io.github.haykam821.lightningprevention.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.fabricmc.fabric.api.tag.TagFactory;
import net.minecraft.block.Block;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

@Mixin(ServerWorld.class)
public class ServerWorldMixin {
	@Unique
	private static final Identifier PREVENT_LIGHTNING_STRIKE_BASE_BLOCKS_ID = new Identifier("lightningprevention", "prevent_lightning_strike_base_blocks");

	@Unique
	private static final Tag<Block> PREVENT_LIGHTNING_STRIKE_BASE_BLOCKS = TagFactory.BLOCK.create(PREVENT_LIGHTNING_STRIKE_BASE_BLOCKS_ID);

	@Redirect(method = "tickChunk", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/world/ServerWorld;hasRain(Lnet/minecraft/util/math/BlockPos;)Z"))
	private boolean preventStrikingOnBlacklistedBases(ServerWorld world, BlockPos pos) {
		if (world.getBlockState(pos.down()).isIn(PREVENT_LIGHTNING_STRIKE_BASE_BLOCKS)) return false;
		return world.hasRain(pos);
	}
}
