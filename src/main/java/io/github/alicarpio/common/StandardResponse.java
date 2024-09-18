package io.github.alicarpio.common;

import com.google.gson.JsonElement;
import io.github.alicarpio.domain.enums.StatusResponse;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class StandardResponse {
    private StatusResponse status;
    private JsonElement data;
    private String message;

    public StandardResponse(StatusResponse status, JsonElement data){
        this.status = status;
        this.data = data;
    }

    public StandardResponse(StatusResponse status, String message){
        this.status = status;
        this.message = message;
    }
}
