package com.cy.dao.interfaces;

import com.cy.entity.Admin;

public interface AdminDaoInter {
	//��ȡ����Ա����
	public Admin getAdmin();
	//�޸��˻�����
	public boolean updateAdminPassword(Admin admin);
}
