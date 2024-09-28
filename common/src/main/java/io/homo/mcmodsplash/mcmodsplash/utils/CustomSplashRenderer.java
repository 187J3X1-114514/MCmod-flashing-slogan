package io.homo.mcmodsplash.mcmodsplash.utils;

import com.mojang.math.Axis;
import io.homo.mcmodsplash.mcmodsplash.MCMODSplash;
import net.minecraft.Util;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.SplashRenderer;
import net.minecraft.util.Mth;

public class CustomSplashRenderer extends SplashRenderer {
    protected String splash;

    public CustomSplashRenderer(String string) {
        super(string);
        this.splash = string;
    }

    @Override
    public void render(GuiGraphics guiGraphics, int i, Font font, int j) {
        guiGraphics.pose().pushPose();
        guiGraphics.pose().translate((float)i / 2.0F + 123.0F, 69.0F, 0.0F);
        guiGraphics.pose().mulPose(Axis.ZP.rotationDegrees(MCMODSplash.getInstance().splashManager.shouldUseSplashDegrees() ?MCMODSplash.getInstance().splashManager.getSplashDegrees():-20.0F));
        float f = 1.8F - Mth.abs(Mth.sin((float)(Util.getMillis() % 1000L) / 1000.0F * 6.2831855F) * 0.1F);
        f = f * 100.0F / (float)(font.width(this.splash) + 32);
        guiGraphics.pose().scale(f, f, f);
        guiGraphics.drawCenteredString(font, this.splash, 0, -8, MCMODSplash.getInstance().splashManager.shouldUseSplashColor() ? MCMODSplash.getInstance().splashManager.getSplashColor().toInt() : 16776960 | j);
        guiGraphics.pose().popPose();
    }

    public void setSplash(String splash) {
        this.splash = splash;
    }

    public String getSplash() {
        return splash;
    }
}
