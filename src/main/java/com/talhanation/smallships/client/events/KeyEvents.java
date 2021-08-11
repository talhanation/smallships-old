package com.talhanation.smallships.client.events;

import com.talhanation.smallships.entities.TNBoatEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

import javax.swing.text.JTextComponent;

public class KeyEvents {

    private boolean wasSailPressed;
    private boolean wasInvPressed;
    private boolean wasDismountPressed;
    private KeyBinding key_sail;
    private KeyBinding key_inv;
    private KeyBinding key_L_sail;
    private KeyBinding key_H_sail;

    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) {
        Minecraft minecraft = Minecraft.getMinecraft();
        EntityPlayer clientPlayerEntity = minecraft.player;
        if (clientPlayerEntity == null)
            return;

        Entity vehicle = clientPlayerEntity.getRidingEntity();
        if (!(vehicle instanceof TNBoatEntity)){
            return;
        }
        TNBoatEntity boat = (TNBoatEntity) vehicle;
        if (clientPlayerEntity == (boat.getDriver())) {
            if (this.key_sail.isKeyDown()) {
                boat.onKeyPressed();
                this.wasSailPressed = true;
            }
            else {
                this.wasSailPressed = false;
            }
        }
        if (boat.getPassengers().contains(clientPlayerEntity)) {
            if (this.key_inv.isKeyDown()) {
                boat.onInvPressed(clientPlayerEntity);
                this.wasInvPressed = true;
            }else {
                this.wasInvPressed = false;

            }
        }
        if (clientPlayerEntity == (boat.getDriver())) {
            if (this.key_L_sail.isKeyDown()) {
                boat.onKeyLowerPressed();
                this.wasSailPressed = true;
            }
            else {
                this.wasSailPressed = false;
            }
        }
        if (clientPlayerEntity == (boat.getDriver())) {
            if (this.key_H_sail.isKeyDown()) {
                boat.onKeyHigherPressed();
                this.wasSailPressed = true;
            }
            else {
                this.wasSailPressed = false;
            }
        }
        /*if (clientPlayerEntity.equals(boat.getDriver())) {
            if (Main.DISMOUNT_KEY.isDown()) {
                boat.onDismountPressed();
                this.wasDismountPressed = true;
            }
            else {
                this.wasDismountPressed = false;
            }
        }
    */
    }

    public KeyEvents(){
        this.key_sail = new KeyBinding("key.ship_sail", 82, "category.smallships");
        this.key_inv = new KeyBinding("key.ship_inventory", 73, "category.smallships");
        this.key_L_sail = new KeyBinding("key.lower_ship_sail", 74, "category.smallships");
        this.key_H_sail = new KeyBinding("key.higher_ship_sail", 75, "category.smallships");
    }

}
