package io.github.andrew6rant.breakvalidator.mixin.client;

import io.github.andrew6rant.breakvalidator.accessor.OverlayColorAccessor;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.OverlayVertexConsumer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.block.BlockModelRenderer;
import net.minecraft.client.render.block.BlockModels;
import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockRenderView;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BlockRenderManager.class)
public class BlockRenderManagerMixin {

    @Shadow @Final private BlockModels models;

    @Shadow @Final private BlockModelRenderer blockModelRenderer;

    @Shadow @Final private Random random;

    @Inject(method = "renderDamage(Lnet/minecraft/block/BlockState;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/world/BlockRenderView;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumer;)V", at = @At("HEAD"), cancellable = true)
    public void breakvalidator$renderSlabDamage(BlockState state, BlockPos pos, BlockRenderView world, MatrixStack matrices, VertexConsumer vertexConsumer, CallbackInfo ci) {
        if (state.getRenderType() == BlockRenderType.MODEL) {
            ClientPlayerEntity clientPlayer = MinecraftClient.getInstance().player;
            if (clientPlayer == null) return;
            if (vertexConsumer instanceof OverlayVertexConsumer overlayVertexConsumer) {
                if (!state.getBlock().equals(Blocks.POLISHED_DIORITE)) { // temporary until I write actual block detection logic
                    overlayVertexConsumer.unfixColor();
                    ((OverlayColorAccessor)overlayVertexConsumer).setOverlayColor(0.8f, 0.6f, 0.58f, 1.0f);
                    BakedModel bakedModel = this.models.getModel(state);
                    long l = state.getRenderingSeed(pos);
                    this.blockModelRenderer.render(world, bakedModel, state, pos, matrices, overlayVertexConsumer, false, this.random, l, OverlayTexture.DEFAULT_UV);
                    ci.cancel();
                }
            }
        }
    }
}
