package com.mod.ia;

    import com.google.gson.JsonArray;
    import com.google.gson.JsonObject;
    import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import okhttp3.*;

    import java.io.IOException;
    import java.util.concurrent.TimeUnit;

    public class AIResponseHandler {
        private static final String API_KEY = "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"; // Usa una variable de entorno

        public static String getAIResponse(String input) {
            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .writeTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .build();

            // Construcción del JSON

            JsonObject jsonBody = new JsonObject();
            jsonBody.addProperty("model", "gpt-3.5-turbo");
            
            JsonArray messages = new JsonArray();
            JsonObject userMessage = new JsonObject();
            userMessage.addProperty("role", "user");
            userMessage.addProperty("content", input);
            messages.add(userMessage);
            jsonBody.add("messages", messages);

            // Construcción de la solicitud
            RequestBody body = RequestBody.create(
                    MediaType.parse("application/json"), jsonBody.toString()
            );

            Request request = new Request.Builder()
                    .url("https://api.openai.com/v1/chat/completions")
                    .addHeader("Authorization", "Bearer " + API_KEY)
                    .post(body)
                    .build();

            try {
                Response response = client.newCall(request).execute();
                if (!response.isSuccessful()) {
                    System.err.println("Error en la API: " + response.code() + " - " + response.message());
                    return "Error en la comunicación con la IA.";
                }
                if (response.body() == null) {
                    System.err.println("Respuesta vacía de la API.");
                    return "Error al obtener respuesta de la IA.";
                }

                // Parseo de la respuesta JSON
                JsonParser parser = new JsonParser();
                JsonObject jsonResponse = parser.parse(response.body().string()).getAsJsonObject();
                JsonArray choices = jsonResponse.getAsJsonArray("choices");
                if (choices == null || choices.size() == 0) {
                    System.err.println("Respuesta de la API no contiene opciones.");
                    return "No se recibió una respuesta válida de la IA.";
                }
                return choices.get(0).getAsJsonObject()
                        .get("message").getAsJsonObject()
                        .get("content").getAsString();
            } catch (IOException | JsonSyntaxException e) {
                e.printStackTrace();
                return "Error en la comunicación con la IA.";
            }
        }
    }
