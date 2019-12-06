package com.books.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.books.beans.UserBean;
import com.books.dao.BorrowingDao;
import com.books.model.Borrowing;
import com.books.model.State;
import com.books.proxies.MicroserviceUserProxy;

@Component
public class MailBatch {
	
	@Autowired
	private BorrowingDao borrowingDao;
	
	@Autowired
	private MicroserviceUserProxy microserviceUserProxy;
	
	@Autowired
	private MailService mailService;
	
	@Scheduled(cron = "*/60 * * * * *")
	public void sendingLateMail() {
		
		List<Borrowing> borrowings = borrowingDao.findByStateName(State.EnRetard);
		
		List<UserBean> users = new ArrayList<UserBean>();
		
		
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
						 " Date de prêt : " + borrowing.getDateBorrowed() +
						 " "
						 + "Date de fin de prêt : " + borrowing.getDeadline();
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

}
