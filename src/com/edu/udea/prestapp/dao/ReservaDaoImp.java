package com.edu.udea.prestapp.dao;

import org.hibernate.SessionFactory;

public class ReservaDaoImp {
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
}
