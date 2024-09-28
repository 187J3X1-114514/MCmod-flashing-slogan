package io.homo.mcmodsplash.mcmodsplash.fabric;

import io.homo.mcmodsplash.mcmodsplash.MCMODSplash;
import net.fabricmc.api.ModInitializer;

public class MCMODSplashFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        new MCMODSplash().init();
    }
}