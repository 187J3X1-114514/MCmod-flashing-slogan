package io.homo.mcmodsplash.mcmodsplash.forge;

import io.homo.mcmodsplash.mcmodsplash.MCMODSplash;
import io.homo.mcmodsplash.mcmodsplash.gui.ConfigScreen;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.ModLoadingContext;

@Mod(MCMODSplash.MOD_ID)
public class MCMODSplashForge {
    public MCMODSplashForge() {
        new MCMODSplash().init();
        ModLoadingContext.get().registerExtensionPoint(ConfigScreenHandler.ConfigScreenFactory.class, () -> new ConfigScreenHandler.ConfigScreenFactory((mc, screen) -> ConfigScreen.buildConfigScreen(screen)));
    }
}