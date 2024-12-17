package com.mod.ia;
//GUI para capturar el mensaje del jugador. NO USAR POR AHORA
import io.netty.util.internal.shaded.org.jctools.queues.MessagePassingQueue.Consumer;
import net.minecraft.client.gui.GuiScreen;



public class ChatInputScreen extends GuiScreen {
    private Consumer<String> callback;

    public ChatInputScreen(Consumer<String> callback) {
        this.callback = callback;
    }

    @Override
    public void initGui() {
        // Aquí diseñas la interfaz gráfica para capturar el texto del jugador
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) {
    	if (keyCode == 28) { //Tecla enter
    		 // Llama al callback con el mensaje del jugador
            callback.accept("Mensaje introducido por el jugador");
            mc.displayGuiScreen(null); // Cierra la GUI
    	}

        }
    }


