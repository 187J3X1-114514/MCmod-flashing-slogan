package io.homo.mcmodsplash.mcmodsplash.mixin;

import io.homo.mcmodsplash.mcmodsplash.MCMODSplash;
import io.homo.mcmodsplash.mcmodsplash.utils.CustomSplashRenderer;
import net.minecraft.client.gui.components.SplashRenderer;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.resources.SplashManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

@Mixin(TitleScreen.class)
public class TitleScreenMixin {
    @Unique
    private Timer MCMODSplash$timer;

    @Redirect(method = "init",at = @At(value = "INVOKE", target = "Lnet/minecraft/client/resources/SplashManager;getSplash()Lnet/minecraft/client/gui/components/SplashRenderer;"))
    private SplashRenderer modifySplash(SplashManager instance) {
        CustomSplashRenderer sr = MCMODSplash.getInstance().splashManager.getSplash();
        MCMODSplash.currentSplashRenderer = sr;
        if (MCMODSplash.getInstance().config.splashChangeInterval != -1) MCMODSplash$createTimer();
        return sr;
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

}
