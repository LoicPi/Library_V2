package com.books.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.books.dao.BookingDao;
import com.books.model.Booking;

@SpringBootTest
public class BookingServiceTest {
	
	@Autowired
	private BookingService bookingService;

	@MockBean
	private BookingDao bookingDaoMock;
	
	private Booking booking = new Booking();
	
	@BeforeEach
	public void initBookingServiceTest() {		
		booking.setId(1);
		
		Mockito.when(bookingDaoMock.findById(1)).thenReturn(Optional.of(booking));
	}
	
	@AfterEach
	public void setBookingServiceTest() {
		bookingDaoMock.delete(booking);
	}
	
	@Test
	public void endBooking_Test() {
		bookingService.endBooking(1);
		
		assertThat(booking.getState().stateName).isEqualTo("Réservation terminé");		
	}
	
	@Test
	public void cancelBookingUser_Test() {
		Booking bookingCancel = bookingService.cancelBookingUser(1);
		
		assertThat(bookingCancel.getState().stateName).isEqualTo("Réservation annulée utilisateur");
	}
	
	@Test
	public void cancelBookingMail_Test() {
		bookingService.cancelBookingMail(1);
		
		assertThat(booking.getState().stateName).isEqualTo("Réservation annulé mail");
	}
}
