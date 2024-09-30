package io.homo.mcmodsplash.mcmodsplash.forge;

import io.homo.mcmodsplash.mcmodsplash.MCMODSplash;
import io.homo.mcmodsplash.mcmodsplash.gui.ConfigScreen;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.ModLoadingContext;

import static net.minecraftforge.fml.ExtensionPoint.CONFIGGUIFACTORY;

@Mod(MCMODSplash.MOD_ID)
public class MCMODSplashForge {
    public MCMODSplashForge() {
        new MCMODSplash().init();

        ModLoadingContext.get().registerExtensionPoint(CONFIGGUIFACTORY, () -> (mc, screen) -> ConfigScreen.buildConfigScreen(screen));
    }
}