package io.github.andrew6rant.breakvalidator.mixin.client;

import net.minecraft.client.render.model.ModelLoader;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

//@Debug(export = true)
@Mixin(ModelLoader.class)
public class ModelLoaderMixin {
    @Mutable @Final @Shadow public static int field_32983;
    @Mutable @Final @Shadow public static List<Identifier> BLOCK_DESTRUCTION_STAGES;


    @ModifyConstant(method = "<clinit>",
            constant = @Constant(intValue = 10, ordinal = 0))
    private static int breakvalidator$modifyModelBreakStages(int constant) {
        return 12;
    }

    @Inject(method = "<clinit>", at = @At("TAIL"))
    private static void breakvalidator$injectField_32983(CallbackInfo ci) {
        field_32983 = 12;
    }


    @Inject(method = "<clinit>", at = @At("TAIL"))
    private static void breakvalidator$injectModelBreakStages(CallbackInfo ci) {
        BLOCK_DESTRUCTION_STAGES = IntStream.range(0, 12).mapToObj((stage) -> new Identifier("block/destroy_stage_" + stage)).collect(Collectors.toList());
    }
}
