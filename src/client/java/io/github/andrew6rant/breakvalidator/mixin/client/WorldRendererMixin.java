package io.github.andrew6rant.breakvalidator.mixin.client;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.client.render.BlockBreakingInfo;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Iterator;

@Mixin(WorldRenderer.class)
public class WorldRendererMixin {
    @Mutable @Final @Shadow private static int field_32764;

    @Shadow @Final private Int2ObjectMap<BlockBreakingInfo> blockBreakingInfos;

    @Shadow private int ticks;

    @ModifyConstant(method = "setBlockBreakingInfo(ILnet/minecraft/util/math/BlockPos;I)V",
            constant = @Constant(intValue = 10, ordinal = 0))
    private static int breakvalidator$modifyWorldBreakStages(int constant) {
        return 12;
    }

    @ModifyConstant(method = "tick()V",
            constant = @Constant(intValue = 400, ordinal = 0))
    private static int breakvalidator$modifyWorldTickBreakStages(int constant) {
        return 680; // 480
    }

    @Inject(method = "<clinit>", at = @At("TAIL"))
    private static void breakvalidator$injectField_32764(CallbackInfo ci) {
        field_32764 = 12;
    }

    @Inject(method = "setBlockBreakingInfo(ILnet/minecraft/util/math/BlockPos;I)V", at = @At("HEAD"))
    private void breakvalidator$test(int entityId, BlockPos pos, int stage, CallbackInfo ci) {
        System.out.println("stage: "+stage);
    }

    @Inject(method = "tick()V", at = @At("HEAD"))
    private void breakvalidator$tickingTest(CallbackInfo ci) {
        if (this.ticks % 20 == 0) {
            System.out.println(this.blockBreakingInfos.values().size());
        }
    }
}
