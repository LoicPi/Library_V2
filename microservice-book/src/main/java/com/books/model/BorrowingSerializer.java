package com.books.model;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class BorrowingSerializer extends StdSerializer<Borrowing> {

	public BorrowingSerializer() {
		this(null);
	}

	public BorrowingSerializer(Class<Borrowing> b) {
		super(b);
	}

	@Override
	public void serialize(Borrowing borrowing, JsonGenerator jGen, SerializerProvider serializer) throws IOException {
		
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		
		jGen.writeStartObject();
		jGen.writeNumberField("id", borrowing.getId());
		jGen.writeStringField("dateBorrowed", dateFormat.format(borrowing.getDateBorrowed()));
		jGen.writeStringField("deadline", dateFormat.format(borrowing.getDeadline()));
		jGen.writeBooleanField("renewal", borrowing.getRenewal());
		jGen.writeStringField("dateRenewal", dateFormat.format(borrowing.getDateRenewal()));
		jGen.writeStringField("dateReturn", dateFormat.format(borrowing.getDateReturn()));
		jGen.writeNumberField("idUser", borrowing.getIdUser());
		jGen.writeStringField("state", borrowing.getState().stateName);
		jGen.writeStringField("bookName", borrowing.getBookCopy().getBook().getName());	
		jGen.writeEndObject();		
	}
	
	
}
