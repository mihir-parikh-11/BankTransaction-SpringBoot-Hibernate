package com.mihir.bank_transactions.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mihir.bank_transactions.entity.BankAccount;
import com.mihir.bank_transactions.entity.DebitCard;
import com.mihir.bank_transactions.entity.TransactionStatement;

@Repository
public class BankdaoImp implements BankDao {

	@Autowired
	SessionFactory factory;

	@Override
	public boolean openBankAccount(BankAccount account) {
		Session session = null;
		Transaction transaction = null;
		boolean isAdded = false;

		try {

			session = factory.openSession();
			transaction = session.beginTransaction();
			session.save(account);
			transaction.commit();
			isAdded = true;

		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			if (session != null)
				session.close();

		}

		return isAdded;
	}

	@Override
	public void saveDebitCard(DebitCard debitCard) {

		Session session = null;
		Transaction transaction = null;

		try {

			session = factory.openSession();
			transaction = session.beginTransaction();
			session.save(debitCard);
			transaction.commit();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null)
				session.close();

		}

	}

	@Override
	public void saveTransaction(TransactionStatement statement) {

		Session session = null;
		Transaction transaction = null;

		try {

			session = factory.openSession();
			transaction = session.beginTransaction();
			session.save(statement);
			transaction.commit();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null)
				session.close();
		}

	}

	@Override
	public BankAccount getAccountDetailsByAccountNumber(long accountNumber) {
		Session session = null;
		BankAccount account = null;

		try {
			session = factory.openSession();
			account = session.get(BankAccount.class, accountNumber);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return account;
	}

	@Override
	public DebitCard getDebitCardDetailsByAccountNumber(long accountNumber) {

		DebitCard debitCard = null;
		Session session = null;

		try {
			session = factory.openSession();
			Criteria criteria = session.createCriteria(DebitCard.class);
			criteria.add(Restrictions.eq("accountNumber", accountNumber));
			List<DebitCard> list = criteria.list();
			if (!list.isEmpty()) {
				debitCard = list.get(0);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null)
				session.close();
		}

		return debitCard;
	}

	@Override
	public List<TransactionStatement> getTransactionDetailsByAccountNumber(long accountNumber) {

		Session session = null;
		List<TransactionStatement> statements = null;

		try {
			session = factory.openSession();
			Criteria criteria = session.createCriteria(TransactionStatement.class);

			criteria.add(Restrictions.eq("accountNumber", accountNumber));
			criteria.addOrder(Order.desc("transactionDate"));
			statements = criteria.list();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null)
				session.close();
		}

		return statements;
	}

	@Override
	public List<TransactionStatement> getTransactionDetailsByDurationByAccountNumber(long accountNumber, String startDate,
			String endDate) {
		
		Session session = null;
		List<TransactionStatement> list= null;
		
		
		try {
			session = factory.openSession();
			Criteria criteria = session.createCriteria(TransactionStatement.class);
			
			Criterion criterion1=Restrictions.between("transactionDate", startDate, endDate);
			Criterion criterion2= Restrictions.eq("accountNumber", accountNumber);
			
			criteria.add(Restrictions.and(criterion1,criterion2));
			criteria.addOrder(Order.desc("transactionDate"));
			
			list =criteria.list();
			
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			if (session != null)
				session.close();
		}

		return list;
	}

	@Override
	public boolean updateAccount(BankAccount account) {
		Session session = null;
		Transaction transaction = null;
		boolean isUpdated = false;
		
		try {
			
			session=factory.openSession();
			transaction=session.beginTransaction();
			BankAccount bankAccount = session.get(BankAccount.class, account.getAccountNumber());
			
			if(bankAccount != null) {
				session.evict(bankAccount);
				session.update(account);
				transaction.commit();
				isUpdated=true;
			}
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			if(session != null)
				session.close();
		}
		
		return isUpdated;
	}

	@Override
	public DebitCard getDebitCardDetailsByDebitCard(long debitCardNumber) {

		Session session = null;
		DebitCard debitCard = null;

		try {
			session = factory.openSession();
			debitCard=session.get(DebitCard.class, debitCardNumber);
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			if(session != null)
				session.close();
		}
		
		return debitCard;
	}
	


	@Override
	public boolean updateDebitCard(DebitCard card) {
		Session session = null;
		Transaction transaction = null;
		boolean isUpdated = false;

		try {
			session = factory.openSession();
			transaction = session.beginTransaction();
			session.update(card);
			transaction.commit();
			isUpdated = true;
		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			if (session != null)
				session.close();

		}
		return isUpdated;
	}
	
	

	@Override
	public boolean deleteBankAccount(BankAccount account) {

		Session session = null;
		Transaction transaction = null;
		boolean isDeleted = false;
		
		try {
			session=factory.openSession();
			transaction=session.beginTransaction();
			BankAccount presentAccount=session.get(BankAccount.class, account.getAccountNumber());
			
			session.delete(presentAccount);
			transaction.commit();
			isDeleted = true;
			
		}catch(Exception e) {
			
		}finally {
			if(session != null)
				session.close();
		}
		
		return isDeleted;
	}

	@Override
	public boolean deleteTransactionOfAccount(TransactionStatement statement) {
		Session session = null;
		Transaction transaction = null;
		boolean isDeleted = false;
		
		try {
			session=factory.openSession();
			transaction=session.beginTransaction();
			TransactionStatement presentStatement=session.get(TransactionStatement.class, statement.getTransactionId());
			
			session.delete(presentStatement);
			transaction.commit();
			isDeleted = true;
			
		}catch(Exception e) {
			
		}finally {
			if(session != null)
				session.close();
		}
		
		return isDeleted;
	}

	@Override
	public boolean deleteDebitCardDetail(DebitCard card) {
		Session session = null;
		Transaction transaction = null;
		boolean isDeleted = false;
		
		try {
			session=factory.openSession();
			transaction=session.beginTransaction();
			DebitCard presentCard=session.get(DebitCard.class, card.getDebitCardNumber());
			
			session.delete(presentCard);
			transaction.commit();
			isDeleted = true;
			
		}catch(Exception e) {
			
		}finally {
			if(session != null)
				session.close();
		}
		
		return isDeleted;
	}

	

}
