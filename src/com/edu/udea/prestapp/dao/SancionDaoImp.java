package com.edu.udea.prestapp.dao;

import org.hibernate.SessionFactory;

public class SancionDaoImp {
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
}
