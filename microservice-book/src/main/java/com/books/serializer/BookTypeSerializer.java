package com.books.serializer;

import java.io.IOException;

import com.books.model.Book;
import com.books.model.BookType;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class BookTypeSerializer extends StdSerializer<BookType>{

	public BookTypeSerializer() {
		this(null);
	}
	
	protected BookTypeSerializer(Class<BookType> t) {
		super(t);
	}

	@Override
	public void serialize(BookType bookType, JsonGenerator jgen, SerializerProvider serializer) throws IOException {
		
		jgen.writeStartObject();
		jgen.writeNumberField("id", bookType.getId());
		jgen.writeStringField("type", bookType.getType());
		jgen.writeArrayFieldStart("books");
		for (Book book : bookType.getBooks()) {
			jgen.writeStartObject();
			jgen.writeNumberField("id", book.getId());
			jgen.writeStringField("name", book.getName());
			jgen.writeEndObject();
		}
		jgen.writeEndArray();
		jgen.writeEndObject();
	}

}
