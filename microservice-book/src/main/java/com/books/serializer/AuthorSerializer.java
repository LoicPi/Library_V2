package com.books.serializer;

import java.io.IOException;

import com.books.model.Author;
import com.books.model.Book;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class AuthorSerializer extends StdSerializer<Author> {

	public AuthorSerializer() {
		this(null);
	}
	
	protected AuthorSerializer(Class<Author> t) {
		super(t);
	}

	@Override
	public void serialize(Author author, JsonGenerator jgen, SerializerProvider serializer) throws IOException {
		
		jgen.writeStartObject();
		jgen.writeNumberField("id", author.getId());
		jgen.writeStringField("lastName", author.getLastName());
		jgen.writeStringField("firstName", author.getFirstName());
		jgen.writeStringField("description", author.getDescription());
		jgen.writeStringField("image", author.getImage());
		jgen.writeArrayFieldStart("books");
		for (Book book : author.getBooks()) {
			jgen.writeStartObject();
			jgen.writeNumberField("id", book.getId());
			jgen.writeStringField("name", book.getName());
			jgen.writeEndObject();
		}
		jgen.writeEndArray();
		jgen.writeEndObject();
	}

}
