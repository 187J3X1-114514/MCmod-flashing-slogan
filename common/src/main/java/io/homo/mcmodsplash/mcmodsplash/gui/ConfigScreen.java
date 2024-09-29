package io.homo.mcmodsplash.mcmodsplash.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import io.homo.mcmodsplash.mcmodsplash.MCMODSplash;
import net.minecraft.client.CycleOption;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Option;
import net.minecraft.client.ProgressOption;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.OptionsList;
import net.minecraft.client.gui.screens.OptionsSubScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.util.FormattedCharSequence;
import org.apache.commons.compress.utils.Lists;

import java.util.List;

public class ConfigScreen  extends OptionsSubScreen {

    public CycleOption<Boolean> obtainSplashOnline;
    public ProgressOption getSplashTimeout;
    public CycleOption<Boolean> colourfulSplash;
    public ProgressOption splashChangeInterval;


    private final List<Option> optionsInstance = Lists.newArrayList();
    protected OptionsList list;

    public static ConfigScreen buildConfigScreen(Screen p){
        return new ConfigScreen(p);
    }


    private ConfigScreen(Screen screen) {
        super(screen,Minecraft.getInstance().options, new TranslatableComponent("mcmodsplash.config.title"));
        this.obtainSplashOnline = CycleOption.createOnOff(
                "mcmodsplash.config.obtainSplashOnline",
                new TranslatableComponent("mcmodsplash.config.obtainSplashOnline.tip"),
                (options) -> MCMODSplash.getInstance().config.obtainSplashOnline,
                (options, option, boolean_) -> MCMODSplash.getInstance().config.obtainSplashOnline = boolean_);
        this.colourfulSplash = CycleOption.createOnOff(
                "mcmodsplash.config.colourfulSplash",
                new TranslatableComponent("mcmodsplash.config.colourfulSplash.tip"),
                (options) ->MCMODSplash.getInstance().config.colourfulSplash,
                (options, option, boolean_) -> MCMODSplash.getInstance().config.colourfulSplash = boolean_);
        this.getSplashTimeout = new ProgressOption(
                "mcmodsplash.config.getSplashTimeout",
                100,
                15000,
                1.0f,
                (options) -> (double) MCMODSplash.getInstance().config.MCMOD_TIMEOUT,
                (options, double_) -> MCMODSplash.getInstance().config.MCMOD_TIMEOUT = double_.intValue(),
                (options, progressOption) -> new TextComponent(new TranslatableComponent("mcmodsplash.config.getSplashTimeout").getString()+" : "+MCMODSplash.getInstance().config.MCMOD_TIMEOUT+"ms"),
                (minecraft)-> minecraft.font.split(new TranslatableComponent("mcmodsplash.config.colourfulSplash.tip"), 200)
        );
        this.splashChangeInterval = new ProgressOption(
                "mcmodsplash.config.splashChangeInterval",
                -1,
                300,
                1.0f,
                (options) -> (double) ((double) MCMODSplash.getInstance().config.splashChangeInterval == 10?0:MCMODSplash.getInstance().config.splashChangeInterval == -1 ? -1:(MCMODSplash.getInstance().config.splashChangeInterval/1000)),
                (options, double_) -> MCMODSplash.getInstance().config.splashChangeInterval = double_.intValue() == -1 ? -1 : double_.intValue() == 0 ? 10 : double_.intValue()*1000,
                (options, progressOption) -> {
                    int integer = MCMODSplash.getInstance().config.splashChangeInterval;
                    TranslatableComponent component = new TranslatableComponent("mcmodsplash.config.splashChangeInterval");
                    if (integer == -1)return new TextComponent(component.getString() + " : " +CommonComponents.OPTION_OFF.getString());
                    if (integer == 10)return new TextComponent(component.getString() + " : " +"让你飞起来");
                    return new TextComponent(component.getString() + " : " + integer/1000 + "s");
                },
                (minecraft)-> minecraft.font.split(new TranslatableComponent("mcmodsplash.config.splashChangeInterval.tip"), 200)
        );
        this.optionsInstance.add(this.obtainSplashOnline);
        this.optionsInstance.add(this.getSplashTimeout);
        this.optionsInstance.add(this.colourfulSplash);
        this.optionsInstance.add(this.splashChangeInterval);

    }

    protected void init() {
        this.list = new OptionsList(this.minecraft, this.width, this.height, 32, this.height - 32, 25);
        for (Option option : this.optionsInstance) {
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
        //MCMODSplash.getInstance().config.obtainSplashOnline = this.obtainSplashOnline.get();
        //MCMODSplash.getInstance().config.colourfulSplash = this.colourfulSplash.get();
        //MCMODSplash.getInstance().config.MCMOD_TIMEOUT= this.getSplashTimeout.get();
        //MCMODSplash.getInstance().config.splashChangeInterval = this.splashChangeInterval.get() == -1 ? -1 : this.splashChangeInterval.get() == 0 ? 10 : this.splashChangeInterval.get()*1000;
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
