package com.mod.ia;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import com.mod.ia.keybind.*;
import com.mod.ia.ManageMessagePlayer;

@Mod(modid = MainClass.MODID, name = MainClass.NAME, version = MainClass.VERSION)
public class MainClass {
    public static final String MODID = "villageria";
    public static final String NAME = "Villager IA Mod";
    public static final String VERSION = "1.0";

	
    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
    	KeybindHandler.registerKeybinds(event);
        MinecraftForge.EVENT_BUS.register(new KeybindHandler()); // Registra la clase
        MinecraftForge.EVENT_BUS.register(new ManageMessagePlayer());

    }
    
}