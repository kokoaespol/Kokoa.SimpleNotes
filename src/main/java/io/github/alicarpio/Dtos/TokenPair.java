package io.github.alicarpio.Dtos;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class TokenPair {
    private final String accessToken;
    private final String refreshToken;

    public JsonElement toJsonElement() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("accessToken", accessToken);
        jsonObject.addProperty("refreshToken", refreshToken);
        return jsonObject;
    }

    public String toJsonString() {
        return toJsonElement().toString();
    }
}
