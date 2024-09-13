package io.github.alicarpio.common;

import com.google.gson.JsonElement;
import io.github.alicarpio.enums.StatusResponse;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class StandardResponse {
    private StatusResponse status;
    private String message;
    private JsonElement data;

    public StandardResponse(StatusResponse status, String message){
        this.status = status;
        this.message = message;
    }

    public StandardResponse(StatusResponse status, JsonElement data){
        this.status = status;
        this.data = data;
    }
}
