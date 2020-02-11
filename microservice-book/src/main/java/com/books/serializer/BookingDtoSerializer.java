package com.books.serializer;

import java.io.IOException;

import com.books.dto.BookingDto;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class BookingDtoSerializer extends StdSerializer<BookingDto> {

	public BookingDtoSerializer() {
		this(null);
	}
	
	public BookingDtoSerializer(Class<BookingDto> t) {
		super(t);
	}

	@Override
	public void serialize(BookingDto bookingDto, JsonGenerator jgen, SerializerProvider serializer) throws IOException {
		
		jgen.writeStartObject();
        jgen.writeNumberField("id", bookingDto.getId());
        jgen.writeNumberField("idUser", bookingDto.getIdUser());
        jgen.writeNumberField("position", bookingDto.getPosition());
        jgen.writeStringField("bookName", bookingDto.getBookName());
        jgen.writeStringField("deadline", bookingDto.getNearestDeadline());
        jgen.writeEndObject();
	}
}
