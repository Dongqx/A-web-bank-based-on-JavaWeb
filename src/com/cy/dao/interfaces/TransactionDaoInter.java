package com.cy.dao.interfaces;

import java.util.List;

import com.cy.entity.Account;
import com.cy.entity.Admin;
import com.cy.entity.LoanLog;
import com.cy.entity.TransactionLog;
import com.cy.entity.TransactionType;

public interface TransactionDaoInter {

	//���ݽ����������ƻ�ȡ�������Ͷ���
	public TransactionType getTranactionType(String name);
	public LoanLog getLoanLog(int id);
	//��Transaction_log������ӽ��׼�¼
	public boolean addLog(TransactionLog log);
	public boolean addloanLog(LoanLog log);
	public boolean uploanLog(LoanLog log);
	//���ݴ���ʾҳҳ����˻������ȡ���׼�¼
	public List getLogs(Account account, int page);
	public List getLoanLogs(Account account, int page);
	public List getLogsOfC(Account account);
	//��ȡ���׼�¼��
	public Integer getCountOfLogs(Account account);
	public Integer getCountOfLoanLogs(Account account);
}
