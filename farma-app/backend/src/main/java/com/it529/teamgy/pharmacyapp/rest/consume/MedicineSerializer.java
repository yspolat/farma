package com.it529.teamgy.pharmacyapp.rest.consume;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.it529.teamgy.pharmacyapp.rest.produce.Medicine;

import java.io.IOException;
import java.util.List;

public class MedicineSerializer extends JsonSerializer<List<Medicine>> {

    // This class and below line are needed for serialize list of medicines that comes from API, otherwise it'll throw error.
    // @JsonSerialize(using = MedicineSerializer.class)

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
