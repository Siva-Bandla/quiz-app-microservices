package com.kirthy.question_service.masking;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class MaskingSerializer extends JsonSerializer<String> {

    @Override
    public void serialize(String value, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if (value == null || value.length() < 4){
            jsonGenerator.writeString("****");
            System.out.println("MaskingSerializer called for: " + value);
            return;
        }
        String masked = value.replaceAll(".(?=.{4})", "*");
        jsonGenerator.writeString(masked);
        System.out.println("MaskingSerializer called for: " + value);
    }
}
