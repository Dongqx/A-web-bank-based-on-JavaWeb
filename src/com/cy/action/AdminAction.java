package com.cy.action;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;
import javax.net.ssl.SSLException;

import org.apache.struts2.interceptor.RequestAware;
import org.apache.struts2.interceptor.SessionAware;

import com.alibaba.fastjson.JSONObject;
import com.cy.entity.Account;
import com.cy.entity.Admin;
import com.cy.entity.Personinfo;
import com.cy.entity.Status;
import com.cy.service.interfaces.AdminServiceInter;
import com.cy.service.interfaces.PersoninfoServiceInter;
import com.cy.service.interfaces.UserServiceInter;
import com.opensymphony.xwork2.ActionSupport;

import Decoder.BASE64Decoder;

public class AdminAction extends ActionSupport implements RequestAware,SessionAware {

	//ʹ��Resourceע��ע��UserServiceInter��PersoninfoServiceInter���ԣ�ʡȥset����
	@Resource 
	private UserServiceInter userServiceInter;
	@Resource 
	private AdminServiceInter adminServiceInter;
	@Resource 
	private PersoninfoServiceInter personinfoServiceInter; 
	Map<String, Object> request;
	Map<String, Object> session;
	public void setRequest(Map<String, Object> request) {
		this.request = request;
	}
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}
	private String oldpwd;
	private String newpwd;
	private Double money;
	private String imgString;
	
	
	
	public String getImgString() {
		return imgString;
	}
	public void setImgString(String imgString) {
		this.imgString = imgString;
	}
	public String getOldpwd() {
		return oldpwd;
	}
	public void setOldpwd(String oldpwd) {
		this.oldpwd = oldpwd;
	}
	public String getNewpwd() {
		return newpwd;
	}
	public void setNewpwd(String newpwd) {
		this.newpwd = newpwd;
	}
	public Double getMoney() {
		return money;
	}
	public void setMoney(Double money) {
		this.money = money;
	}
	//����Admin�������ڷ�װ����Ա��¼��ҳ��
	private Admin admin;
	public Admin getAdmin() {
		return admin;
	}
	public void setAdmin(Admin admin) {
		this.admin = admin;
	}
	//����status����listUsers()����ʹ��
	private Status status;
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	//����personinfo����search()����ʹ��
	private Personinfo personinfo;
	public Personinfo getPersoninfo() {
		return personinfo;
	}
	public void setPersoninfo(Personinfo personinfo) {
		this.personinfo = personinfo;
	}
	
	private int id;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	private Account account;
	public Account getAccount() {
		return account;
	}
	public void setAccount(Account account) {
		this.account = account;
	}
	//�Ե�¼��ҳ�������֤���û����������Ƿ���ȷ
	public void validateLogin() {
		Admin a = userServiceInter.getAdmin(admin.getUsername());
		a = admin;
		if(a == null) {
			this.addFieldError("username", "�û��������ڣ�");
		} else
		if(!admin.getPassword().equals(a.getPassword())){			
			this.addFieldError("password", "���벻��ȷ��");
		}
		admin = a;
	}
	//��¼
	public String login() {
		if(admin != null) {
			Admin a = userServiceInter.getAdmin(admin.getUsername());
			session.put("admin", a);
			return "success";
		}else{
			return "login";
		}
		
	}
	
	//ִ���޸���������
	public String changepwd() {
		Admin admin = adminServiceInter.getAdmin();
		if(!admin.getPassword().equals(oldpwd) ) {
			request.put("message", "��ǰ������������");
			return "message";
		} else {
			admin.setUsername(admin.getUsername());
			admin.setPassword(newpwd);
			Boolean modifyAccount = adminServiceInter.modifyAccount(admin);
			if(modifyAccount) {
				session.put("admin", admin);
				request.put("message", "�����޸ĳɹ���");
				return "message";
			} else {
				request.put("message", "�����޸�ʧ�ܣ�");
				return "message";
			}
		}
	}
	
	//�����˻���ӽ��
		public String addmoney() {
			Admin admin = adminServiceInter.getAdmin();
			Double money = this.money;
			admin.setUsername(admin.getUsername());
			admin.setPassword(admin.getPassword());
			admin.setMoney(admin.getMoney()+money);
			Boolean modifyAccount = adminServiceInter.modifyAccount(admin);
			if(modifyAccount) {
				session.put("admin", admin);
				request.put("message", "��ֵ�ɹ���");
				return "message";
			} else {
				request.put("message", "��ֵʧ�ܣ�");
				return "message";
			}
		}
	
	// ע��
	public String logout() {
    	session.remove("user");
		session.remove("personinfo");
		return "login";  
}
	
	//����Ա��ѯ�˻�
	public String listUsers() {
		System.out.println("���"+status.getId());
		List users = personinfoServiceInter.searchPersoninfo(status);
		request.put("users", users);
		return "users";
	}
	
	//������ʵ������ѯ�˻�
	public String search() {
		List users = personinfoServiceInter.searchPersoninfo(personinfo);
		request.put("users", users);
		return "users";
	}
	
	//�����˻�
	public String enabled() {
		userServiceInter.enabled(id);
		request.put("message", "���óɹ�");
		return "message";
	}
	//�����˻�
	public String locking() {
		userServiceInter.locking(id);
		request.put("message", "����ɹ�");
		return "message";
	}
	//ɾ���˻�
	public String del() {
		userServiceInter.delAccount(id);
		request.put("message", "ɾ���ɹ�");
		return "message";
	}
	
	//�Կ���ҳ�������֤����֤�û����Ƿ���ڡ�һ�����ֻ֤��ӵ��һ���˻�
	public void validateOpenAccount() {
		if(userServiceInter.getAccount(account.getUsername()) != null ) {
			request.put("message", "�û����Ѵ��ڣ�");
		}
		//
		
	}
	//����
	public String openAccount() {
		if(userServiceInter.getAccount(account.getUsername()) != null ) {
			request.put("message", "����ʧ�ܣ��û����Ѵ��ڣ�");
			return "message";
		}else {
			//��account��������˻�
			userServiceInter.addAccount(account);
			//��personinfo������Ӹ�����Ϣ
			account = userServiceInter.getAccount(account.getUsername());
			personinfo.setAccount(account);
			personinfoServiceInter.add(personinfo);
			request.put("message", "�����ɹ���");
			return "message";
		}
		
	}
	
}
