package com.mod.ia.keybind;

import com.mod.ia.VillagerInteractionHandler;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import com.mod.ia.ManageMessagePlayer;

public class KeybindHandler {
 private static final KeyBinding interactKey = new KeyBinding("Interactuar con aldeano", 18, "NPC AI Mod");

 public static void registerKeybinds(FMLInitializationEvent event) {
     ClientRegistry.registerKeyBinding(interactKey);
 }

 @SubscribeEvent
 public void onKeyInput(InputEvent.KeyInputEvent event) {
	 if (FMLCommonHandler.instance().getSide().isClient() && interactKey.isPressed()) {
		 //Sacar un mensaje de que se ha presionado la tecla
		 ManageMessagePlayer.sendMessageToPlayer("Tecla E pulsada");
		    VillagerInteractionHandler.interactWithVillager();
		}

     }

 }

