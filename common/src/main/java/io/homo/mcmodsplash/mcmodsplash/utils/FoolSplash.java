package io.homo.mcmodsplash.mcmodsplash.utils;

import io.homo.mcmodsplash.mcmodsplash.MCMODSplash;
import net.minecraft.client.gui.components.SplashRenderer;

import java.util.ArrayList;
import java.util.Random;

public class FoolSplash {
    private static final Random random = new Random();
    private static final ArrayList<String> SplashList = new ArrayList<>();
    static {
        SplashList.add("哈基米");
        SplashList.add("曼波~ 曼波~");
        SplashList.add("艹");
        SplashList.add("小黑子");
        SplashList.add("Genshin impact 启动！");
        SplashList.add("O.o");
        SplashList.add("o.O");
        SplashList.add("↑↓");
        SplashList.add("嗨嗨嗨");
    }
    public static CustomSplashRenderer getRandomSplash(){
        return new CustomSplashRenderer(SplashList.get(random.nextInt(SplashList.size())));
    }
}
