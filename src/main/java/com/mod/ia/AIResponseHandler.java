package com.mod.ia;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class AIResponseHandler {
    private static final String API_KEY = "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";

    public static String getAIResponse(String input) {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            // Construcción del JSON
            JsonObject jsonBody = new JsonObject();
            jsonBody.addProperty("model", "gpt-3.5-turbo");

            JsonArray messages = new JsonArray();
            JsonObject userMessage = new JsonObject();
            userMessage.addProperty("role", "user");
            userMessage.addProperty("content", input);
            messages.add(userMessage);
            jsonBody.add("messages", messages);

            // Construcción de la solicitud HTTP
            HttpPost post = new HttpPost("https://api.openai.com/v1/chat/completions");
            post.setHeader("Authorization", "Bearer " + API_KEY);
            post.setHeader("Content-Type", "application/json");
            post.setEntity(new StringEntity(jsonBody.toString(), "UTF-8"));

            try (CloseableHttpResponse response = client.execute(post)) {
                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode != 200) {
                    System.err.println("Error en la API: " + statusCode + " - " + response.getStatusLine().getReasonPhrase());
                    return "Error en la comunicación con la IA.";
                }

                String responseBody = EntityUtils.toString(response.getEntity(), "UTF-8");
                JsonObject jsonResponse = JsonParser.parseString(responseBody).getAsJsonObject();

                JsonArray choices = jsonResponse.getAsJsonArray("choices");
                if (choices == null || choices.size() == 0) {
                    System.err.println("Respuesta de la API no contiene opciones.");
                    return "No se recibió una respuesta válida de la IA.";
                }
                return choices.get(0).getAsJsonObject()
                        .get("message").getAsJsonObject()
                        .get("content").getAsString();
            }
        } catch (IOException | JsonSyntaxException e) {
            e.printStackTrace();
            return "Error en la comunicación con la IA.";
        }
    }
}
