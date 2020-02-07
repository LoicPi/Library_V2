package com.books.service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

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
	
	@Autowired
	private BorrowingDao borrowingDao;
	
	@Scheduled(cron = "*/30 * * * * *")
	public void BorrowingStatus() {
		List<Borrowing> borrowings = borrowingDao.findByStates(new Date(), Arrays.asList(State.EnCours, State.Renouvele));
		
		for (Borrowing borrowing : borrowings) {
			borrowing.setState(State.EnRetard);
			borrowingDao.save(borrowing);
		}
		
		System.out.println("lancement du batch");
		
	}

}
