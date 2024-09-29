package io.homo.mcmodsplash.mcmodsplash.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import io.homo.mcmodsplash.mcmodsplash.MCMODSplash;
import net.minecraft.client.Minecraft;
import net.minecraft.client.OptionInstance;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.OptionsList;
import net.minecraft.client.gui.screens.OptionsSubScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;
import org.apache.commons.compress.utils.Lists;

import java.util.List;

public class ConfigScreen  extends OptionsSubScreen {

    public OptionInstance<Boolean> obtainSplashOnline;
    public OptionInstance<Integer> getSplashTimeout;
    public OptionInstance<Boolean> colourfulSplash;
    public OptionInstance<Integer> splashChangeInterval;


    private final List<OptionInstance<?>> optionsInstance = Lists.newArrayList();
    protected OptionsList list;

    public static ConfigScreen buildConfigScreen(Screen p){
        return new ConfigScreen(p);
    }


    private ConfigScreen(Screen screen) {
        super(screen,Minecraft.getInstance().options, Component.translatable("mcmodsplash.config.title"));
        this.obtainSplashOnline = OptionInstance.createBoolean(
                "mcmodsplash.config.obtainSplashOnline",
                OptionInstance.cachedConstantTooltip(
                        Component.translatable("mcmodsplash.config.obtainSplashOnline.tip")
                ),
                MCMODSplash.getInstance().config.obtainSplashOnline,
                (boolean_) -> {

                });
        this.colourfulSplash = OptionInstance.createBoolean(
                "mcmodsplash.config.colourfulSplash",
                OptionInstance.cachedConstantTooltip(
                        Component.translatable("mcmodsplash.config.colourfulSplash.tip")
                ),
                MCMODSplash.getInstance().config.colourfulSplash,
                (boolean_) -> {

                });
        this.getSplashTimeout = new OptionInstance<>(
                "mcmodsplash.config.getSplashTimeout",
                OptionInstance.cachedConstantTooltip(Component.translatable("mcmodsplash.config.getSplashTimeout.tip")),
                (component, integer) -> Component.literal(component.getString()+" : "+integer.toString()+"ms"),
                new OptionInstance.IntRange(100, 15000), MCMODSplash.getInstance().config.MCMOD_TIMEOUT,
                (integer) -> {
                });
        this.splashChangeInterval = new OptionInstance<>(
                "mcmodsplash.config.splashChangeInterval",
                OptionInstance.cachedConstantTooltip(Component.translatable("mcmodsplash.config.splashChangeInterval.tip")),
                (component, integer) -> {
                    if (integer == -1)return Component.literal(component.getString() + " : " +CommonComponents.OPTION_OFF.getString());
                    if (integer == 0)return Component.literal(component.getString() + " : " +"让你飞起来");
                    return Component.literal(component.getString() + " : " + integer + "s");
                },
                new OptionInstance.IntRange(-1, 300), MCMODSplash.getInstance().config.splashChangeInterval == 10?0:MCMODSplash.getInstance().config.splashChangeInterval == -1 ? -1:(MCMODSplash.getInstance().config.splashChangeInterval/1000),
                (integer) -> {
                });
        this.optionsInstance.add(this.obtainSplashOnline);
        this.optionsInstance.add(this.getSplashTimeout);
        this.optionsInstance.add(this.colourfulSplash);
        this.optionsInstance.add(this.splashChangeInterval);

    }

    protected void init() {
        this.list = new OptionsList(this.minecraft, this.width, this.height, 32, this.height - 32, 25);
        for (OptionInstance<?> option : this.optionsInstance) {
            this.list.addBig(option);
        }
        this.addWidget(this.list);
        this.createFooter();

    }

    protected void createFooter() {
        this.addRenderableWidget(new Button(this.width / 2 - 100, this.height - 27, 200, 20, CommonComponents.GUI_DONE, (button) -> {
            this.updateConfig();
            if (this.minecraft != null) {
                this.minecraft.setScreen(this.lastScreen);
            }
        }));
    }

    private void updateConfig(){
        MCMODSplash.getInstance().config.obtainSplashOnline = this.obtainSplashOnline.get();
        MCMODSplash.getInstance().config.colourfulSplash = this.colourfulSplash.get();
        MCMODSplash.getInstance().config.MCMOD_TIMEOUT= this.getSplashTimeout.get();
        MCMODSplash.getInstance().config.splashChangeInterval = this.splashChangeInterval.get() == -1 ? -1 : this.splashChangeInterval.get() == 0 ? 10 : this.splashChangeInterval.get()*1000;
        MCMODSplash.getInstance().config.write();
    }

    public void render(PoseStack poseStack, int i, int j, float f) {
        this.renderBackground(poseStack);
        this.list.render(poseStack, i, j, f);
        drawCenteredString(poseStack, this.font, this.title, this.width / 2, 20, 16777215);
        super.render(poseStack, i, j, f);
        List<FormattedCharSequence> list = tooltipAt(this.list, i, j);
        this.renderTooltip(poseStack, list, i, j);
    }

    @Override
    public void removed() {}

    @Override
    public void onClose() {
        if (this.minecraft != null) {
            this.minecraft.setScreen(this.lastScreen);
        }
    }
}
