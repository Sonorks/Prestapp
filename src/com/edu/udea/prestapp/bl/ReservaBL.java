package com.edu.udea.prestapp.bl;

import org.springframework.transaction.annotation.Transactional;

import com.edu.udea.prestapp.dao.ReservaDaoImp;

@Transactional
public class ReservaBL {
	private ReservaDaoImp reservaDaoImp;

	public ReservaDaoImp getReservaDaoImp() {
		return reservaDaoImp;
	}

	public void setReservaDaoImp(ReservaDaoImp reservaDaoImp) {
		this.reservaDaoImp = reservaDaoImp;
	}
	
}
