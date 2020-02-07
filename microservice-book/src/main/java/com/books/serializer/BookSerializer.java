package com.books.serializer;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import com.books.model.Author;
import com.books.model.Book;
import com.books.model.BookCopy;
import com.books.model.Borrowing;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class BookSerializer extends StdSerializer<Book> {

	public BookSerializer() {
		this(null);
	}
	
	public BookSerializer(Class<Book> t) {
		super(t);
	}

	@Override
	public void serialize(Book book, JsonGenerator jgen, SerializerProvider serializer) throws IOException {
		
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		
		jgen.writeStartObject();
        jgen.writeNumberField("id", book.getId());
        jgen.writeStringField("name", book.getName());
        jgen.writeStringField("description", book.getDescription());
        jgen.writeStringField("image", book.getImagePath());
        jgen.writeArrayFieldStart("bookCopies");
        for(BookCopy bookCopies : book.getBooksCopies()) {
        	jgen.writeStartObject();
        	jgen.writeNumberField("id", bookCopies.getId());
        	jgen.writeStringField("ean", bookCopies.getEan());
        	jgen.writeBooleanField("borrowed", bookCopies.isBorrowed());
        	jgen.writeArrayFieldStart("borrowings");
        	for(Borrowing borrowings : bookCopies.getBorrowings()) {
        		jgen.writeStartObject();
        		jgen.writeStringField("deadline", dateFormat.format(borrowings.getDeadline()));
        		jgen.writeEndObject();
        	}
        	jgen.writeEndArray();
        	jgen.writeEndObject();
        }
        jgen.writeEndArray();
        jgen.writeArrayFieldStart("authors");
        for(Author authors : book.getAuthors()) {
        	jgen.writeStartObject();
        	jgen.writeNumberField("id", authors.getId());
        	jgen.writeStringField("lastName", authors.getLastName());
        	jgen.writeStringField("firstName", authors.getFirstName());
        	jgen.writeEndObject();
        }
        jgen.writeEndArray();
        jgen.writeNumberField("numberOfBooking", book.getBookings().size());
        jgen.writeNumberField("maxNumberOfBooking", book.getBooksCopies().size()*2);
        jgen.writeStringField("bookType", book.getBookType().getType());
        jgen.writeEndObject();
	}

}
