package io.homo.mcmodsplash.mcmodsplash.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import io.homo.mcmodsplash.mcmodsplash.MCMODSplash;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.resources.SplashManager;
import net.minecraft.util.Mth;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

@Mixin(TitleScreen.class)
public class TitleScreenMixin {
    @Final
    @Shadow private boolean fading;
    @Shadow
    private long fadeInStart;
    @Unique
    private Timer MCMODSplash$timer;

    @Redirect(method = "init",at = @At(value = "INVOKE", target = "Lnet/minecraft/client/resources/SplashManager;getSplash()Ljava/lang/String;"))
    private String modifySplash(SplashManager instance) {
        MCMODSplash.currentSplashRenderer = MCMODSplash.getInstance().splashManager.getSplash();
        if (MCMODSplash.getInstance().config.splashChangeInterval != -1) MCMODSplash$createTimer();
        return null;
    }

    @Unique
    private void MCMODSplash$createTimer(){
        cancelMCMODSplash$timer();
        MCMODSplash$timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                if (MCMODSplash.currentSplashRenderer != null){
                    MCMODSplash.currentSplashRenderer.setSplash(MCMODSplash.getInstance().splashManager.MCMOD_SPLASH.getRandomSplash().getSplash());
                }
            }
        };
        MCMODSplash$timer.scheduleAtFixedRate(task, new Date(), MCMODSplash.getInstance().config.splashChangeInterval);
    }

    @Unique
    private void cancelMCMODSplash$timer(){
        if (MCMODSplash$timer != null) MCMODSplash$timer.cancel();
    }

    @Inject(method = "removed",at=@At(value = "HEAD"))
    public void cancelMCMODSplash$timer_1 (CallbackInfo ci){
        cancelMCMODSplash$timer();
        MCMODSplash.currentSplashRenderer = null;
    }

    @Inject(method = "render",at= @At(value = "INVOKE", target = "Lnet/minecraft/WorldVersion;getName()Ljava/lang/String;"))
    public void renderSplash(PoseStack poseStack, int i, int j, float f, CallbackInfo ci){
        if (MCMODSplash.currentSplashRenderer != null){
            float g = this.fading ? (float)(Util.getMillis() - this.fadeInStart) / 1000.0F : 1.0F;
            float h = this.fading ? Mth.clamp(g - 1.0F, 0.0F, 1.0F) : 1.0F;
            int n = Mth.ceil(h * 255.0F) << 24;
            if (Minecraft.getInstance().screen != null) {
                MCMODSplash.currentSplashRenderer.render(poseStack,Minecraft.getInstance().screen.width, Minecraft.getInstance().font, n);
            }
        }
    }

}
