package io.homo.mcmodsplash.mcmodsplash.utils;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import io.homo.mcmodsplash.mcmodsplash.MCMODSplash;
import net.minecraft.Util;
import net.minecraft.client.gui.Font;
import net.minecraft.util.Mth;

import static net.minecraft.client.gui.GuiComponent.drawCenteredString;

public class CustomSplashRenderer {
    protected String splash;

    public CustomSplashRenderer(String string) {
        this.splash = string;
    }

    public void render(PoseStack poseStack, int i, Font font,int j) {
        poseStack.pushPose();
        poseStack.translate(((double) i / 2 + 90), 70.0, 0.0);
        poseStack.mulPose(Vector3f.ZP.rotationDegrees(MCMODSplash.getInstance().splashManager.shouldUseSplashDegrees() ?MCMODSplash.getInstance().splashManager.getSplashDegrees():-20.0F));
        float o = 1.8F - Mth.abs(Mth.sin((float)(Util.getMillis() % 1000L) / 1000.0F * 6.2831855F) * 0.1F);
        o = o * 100.0F / (float)(font.width(this.splash) + 32);
        poseStack.scale(o, o, o);
        drawCenteredString(poseStack, font, this.splash, 0, -8, MCMODSplash.getInstance().splashManager.shouldUseSplashColor() ? MCMODSplash.getInstance().splashManager.getSplashColor().toInt() : 16776960 | j);
        poseStack.popPose();
    }

    public void setSplash(String splash) {
        this.splash = splash;
    }

    public String getSplash() {
        return splash;
    }
}
