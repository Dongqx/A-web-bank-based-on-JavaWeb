package com.cy.service.interfaces;

import com.cy.entity.Admin;

public interface AdminServiceInter {
	//��ȡ����Ա����
	public Admin getAdmin();
	//�޸Ĺ���Ա�˺�����
	public Boolean modifyAccount(Admin admin);
}
