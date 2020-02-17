package com.books.serializer;

import java.io.IOException;

import com.books.model.BookCopy;
import com.books.model.Borrowing;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class BookCopySerializer extends StdSerializer<BookCopy> {

	public BookCopySerializer() {
		this(null);
	}
	
	protected BookCopySerializer(Class<BookCopy> t) {
		super(t);
	}

	@Override
	public void serialize(BookCopy bookCopy, JsonGenerator jgen, SerializerProvider serializer) throws IOException {
	
		jgen.writeStartObject();
		jgen.writeNumberField("id", bookCopy.getId());
		jgen.writeStringField("ean", bookCopy.getEan());
		jgen.writeBooleanField("isBorrowed", bookCopy.isBorrowed());
		jgen.writeNumberField("idBook", bookCopy.getBook().getId());
		jgen.writeArrayFieldStart("borrowings");
		for (Borrowing borrowing : bookCopy.getBorrowings()) {
			jgen.writeStartObject();
			jgen.writeNumberField("id", borrowing.getId());
			jgen.writeNumberField("idUser", borrowing.getIdUser());
			jgen.writeStringField("state", borrowing.getState().stateName);
			jgen.writeEndObject();
		}
		jgen.writeEndArray();
		jgen.writeEndObject();
	}

}
