package com.it529.teamgy.pharmacyapp.rest.consume;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.it529.teamgy.pharmacyapp.rest.Medicine;
import org.springframework.ui.Model;

import java.io.IOException;
import java.util.List;

public class ModelSerializer extends JsonSerializer<List<Medicine>> {

    @Override
    public void serialize(List<Medicine> value, JsonGenerator jgen,
                          SerializerProvider provider) throws IOException,
            JsonProcessingException {
        jgen.writeStartArray();
        for (Medicine medicine : value) {
            jgen.writeStartObject();
            jgen.writeObjectField("medicines", medicine);
            jgen.writeEndObject();
        }
        jgen.writeEndArray();
    }
}
