package io.github.andrew6rant.breakvalidator.mixin.client;

import io.github.andrew6rant.breakvalidator.accessor.OverlayColorAccessor;
import net.minecraft.client.render.OverlayVertexConsumer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(OverlayVertexConsumer.class)
public abstract class OverlayVertexConsumerMixin implements OverlayColorAccessor {
    @Unique private float red = 1.0f;
    @Unique private float green = 1.0f;
    @Unique private float blue = 1.0f;
    @Unique private float alpha = 1.0f;


    @ModifyConstant(method = "next",
            constant = @Constant(floatValue = 1.0f, ordinal = 1))
    private float breakvalidator$modifyRed(float constant) {
        return this.red;
    }

    @ModifyConstant(method = "next",
            constant = @Constant(floatValue = 1.0f, ordinal = 2))
    private float breakvalidator$modifyGreen(float constant) {
        return this.green;
    }

    @ModifyConstant(method = "next",
            constant = @Constant(floatValue = 1.0f, ordinal = 3))
    private float breakvalidator$modifyBlue(float constant) {
        return this.blue;
    }

    @ModifyConstant(method = "next",
            constant = @Constant(floatValue = 1.0f, ordinal = 4))
    private float breakvalidator$modifyAlpha(float constant) {
        return this.alpha;
    }

    @Override
    public void setOverlayColor(float red, float green, float blue, float alpha) {
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.alpha = alpha;
    }
}
