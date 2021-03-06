package cs544.exercise7_1.bank.dao;

import java.util.Collection;

import org.hibernate.SessionFactory;

import cs544.exercise7_1.bank.domain.Account;

public class AccountDAOHibernate implements IAccountDAO{

	//private SessionFactory sf = HibernateUtil.getSessionFactory();
	private SessionFactory sf;
	
	public AccountDAOHibernate() {
	
	}

	public void setSf(SessionFactory sf) {
		this.sf = sf;
	}

	@SuppressWarnings("unchecked")
	public Collection<Account> getAccounts() {
		// To prevent our Lazy Initialization problems
		// we don't have open session in view, so instead we have to eagerly load!
		return sf.getCurrentSession().createQuery("select distinct a from Account a " +
				"join fetch a.customer " +
				"join fetch a.entryList").list();
	}

	public Account loadAccount(long accountnumber) {
		return (Account) sf.getCurrentSession().get(Account.class, accountnumber);
	}

	public void saveAccount(Account account) {
		sf.getCurrentSession().persist(account);
	}

	public void updateAccount(Account account) {
		sf.getCurrentSession().saveOrUpdate(account);
	}

}
