package io.github.andrew6rant.breakvalidator.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.BlockBreakingInfo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

//@Environment(EnvType.CLIENT)
@Mixin(BlockBreakingInfo.class)
public class BlockBreakingInfoMixin {
    @ModifyConstant(method = "setStage(I)V",
            constant = {
                @Constant(intValue = 10, ordinal = 0),
                @Constant(intValue = 10, ordinal = 1)
            })
    private static int breakvalidator$modifyBreakStage(int constant) {
        return 12;
    }
}
