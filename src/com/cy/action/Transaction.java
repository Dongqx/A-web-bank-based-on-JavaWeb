package com.cy.action;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.interceptor.RequestAware;
import org.apache.struts2.interceptor.SessionAware;

import com.cy.entity.Account;
import com.cy.entity.Admin;
import com.cy.entity.LoanLog;
import com.cy.entity.Pager;
import com.cy.entity.TransactionLog;
import com.cy.service.interfaces.AdminServiceInter;
import com.cy.service.interfaces.TransactionServiceInter;
import com.cy.service.interfaces.UserServiceInter;
import com.opensymphony.xwork2.ActionSupport;

public class Transaction extends ActionSupport implements RequestAware, SessionAware {

	// �������ԣ����set������ʵ������ע��
	private UserServiceInter userServiceInter;
	private TransactionServiceInter transactionServiceInter;
	private Map<String, Object> request;
	private Map<String, Object> session;
	private Double loanmoney;
	private int id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Resource
	private AdminServiceInter adminServiceInter;

	public Double getLoanmoney() {
		return loanmoney;
	}

	public void setLoanmoney(Double loanmoney) {
		this.loanmoney = loanmoney;
	}

	public UserServiceInter getUserServiceInter() {
		return userServiceInter;
	}

	public void setUserServiceInter(UserServiceInter userServiceInter) {
		this.userServiceInter = userServiceInter;
	}

	public TransactionServiceInter getTransactionServiceInter() {
		return transactionServiceInter;
	}

	public void setTransactionServiceInter(TransactionServiceInter transactionServiceInter) {
		this.transactionServiceInter = transactionServiceInter;
	}

	public Map<String, Object> getRequest() {
		return request;
	}

	public void setRequest(Map<String, Object> request) {
		this.request = request;
	}

	public Map<String, Object> getSession() {
		return session;
	}

	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	private Account account;

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	// ����TransactionLog�������get��set�������û���װҳ�������
	private TransactionLog log;

	public TransactionLog getLog() {
		return log;
	}

	public void setLog(TransactionLog log) {
		this.log = log;
	}

	// �����ҳʵ����
	private Pager pager;

	public Pager getPager() {
		return pager;
	}

	public void setPager(Pager pager) {
		this.pager = pager;
	}

	// ���
	public String deposit() {
		// ����isEnable�����ж��˻��Ƿ񶳽�
		if (isEnable()) {
			// System.out.println(log.getTr_money());
			// �����׶���log�й������˻��������Ը�ֵ
			log.setAccount(account);
			session.put("user", account);
			// ����ҵ�񷽷��������˻���account�е����
			// ���ڽ�����Ϣ��transaction_log������Ӽ�¼
			try {
				return isSuccess(transactionServiceInter.deposit(log));
			} catch (Exception e) {
				request.put("message", "��������ȷ��Ϣ��");
				return "message";
			}
		}
		return "message";
	}

	// ��ȡ��ҳ�������֤���ж��˻�����Ƿ����
	public void validateWithdraw() {
		account = (Account) session.get("user");
		// System.out.println(log.getTr_money());
		// System.out.println(account.getBalance());
		try {
			if (log.getTr_money() > account.getBalance()) {
				this.addFieldError("log.tr_money", "�����˻����㣡");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// ȡ��
	public String withdraw() {
		// ����isEnable�����ж��˻��Ƿ񶳽�
		if (isEnable()) {
			// �����׶���log�й������˻��������Ը�ֵ
			log.setAccount(account);
			session.put("user", account);
			// ����ҵ�񷽷��������˻���account�е����
			// ���ڽ�����Ϣ��transaction_log������Ӽ�¼
			try {
				return isSuccess(transactionServiceInter.withdraw(log));
			} catch (Exception e) {
				request.put("message", "��������ȷ��Ϣ��");
				return "message";
			}
		}
		return "message";
	}

	// ��ת��ҳ���������֤
	public String validateTransfer() {
		account = (Account) session.get("user");
		Account other = userServiceInter.getAccount(log.getOtherid());
		if(other!=null){
			if(other.getStatus().getName().equals("����")){
				request.put("message", "�Է��˻��ѱ����ᣬ�޷�ת�룡");
				return "message";
			} 
			if (log.getOtherid().intValue() == account.getAccountid().intValue()) {
				request.put("message", "������ת���Լ���");
				return "message";
			}
			if (userServiceInter.getAccount(log.getOtherid()) == null) {
				request.put("message", "���˻������ڣ�");
				return "message";
			}
			if (log.getTr_money() > account.getBalance()) {
				request.put("message", "�����˻����㣡");
				return "message";
			}
		} else {
			request.put("message", "���˻������ڣ�");
			return "message";
		}
		return "message";
	}

	// ת��
	public String transfer() {
		// �ж��˻��Ƿ񶳽�
		if (isEnable()) {
			// �����׶���log�й������˻��������Ը�ֵ
			Account other = userServiceInter.getAccount(log.getOtherid());
			if(other != null ){
				if(other.getStatus().getName().equals("����")){
					request.put("message", "�Է��˻��ѱ����ᣬ�޷�ת�룡");
					return "message";
				} 
				if (log.getOtherid().intValue() == account.getAccountid().intValue()) {
					request.put("message", "������ת���Լ���");
					return "message";
				}
				if (userServiceInter.getAccount(log.getOtherid()) == null) {
					request.put("message", "���˻������ڣ�");
					return "message";
				}
				if (log.getTr_money() > account.getBalance()) {
					request.put("message", "�����˻����㣡");
					return "message";
				}else {
					log.setAccount(account);
					session.put("user", account);
					// ����ҵ�񷽷��������˻���account�е����
					// ���ڽ�����Ϣ��transaction_log������Ӽ�¼
					try {
						return isSuccess(transactionServiceInter.transfer(log));
					} catch (Exception e) {
						request.put("message", "��������ȷ��Ϣ��");
						return "message";
					}
				}
			}
		}
		return "message";
	}

	// �ж��˻��Ƿ񶳽�
	public boolean isEnable() {
		// userServiceInter.reflush(account);
		account = (Account) session.get("user");
		// System.out.println(account.getUsername());
		if (account.getStatus().getName().equals("����")) {
			request.put("message", "��Ǹ�����˻��ѱ����ᣬ�޷�������ز�����<br>");
			return false;
		}
		return true;
	}
	
	//�ж϶Է��˻��Ƿ񶳽�


	public String isSuccess(boolean flag) {
		if (flag) {
			request.put("message", "�����ɹ���");
			return "message";
		}
		request.put("message", "����ʧ�ܣ�<a href='javascript:history.go(-1)'>����</a>");
		return "message";
	}

	// ��ʾ���׼�¼
	public String list() {
		account = (Account) session.get("user");
		// ��ȡ����ʾҳҳ��
		int curPage = pager.getCurPage();
		// ���ݴ���ʾҳҳ����˻������ȡ���׼�¼
		List<TransactionLog> logs = transactionServiceInter.getLogs(account, curPage);
		// ����˻��Ľ��׼�¼������������ʼ��pager����
		// ��������perPageRows��rowCount����
		pager = transactionServiceInter.getPagerOfLogs(account);
		// ����pager�����д���ʾҳҳ��
		pager.setCurPage(curPage);
		request.put("logs", logs);
		return "success";
	}

	// ��ʾ�����¼
	public String loanlist() {
		account = (Account) session.get("user");
		// ��ȡ����ʾҳҳ��
		int curPage = pager.getCurPage();
		// ���ݴ���ʾҳҳ����˻������ȡ���׼�¼
		List<LoanLog> logs = transactionServiceInter.getLoanLogs(account, curPage);
		// ����˻��Ľ��׼�¼������������ʼ��pager����
		// ��������perPageRows��rowCount����
		pager = transactionServiceInter.getPagerOfLoanLogs(account);
		// ����pager�����д���ʾҳҳ��
		pager.setCurPage(curPage);
		request.put("logs", logs);
		return "success";
	}

	// ��ʾ�����¼
	public String loanlist1() {
		account = userServiceInter.getAccount(id);
		// ��ȡ����ʾҳҳ��
		int curPage = pager.getCurPage();
		// ���ݴ���ʾҳҳ����˻������ȡ���׼�¼
		List<LoanLog> logs = transactionServiceInter.getLoanLogs(account, curPage);
		// ����˻��Ľ��׼�¼������������ʼ��pager����
		// ��������perPageRows��rowCount����
		pager = transactionServiceInter.getPagerOfLoanLogs(account);
		// ����pager�����д���ʾҳҳ��
		pager.setCurPage(curPage);
		request.put("logs", logs);
		return "success";
	}

	// ����Ȩ���ж�
	public String loan() {
		account = (Account) session.get("user");
		List<TransactionLog> logs = transactionServiceInter.getLogsOfC(account);
		Double sum = 0.0;
		int su = 0;
		for (TransactionLog log : logs) {
			if (log.getTransactionType().getId() == 1) {
				sum = sum + log.getTr_money();
				su++;
			}
		}
		Double arg = sum / su;
		session.put("arg", arg);
		return "success";
	}

	// ����
	public String loanmoney() {
		if(isEnable()){
			account = (Account) session.get("user");
			Admin a = userServiceInter.getAdmin("admin");
			if (loanmoney > a.getMoney()) {
				request.put("message", "��������ڿɴ���<a href='javascript:history.go(-1)'>����</a>");
				return "message";
			} else {
				String loanmoney2 = transactionServiceInter.loanmoney(account, a, loanmoney);
				request.put("message", loanmoney2);
				return "message";
			}
		} else {
			request.put("message", "��Ǹ�����˻��ѱ����ᣬ�޷�������ز�����<br>");
			return "message";
		}
	}

	// ����
	public String huan() {
		account = (Account) session.get("user");
		Admin a = userServiceInter.getAdmin("admin");
		String huan = transactionServiceInter.huan(account, a, id);
		request.put("message", huan + "<a href='javascript:history.go(-1)'>����</a>");
		return "message";
	}
}
