package com.cy.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.cy.dao.interfaces.AdminDaoInter;
import com.cy.dao.interfaces.TransactionDaoInter;
import com.cy.dao.interfaces.UserDaoInter;
import com.cy.entity.Account;
import com.cy.entity.Admin;
import com.cy.entity.LoanLog;
import com.cy.entity.Pager;
import com.cy.entity.TransactionLog;
import com.cy.entity.TransactionType;
import com.cy.service.interfaces.TransactionServiceInter;

import antlr.Parser;

@Transactional
public class TransactionServiceImpl implements TransactionServiceInter {

	private TransactionDaoInter transactionDaoInter;
	private UserDaoInter userDaoInter;
	AdminDaoInter adminDaoInter;
	
	public void setAdminDaoInter(AdminDaoInter adminDaoInter) {
		this.adminDaoInter = adminDaoInter;
	}
	public void setTransactionDaoInter(TransactionDaoInter transactionDaoInter) {
		this.transactionDaoInter = transactionDaoInter;
	}
	public void setUserDaoInter(UserDaoInter userDaoInter) {
		this.userDaoInter = userDaoInter;
	}
	//���
//	@Override
	public boolean deposit(TransactionLog log) {
		//�ӽ��׶���log�л�ȡ�˻�����
        Account self = log.getAccount();
        //���˻�����������  
        //ʹ��log.getTr_money()���Ի�ȡ��������Ĵ���ԭ���Ƿ�װTransaction����ʵ��action���ձ�����
        self.setBalance(log.getTr_money()+log.getAccount().getBalance());
        //�����˻����޸����
        userDaoInter.updateAccount(self);
        //���ݽ������ͻ�ȡ���׶���
        TransactionType type = transactionDaoInter.getTranactionType("���");
        log.setTransactionType(type);
        log.setOtherid(self.getAccountid());
        log.setDatetime(log.getDatetime());
		//���ýӿ��е�addlog��������transaction_log����ӽ��׼�¼
        return transactionDaoInter.addLog(log);
	}
	//ȡ��
//	@Override
	public boolean withdraw(TransactionLog log) {
		//�ӽ��׶���log�л�ȡ�˻�����
		Account self = log.getAccount();
		 //���˻�����������  
        //ʹ��log.getTr_money()���Ի�ȡ��������Ĵ���ԭ���Ƿ�װTransaction����ʵ��action���ձ�����
		self.setBalance(log.getAccount().getBalance()-log.getTr_money());
        //�����˻����޸����
		userDaoInter.updateAccount(self);
        //���ݽ������ͻ�ȡ���׶���
		TransactionType type = transactionDaoInter.getTranactionType("ȡ��");
		log.setTransactionType(type);
		log.setOtherid(self.getAccountid());
		log.setDatetime(log.getDatetime());
		//���ýӿ��е�addlog��������transaction_log����ӽ��׼�¼
		return transactionDaoInter.addLog(log);
	}
	//ת��
//	@Override
	@Transactional
	public boolean transfer(TransactionLog log) {
		//��ȡ�Է��˻�����
		Account other = userDaoInter.getAccount(log.getOtherid());
        //��ȡ�Լ��˻�����		
		Account self = log.getAccount();
		if(other!=null) {
			//�޸ĶԷ��˻����
			other.setBalance(other.getBalance()+log.getTr_money());
			//�޸��Լ��˻����
			self.setBalance(self.getBalance()-log.getTr_money());
			//���¶Է����Լ��˻������
			userDaoInter.updateAccount(other);
			userDaoInter.updateAccount(self);
			//���ݽ������ͻ�ȡ���׶���
			TransactionType type = transactionDaoInter.getTranactionType("ת��");
			log.setTransactionType(type);
			log.setOtherid(log.getOtherid());
			log.setDatetime(log.getDatetime());
			//���transaction_log����ӽ��׼�¼
			return transactionDaoInter.addLog(log);
		}
		return false;
	}
	
//	@Override
	//��ȡ���׼�¼
	public List getLogs(Account account, int page) {
		return transactionDaoInter.getLogs(account, page);
	}
	public List getLoanLogs(Account account, int page) {
		return transactionDaoInter.getLoanLogs(account, page);
	}
	//��ȡ���׼�¼
	public List getLogsOfC(Account account) {
		return transactionDaoInter.getLogsOfC(account);
	}
	public String loanmoney(Account account,Admin a,Double money) {
		Double mon = a.getMoney()-money;
		a.setMoney(mon);
		boolean updateAdminPassword = adminDaoInter.updateAdminPassword(a);
		Double mon1 = account.getBalance() + money;
		account.setBalance(mon1);
		boolean updateAccount = userDaoInter.updateAccount(account);
		if(updateAdminPassword && updateAccount) {
			LoanLog log = new LoanLog();
			log.setAccount(account);
			log.setLoan_money(money);
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//�������ڸ�ʽ
	        String format = df.format(new Date());// new Date()Ϊ��ȡ��ǰϵͳʱ��
			log.setDatetime(format);
			log.setHuan_money(money);
			transactionDaoInter.addloanLog(log);
			return "����ɹ�";
		}else {
			return "����ʧ��";
		}
	}
	public String huan(Account account,Admin a,int id) {
		LoanLog loanLog = transactionDaoInter.getLoanLog(id);
		if(loanLog != null) {
			Double huan_money = loanLog.getHuan_money();
			Double balance = account.getBalance();
			if(balance < huan_money) {
				return "���㣬���ܻ���";
			}else {
				Double mon = a.getMoney()+huan_money;
				a.setMoney(mon);
				boolean updateAdminPassword = adminDaoInter.updateAdminPassword(a);
				Double mon1 = account.getBalance() - huan_money;
				account.setBalance(mon1);
				boolean updateAccount = userDaoInter.updateAccount(account);
				if(updateAdminPassword && updateAccount) {
					loanLog.setHuan_money(0.0);
					boolean uploanLog = transactionDaoInter.uploanLog(loanLog);
					return "����ɹ�";
				}else {
					return "����ʧ��";
				}
			}
		} else {
			return "����ʧ��";
		}
	}
//	@Override
	//����˻��Ľ��׼�¼������������ʼ����ҳ��Pager����
	//��������perPagerRows��rowCount����
	public Pager getPagerOfLogs(Account account) {
		//�ӱ�Transaction_Log�л�ȡ���˻���صĽ��׼�¼��
		int count = transactionDaoInter.getCountOfLogs(account);
		//
		Pager pager = new Pager();
		//����pager������PerPageRows���ԣ���ʾÿҳ��ʾ10����¼
		pager.setPerPageRows(8);
		//����rowCount���ԣ���ʾ��¼����
		pager.setRowCount(count);
		double parseDouble = Double.parseDouble(Integer.toString(count));
		Double a = Math.ceil(parseDouble/8.00);
		pager.setPageCount(a.intValue());
		return pager;
	}
	
	public Pager getPagerOfLoanLogs(Account account) {
		//�ӱ�Transaction_Log�л�ȡ���˻���صĽ��׼�¼��
		int count = transactionDaoInter.getCountOfLoanLogs(account);
		//
		Pager pager = new Pager();
		//����pager������PerPageRows���ԣ���ʾÿҳ��ʾ8����¼
		pager.setPerPageRows(8);
		//����rowCount���ԣ���ʾ��¼����
		pager.setRowCount(count);
		double parseDouble = Double.parseDouble(Integer.toString(count));
		Double a = Math.ceil(parseDouble/8.00);
		pager.setPageCount(a.intValue());
		return pager;
	}
}
