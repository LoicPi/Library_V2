package com.users.model;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class UserSerializer extends StdSerializer<User> {

	public UserSerializer() {
		this(null);
	}
	
	public UserSerializer(Class<User> t) {
		super(t);
	}

	@Override
	public void serialize(User user, JsonGenerator jGen, SerializerProvider provider) throws IOException {
		
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		
		jGen.writeStartObject();
		jGen.writeNumberField("id", user.getId());
		jGen.writeStringField("lastName", user.getLastName());
		jGen.writeStringField("firstName", user.getFirstName());
		jGen.writeStringField("email", user.getEmail());
		jGen.writeNumberField("cardNumber", user.getCardNumber());
		jGen.writeNumberField("phoneNumber", user.getPhoneNumber());
		jGen.writeStringField("dateRegistration", dateFormat.format(user.getDateRegistration()));
		jGen.writeEndObject();
	}
}
