package com.books.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

@Component
public class MailBatch {
	
	final static Logger log = LogManager.getLogger();
	
	@Autowired
	private BorrowingDao borrowingDao;
	
	@Autowired
	private BookingDao bookingDao;
	
	@Autowired
	private BookingService bookingService;
	
	@Autowired
	private MicroserviceUserProxy microserviceUserProxy;
	
	@Autowired
	private MailService mailService;
	 
	@Scheduled(cron = "0 0 0 * * *")
	public void sendingLateMail() {
		
		List<Borrowing> borrowings = borrowingDao.findByStateName(State.EnRetard);
		
		List<UserBean> users = new ArrayList<UserBean>();
		
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		
		for (Borrowing borrowing : borrowings) {
			
			UserBean user = microserviceUserProxy.getUser(borrowing.getIdUser());
			
			if(users.size() > 0) {
				if ((users.contains(user))) {
					users.add(user);
				}
			} else {
				users.add(user);
			}
		}		
		
		for (UserBean userRelaunched : users) {
			
			String mailTo = userRelaunched.getEmail();
            String mailSubject = "Vos prêts à la bibliothèque sont en retard";
            String listBook = "";
            
            List<Borrowing> borrowingOfUser = borrowingDao.findByIdUserAndStates(userRelaunched.getId(), Arrays.asList(State.EnRetard));
            
            for (Borrowing borrowing : borrowingOfUser) {
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
		
		log.info("Lancement du batch d'envoi des mails de retard de prêt.");
	}
	
	/*
	 * Function Batch to cancel a booking when user don't come to borrow the book who is booking
	 * and send a mail to the next user who is booking the book in the list
	 */
	@Scheduled(cron = "*/60 * * * * *")
	public void sendBookingMail () {
		
		List<Booking> bookingList = bookingDao.findBookingBySendMailAndState(true, State.EnAttente);
		
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		
		LocalDate localDate = LocalDate.now();
			
		if(bookingList.size() > 0) {
			
			for (Booking booking : bookingList) {
				
				Date dateDeadline = DateUtils.addDays(booking.getDateMail(), 2);
				
				Date today = new Date();
				
				if(today.after(dateDeadline)) {
					
					bookingService.cancelBookingMail(booking.getId());
				
					List<Booking> bookings = bookingDao.findBookingByBookAndStateOrderByDateCreate(booking.getBook(), State.EnAttente);
				
					if ( bookings.size() > 0 ) {
					
						Booking bookingMail = bookings.get(0);

						bookingMail.setDateMail(java.sql.Date.valueOf(localDate));
					
						Book book = bookingMail.getBook();
					
						Date deadlineBooking = DateUtils.addDays(bookingMail.getDateMail(), 2);
					
						UserBean user = microserviceUserProxy.getUser(bookingMail.getIdUser());
					
						String mailTo = user.getEmail();
						String mailSubject = "Bibliothèque : Votre réservation est disponible";
						String mailText = "Bonjour " + user.getFirstName() + " " + user.getLastName() + "," +
								"\n\nNous vous informons que la réservation du document ci-dessous est disponible : " +
								"\n\n" + book.getName() +
								"\n\nVous avez jusqu'au " + dateFormat.format(deadlineBooking) + " pour venir récupérer votre document." +
								"\n\nPassée cette date, le document sera remis en circulation." +
								"\n\nCordialement," +
								"\n\nL'équipe de la Bibliothèque";
					
						mailService.sendMessage( mailTo, mailSubject, mailText );
					
						bookingMail.setSendMail(true);
					
						bookingDao.save(bookingMail);
					}
				}
			}
		}
		
		log.info("Lancement du batch pour annuler les réservations si un utilisateur n'a pas pris sa réservation à temps.");
	}
}
