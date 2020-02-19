package com.books.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class BookCopyTest {

	private BookCopy bookCopyTest = new BookCopy();
	
	private BookCopy bookCopyTest2 = new BookCopy();
	
	private List<Borrowing> borrowings = new ArrayList<Borrowing>();
	
	private List<Borrowing> borrowings2 = new ArrayList<Borrowing>();
	
	@BeforeEach
	public void initBorrowing() {
		for (int i = 0; i < 1; i++) {
			Borrowing borrowingMock = Mockito.mock(Borrowing.class);
			Mockito.when(borrowingMock.getState()).thenReturn(State.EnCours);
			borrowings.add(borrowingMock);
		}
		
		bookCopyTest.setBorrowings(borrowings);
		
		for(int i = 0; i < 1; i++) {
			Borrowing borrowingM = Mockito.mock(Borrowing.class);
			Mockito.when(borrowingM.getState()).thenReturn(State.Termine);
			borrowings2.add(borrowingM);
		}
		
		bookCopyTest2.setBorrowings(borrowings2);
	}
	
	@AfterEach
	public void undefBorrowing() {
		borrowings.clear();
		borrowings2.clear();
	}
	
	@Test
	public void isBorrowed_ReturnTrue_Test () {
		assertThat(bookCopyTest.isBorrowed()).isEqualTo(true);
	}
	
	@Test
	public void isBorrowed_ReturnFalse_Test() {
		assertThat(bookCopyTest2.isBorrowed()).isEqualTo(false);
	}
}
