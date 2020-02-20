package com.books.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.books.dao.BookCopyDao;
import com.books.dao.BookingDao;
import com.books.dao.BorrowingDao;
import com.books.model.Book;
import com.books.model.BookCopy;
import com.books.model.Booking;
import com.books.model.Borrowing;
import com.books.model.State;

@SpringBootTest
public class BookServiceTest {

	@Autowired
	private BookService bookService;
	
	private List<Booking> bookings = new ArrayList<Booking>();
	
	private List<BookCopy> bookCopies = new ArrayList<BookCopy>();
	
	private List<Borrowing> borrowings = new ArrayList<Borrowing>();
	
	@MockBean
	private BookCopyDao bookCopyDaoMock;
	
	@MockBean
	private BookingDao bookingDaoMock;
	
	@MockBean
	private BorrowingDao borrowingDaoMock;
	
	@MockBean
	private Booking bookingMock;
	
	@Test
	public void nearestDeadlineBook_Test() {
		Book book = new Book();
		
		book.setId(1);
		
		BookCopy bookCopy = new BookCopy();
		
		Borrowing borrowing = new Borrowing();
		
		LocalDate ld = LocalDate.of( 2020 , 02 , 20 ) ;
		
		borrowing.setDeadline(java.sql.Date.valueOf(ld));
		
		borrowings.add(borrowing);
		
		bookCopy.setBorrowings(borrowings);
		
		bookCopies.add(bookCopy);
		
		Mockito.when(bookCopyDaoMock.findBookCopyByBook_Id(book.getId())).thenReturn(bookCopies);
		
		Mockito.when(borrowingDaoMock.findByBookCopyAndStates(bookCopy, Arrays.asList(State.EnCours, State.Renouvele, State.EnRetard))).thenReturn(borrowings);
		
		bookService.nearestDeadlineBook(book);
		
		assertThat(book.getNearestDeadline()).isEqualTo("20/02/2020");
	}
	
	@Test
	public void nearestDeadlineBook_NoNearestDeadline_Test() {
		Book book = new Book();
		
		book.setId(1);
		
		BookCopy bookCopy = new BookCopy();
		
		bookCopy.setBorrowings(borrowings);
		
		bookCopies.add(bookCopy);
		
		Mockito.when(bookCopyDaoMock.findBookCopyByBook_Id(book.getId())).thenReturn(bookCopies);
		
		Mockito.when(borrowingDaoMock.findByBookCopyAndStates(bookCopy, Arrays.asList(State.EnCours, State.Renouvele, State.EnRetard))).thenReturn(borrowings);
		
		bookService.nearestDeadlineBook(book);
		
		assertThat(book.getNearestDeadline()).isEqualTo("01/01/2001");
	}
	
	
	@Test
	public void numberOfBooking_Test() {
		Book book = new Book();
		
		bookings.add(bookingMock);
		
		Mockito.when(bookingDaoMock.findBookingByBookAndStateOrderByDateCreate(book, State.EnAttente)).thenReturn(bookings);
		
		bookService.numberOfBooking(book);
		
		assertThat(book.getNumberOfBooking()).isEqualTo(1);
		
	}
	
	@Test
	public void bookingBook_True_Test() {
		Book book = new Book();
		
		bookings.add(bookingMock);
		
		Mockito.when(bookingDaoMock.findBookingByBookAndStateOrderByDateCreate(book, State.EnAttente)).thenReturn(bookings);
		
		bookService.bookingBook(book);
		
		assertThat(book.getIsBooking()).isEqualTo(true);
	}
	
	@Test
	public void bookingBook_False_Test() {
		Book book = new Book();
		
		Mockito.when(bookingDaoMock.findBookingByBookAndStateOrderByDateCreate(book, State.EnAttente)).thenReturn(bookings);
		
		bookService.bookingBook(book);
		
		assertThat(book.getIsBooking()).isEqualTo(false);
	}
}
