package com.supermartijn642.itemcollectors.screen;

import com.mojang.blaze3d.vertex.PoseStack;
import com.supermartijn642.core.TextComponents;
import com.supermartijn642.core.gui.ScreenUtils;
import com.supermartijn642.core.gui.widget.AbstractButtonWidget;
import com.supermartijn642.core.gui.widget.IHoverTextWidget;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

/**
 * Created 7/8/2020 by SuperMartijn642
 */
public class ArrowButton extends AbstractButtonWidget implements IHoverTextWidget {

    private final ResourceLocation BUTTONS = new ResourceLocation("itemcollectors", "textures/arrow_buttons.png");

    private final boolean down;

    public ArrowButton(int x, int y, boolean down, Runnable onPress){
        super(x, y, 17, 11, onPress);
        this.down = down;
    }

    @Override
    protected Component getNarrationMessage(){
        return this.getHoverText();
    }

    @Override
    public void render(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks){
        ScreenUtils.bindTexture(BUTTONS);
        ScreenUtils.drawTexture(matrixStack, this.x, this.y, this.width, this.height, this.down ? 0.5f : 0, this.active ? this.hovered ? 1 / 3f : 0 : 2 / 3f, 0.5f, 1 / 3f);
    }

    @Override
    public Component getHoverText(){
        return TextComponents.translation("gui.itemcollectors.basic_collector.range." + (this.down ? "decrease" : "increase")).get();
    }
}
