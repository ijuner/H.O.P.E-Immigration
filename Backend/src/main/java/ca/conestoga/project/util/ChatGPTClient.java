package ca.conestoga.project.util;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ChatGPTClient {
    private static final String API_URL = "https://api.openai.com/v1/chat/completions";

    public static List<String> getContent(String prompt) {
        List<String> list = new ArrayList<>();

        try {
            OkHttpClient client = new OkHttpClient();
            String model = "gpt-3.5-turbo-0125";
            String apiKey = "api-key";
            String bodyJson = "{\"model\": \"" + model + "\", \"messages\": [{\"role\": \"user\", \"content\": \"" + prompt + "\"}]}";
            MediaType mediaType = MediaType.parse("application/json");
            RequestBody body = RequestBody.create(mediaType, bodyJson);
            Request request = new Request.Builder()
                    .url(API_URL)
                    .post(body)
                    .addHeader("Authorization", "Bearer " + apiKey)
                    .addHeader("Content-Type", "application/json")
                    .build();
            Response response = client.newCall(request).execute();
            String responseBody = response.body().string();
            ObjectMapper mapper = new ObjectMapper();
            JsonNode node = mapper.readTree(responseBody);
            if (node.has("choices")) {
                JsonNode choices = node.get("choices");
                if (choices.isArray()) {
                    JsonNode choice = choices.get(0);
                    if (choice.has("message")) {
                        JsonNode message = choice.get("message");
                        if (message.has("content")) {
                            String content = message.get("content").asText();
                            list.addAll(Arrays.asList(content.split("\n\n")));
                        }
                    }
                }
            }
        }catch (Exception ex) {
            ex.printStackTrace(System.out);
        }
        return list;
    }
}
