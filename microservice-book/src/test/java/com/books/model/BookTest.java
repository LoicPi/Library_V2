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
public class BookTest {

	private Book bookTest = new Book();
	
	private Book bookTest2 = new Book();
	
	private List<BookCopy> bookCopies = new ArrayList<BookCopy>();
	
	private List<BookCopy> bookCopies2 = new ArrayList<BookCopy>();
	
	@BeforeEach
	public void initBookCopy() {
		for (int i = 0; i < 1; i++) {
			BookCopy bookCopyMock = Mockito.mock(BookCopy.class);
			Mockito.when(bookCopyMock.isBorrowed()).thenReturn(false);
			bookCopies.add(bookCopyMock);
		}
		
		bookTest.setBooksCopies(bookCopies);
		
		for (int i = 0; i < 1; i++) {
			BookCopy bookCopyM= Mockito.mock(BookCopy.class);
			Mockito.when(bookCopyM.isBorrowed()).thenReturn(true);
			bookCopies2.add(bookCopyM);
		}
		
		bookTest2.setBooksCopies(bookCopies2);
	}
	
	@AfterEach
	public void undefBookCopy() {
		bookCopies.clear();
		bookCopies2.clear();
	}
	
	@Test
	public void avaibleBook_ReturnTrue_Test() {
		assertThat(bookTest.avaibleBook()).isTrue();
	}
	
	@Test
	public void avaibleBook_ReturnFalse_Test() {
		assertThat(bookTest2.avaibleBook()).isFalse();
	}
}
