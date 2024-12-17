package com.mod.ia;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.text.TextComponentString;
import com.mod.ia.ManageMessagePlayer;

public class VillagerInteractionHandler {

    public static void interactWithVillager() {
        // Obtén al jugador
        Minecraft mc = Minecraft.getMinecraft();
        if (mc.player == null) return;

        // Define el rango de búsqueda alrededor del jugador (radio de 5 bloques)
        double range = 5.0;
        AxisAlignedBB searchBox = new AxisAlignedBB(
                mc.player.posX - range, mc.player.posY - range, mc.player.posZ - range,
                mc.player.posX + range, mc.player.posY + range, mc.player.posZ + range
        );

        // Encuentra todos los aldeanos en el rango
        EntityVillager closestVillager = mc.world.getEntitiesWithinAABB(EntityVillager.class, searchBox)
                .stream()
                .min((v1, v2) -> {
                    double d1 = mc.player.getDistance(v1);
                    double d2 = mc.player.getDistance(v2);
                    return Double.compare(d1, d2);
                })
                .orElse(null);

        // Si se encuentra un aldeano, imprime su información
        if (closestVillager != null) {
            System.out.println("Aldeano más cercano encontrado: " + closestVillager.getName());
            System.out.println("Posición: " + closestVillager.getPosition());
            ManageMessagePlayer.sendMessageToPlayer("Aldeano mas cercano encontrado");
            //Iniciar el chat para capturar el mensaje del jugador
            //Despues que este se cargue en el obtener respuesta de OpenAI
            String lastmessage = ManageMessagePlayer.getLastMessage();
            if (lastmessage == null || lastmessage.isEmpty()) {
                lastmessage = "Nomba una fruta"; // Mensaje por defecto si no hay entrada del jugador
            }
            
            // Obtener respuesta de OpenAI
            String aiResponse = AIResponseHandler.getAIResponse(lastmessage);

            // Mostrar la respuesta en el chat del juego
            mc.player.sendMessage(new TextComponentString("Aldeano: " + aiResponse));
        } else {
        	mc.player.sendMessage(new TextComponentString("No hay aldeanos cerca para interactuar."));        }
    }
}
