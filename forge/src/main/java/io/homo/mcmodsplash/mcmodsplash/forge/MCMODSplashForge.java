package io.homo.mcmodsplash.mcmodsplash.forge;

import io.homo.mcmodsplash.mcmodsplash.MCMODSplash;
import io.homo.mcmodsplash.mcmodsplash.gui.ConfigScreen;
import net.minecraftforge.client.ConfigGuiHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.ModLoadingContext;

@Mod(MCMODSplash.MOD_ID)
public class MCMODSplashForge {
    public MCMODSplashForge() {
        new MCMODSplash().init();
        ModLoadingContext.get().registerExtensionPoint(ConfigGuiHandler.ConfigGuiFactory.class, () -> new ConfigGuiHandler.ConfigGuiFactory((mc, screen) -> ConfigScreen.buildConfigScreen(screen)));
    }
}