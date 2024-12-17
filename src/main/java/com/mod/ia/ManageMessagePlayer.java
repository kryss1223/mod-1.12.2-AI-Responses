package com.mod.ia;


import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ManageMessagePlayer {    

    public void ChatMessageHandler() {
        // Registra la clase en el bus de eventos
        MinecraftForge.EVENT_BUS.register(this);
    }
    private static String message = "";
    
    @SubscribeEvent
    public void onChatMessage(ClientChatEvent event) {
        // Captura el mensaje enviado por el jugador
    	message = event.getMessage();
    	
        // Imprime el mensaje para depuración
        System.out.println("Mensaje capturado: " + message);
        ManageMessagePlayer.sendMessageToPlayer("Mensaje capturado");
		return;
		
        }

    // Método para obtener el último mensaje capturado
    public static String getLastMessage() {    	
        return message;
    }
    
    // Método para enviar mensajes al chat del jugador
    public static void sendMessageToPlayer(String infomessage) {
        EntityPlayerSP player = Minecraft.getMinecraft().player;  // Obtener el jugador
        if (player != null) {
            player.sendMessage(new TextComponentString(infomessage));  // Enviar mensaje al chat
        }
    }
}
    
	


