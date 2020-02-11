package com.books.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.books.dao.BookCopyDao;
import com.books.dao.BorrowingDao;
import com.books.model.Book;
import com.books.model.BookCopy;
import com.books.model.Borrowing;
import com.books.model.State;

@Service
public class BookService {

	@Autowired
	private BookCopyDao bookCopyDao;
	
	@Autowired
	private BorrowingDao borrowingDao;
	
	public void nearestDeadlineBook (Book book) {
		
		List<Date> dates = new ArrayList<Date>();
		
		List<BookCopy> bookCopies = bookCopyDao.findBookCopyByBook_Id(book.getId());
		
		for (BookCopy bookCopy : bookCopies) {
			List<Borrowing> borrowings = borrowingDao.findByBookCopyAndStates(bookCopy, Arrays.asList(State.EnCours, State.Renouvele, State.EnRetard));
			if(borrowings.size() > 0) {
				Borrowing borrowing = borrowings.get(0);
				dates.add(borrowing.getDeadline());				
			}
		}
		
		Collections.sort(dates);
		
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		
		if (dates.size() > 0) {
			Date nearestDeadline = dates.get(0);
			
			book.setNearestDeadline(dateFormat.format(nearestDeadline));
		} else {
			LocalDate ld = LocalDate.of( 2001 , 01 , 01 ) ;
			book.setNearestDeadline(dateFormat.format(java.sql.Date.valueOf(ld)));
		}
	}
}
