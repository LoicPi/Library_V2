package com.books.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.books.beans.UserBean;
import com.books.dao.BookingDao;
import com.books.dao.BorrowingDao;
import com.books.model.Book;
import com.books.model.Booking;
import com.books.model.Borrowing;
import com.books.model.State;
import com.books.proxies.MicroserviceUserProxy;
import com.books.web.controller.BookingController;

@Component
public class MailBatch {
	
	@Autowired
	private BorrowingDao borrowingDao;
	
	@Autowired
	private BookingDao bookingDao;
	
	@Autowired
	private BookingController bookingController;
	
	@Autowired
	private MicroserviceUserProxy microserviceUserProxy;
	
	@Autowired
	private MailService mailService;
	
	@Scheduled(cron = "*/60 * * * * *")
	public void sendingLateMail() {
		
		List<Borrowing> borrowings = borrowingDao.findByStateName(State.EnRetard);
		
		List<UserBean> users = new ArrayList<UserBean>();
		
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		
		for (Borrowing borrowing : borrowings) {
			
			UserBean user = microserviceUserProxy.getUser(borrowing.getIdUser());
			
			List<Borrowing> borrowingsUser = user.getBorrowings();
			
			borrowingsUser.add(borrowing);
			
			user.setBorrowings(borrowingsUser);
			
			if (!(users.contains(user))) {
				users.add(user);
			}
		}		
		
		for (UserBean userRelaunched : users) {
			
			String mailTo = userRelaunched.getEmail();
            String mailSubject = "Vos prêts à la bibliothèque sont en retard";
            String listBook = "";
            
            for (Borrowing borrowing : userRelaunched.getBorrowings()) {
				listBook = listBook + "\n\n- " + borrowing.getBookCopy().getBook().getName() +
						 " Date de prêt : " + dateFormat.format(borrowing.getDateBorrowed()) +
						 " "
						 + "Date de fin de prêt : " + dateFormat.format(borrowing.getDeadline());
			}
            
            String mailText = "Bonjour " + userRelaunched.getFirstName() + " " + userRelaunched.getLastName() + "," +
                    "\n\nVotre réservation  pour les livres suivants sont arrivées à échéance :" 
            		+ listBook +  
                    "\n\nVous êtes priés de les rapporter très rapidement car d'autres personnes les attendent peut-être." + 
                    "\n\nNous vous remercions de votre compréhension." + 
                    "\n\nCordialement," +
                    "\n\nL'équipe de la Bibliothèque";
            mailService.sendMessage( mailTo, mailSubject, mailText );
		}	
	}
	
	@Scheduled(cron = "*/60 * * * * *")
	public void sendBookingMail () {
		
		List<Booking> bookingList = bookingDao.findBookingBySendMail(true);
		
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			
		for (Booking booking : bookingList) {
			
			Date dateDeadline = DateUtils.addDays(booking.getDateMail(), 2);
			
			if(booking.getDateMail().after(dateDeadline)) {
				
				Integer bookId = booking.getBook().getId();
				
				bookingController.delBooking(booking.getId());
				
				Booking bookingMail = bookingDao.findByBookAndPosition(bookId, 1);
				
				Book book = bookingMail.getBook();
				
				Date deadlineBooking = DateUtils.addDays(bookingMail.getDateMail(), 2);
				
				UserBean user = microserviceUserProxy.getUser(bookingMail.getIdUser());
				
				String mailTo = user.getEmail();
				String mailSubject = "Bibliothèque : Votre réservation est disponible";
				String mailText = "Bonjour " + user.getFirstName() + " " + user.getLastName() + "," +
						"\n\nNous vous informons que la réservation du document ci-dessous est disponible : " +
						"\n\n" + book.getName() +
						"\n\nVous avez jusqu'au " + dateFormat.format(deadlineBooking) + " pour venir récupérer votre document." +
						"\n\nPassé cette date, le document sera remis en circulation." +
						"\n\nCordialement," +
	                    "\n\nL'équipe de la Bibliothèque";
				
				mailService.sendMessage( mailTo, mailSubject, mailText );
			}
		}
		
		
		
	}

}
