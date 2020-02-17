package com.books.serializer;

import java.io.IOException;

import com.books.model.Booking;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class BookingSerializer extends StdSerializer<Booking> {

	public BookingSerializer() {
		this(null);
	}
	
	public BookingSerializer(Class<Booking> t) {
		super(t);
	}

	@Override
	public void serialize(Booking booking, JsonGenerator jgen, SerializerProvider serializer) throws IOException {
		
		jgen.writeStartObject();
        jgen.writeNumberField("id", booking.getId());
        jgen.writeNumberField("idUser", booking.getIdUser());
        jgen.writeNumberField("idBook", booking.getBook().getId());
        jgen.writeStringField("bookName", booking.getBook().getName());
        jgen.writeStringField("state", booking.getState().stateName);
        jgen.writeEndObject();
	}
	
}
