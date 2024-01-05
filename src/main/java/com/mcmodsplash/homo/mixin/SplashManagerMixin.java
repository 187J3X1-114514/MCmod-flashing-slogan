package com.mcmodsplash.homo.mixin;

import com.mcmodsplash.homo.utils.GetMCMODSplash;
import org.spongepowered.asm.mixin.Mixin;
import net.minecraft.client.resources.SplashManager;
import org.spongepowered.asm.mixin.Overwrite;
import javax.annotation.Nullable;

@Mixin(SplashManager.class)
public abstract class SplashManagerMixin {
    /**
     * 额，很无语
     *
     * @return 不知道这里不加Javadoc为什么会报错
     * @author so
     * @reason 就加了笑[doge]
     */
    @Nullable //IDE提示的
    @Overwrite
    public String getSplash(){
            return GetMCMODSplash.get();
    }

}
