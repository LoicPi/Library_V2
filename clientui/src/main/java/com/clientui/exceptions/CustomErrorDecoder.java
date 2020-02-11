package com.clientui.exceptions;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

import feign.Response;
import feign.codec.ErrorDecoder;

public class CustomErrorDecoder implements ErrorDecoder{
	
	private final ErrorDecoder defaultErrorDecoder = new Default();

	@Override
	public Exception decode(String methodKey, Response response) {
		
		ObjectMapper mapper = new ObjectMapper();
		
		try {
			ApiError error = mapper.readValue(response.body().asInputStream(), ApiError.class);
			switch (error.getMessage()) {
				case "UserException01":
					return new CanNotAddUserException("");
				case "UserException02":
					return new UserNotFoundException("L'utilisateur n'existe pas.");
				case"UserException03":
					return new PasswordDoesNotMatchException("Le mot de passe associé à cet email n'est pas bon.");
				case"Borrowing01":
					return new BorrowingInvalidRenewalException("L'emprunt a déjà été renouvelé. Vous ne pouvez pas renouveler plus d'une fois un emprunt.");
				case"BookingException01":
					return new CanNotAddBookingException("Vous avez déjà réservé ce livre.");
				case"BookingException02":
					return new CanNotAddBookingException("Vous avez déjà ce livre en emprunt actuellement.");
				case"BookingException03":
					return new CanNotAddBookingException("La capacité de réservation de ce livre est au maximum.");
				case"BookingException04":
					return new CanNotAddBookingException("Impossible d'ajouter la réservation. Veuillez réessayer ultérieurement.");	
				default: return defaultErrorDecoder.decode(methodKey, response);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return defaultErrorDecoder.decode(methodKey, response);
	}
}
