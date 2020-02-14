package com.books.service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.books.dao.BorrowingDao;
import com.books.model.Borrowing;
import com.books.model.State;

//#Issues 2 Correction de bug :
//Il ne doit pas être possible pour l’usager de prolonger un prêt si la date de fin de prêt est dépassée.
//Création d'un batch passant les emprunts dépassant la date du jour au statut en retard
@Component
public class BorrowingBatch {
	
	Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private BorrowingDao borrowingDao;
	
	@Scheduled(cron = "0 0 0 * * *")
	public void BorrowingStatus() {
		List<Borrowing> borrowings = borrowingDao.findByStates(new Date(), Arrays.asList(State.EnCours, State.Renouvele));
		
		for (Borrowing borrowing : borrowings) {
			borrowing.setState(State.EnRetard);
			borrowingDao.save(borrowing);
		}

		log.info("Lancement du batch de passage des livres en retard au statut en retard.");		
	}

}
