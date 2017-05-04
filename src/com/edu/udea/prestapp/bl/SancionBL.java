package com.edu.udea.prestapp.bl;

import org.springframework.transaction.annotation.Transactional;

import com.edu.udea.prestapp.dao.SancionDaoImp;

@Transactional
public class SancionBL {
	private SancionDaoImp sancionDaoImp;

	public SancionDaoImp getSancionDaoImp() {
		return sancionDaoImp;
	}

	public void setSancionDaoImp(SancionDaoImp sancionDaoImp) {
		this.sancionDaoImp = sancionDaoImp;
	}
	
	
}
