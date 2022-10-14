package com.mihir.bank_transactions.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mihir.bank_transactions.entity.User;

@Repository
public class NetBankingDaoImpl implements NetBankingDao {

	@Autowired
	SessionFactory factory;

	@Override
	public User getUserDeatilsByUsername(String userName) {

		Session session = null;
		User user = null;

		try {
			session = factory.openSession();
			user = session.get(User.class, userName);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null)
				session.close();
		}

		return user;
	}

	@Override
	public boolean saveUserDetails(User user) {
		Session session = null;
		Transaction transaction = null;
		boolean isAdded = false;

		try {
			session = factory.openSession();
			transaction = session.beginTransaction();
			session.save(user);
			transaction.commit();
			isAdded = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null)
				session.close();
		}

		return isAdded;
	}

	@Override
	public User getUserByAccountNumber(long accountNumber) {

		Session session = null;
		User user = null;

		try {
			session = factory.openSession();
			Criteria criteria = session.createCriteria(User.class);

			criteria.add(Restrictions.eq("accountNumber", accountNumber));
			List<User> list = criteria.list();
			if (!list.isEmpty()) {
				user = list.get(0);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null)
				session.close();
		}
		return user;
	}

	@Override
	public boolean updateUser(User user) {
		Session session = null;
		Transaction transaction = null;
		boolean isUpdated = false;

		try {
			session = factory.openSession();
			transaction = session.beginTransaction();
			session.update(user);
			transaction.commit();
			isUpdated = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null)
				session.close();
		}
		return isUpdated;
	}

	@Override
	public boolean deleteUserDetails(User user) {
		Session session = null;
		Transaction transaction = null;
		boolean isDeleted = false;
		
		try {
			session=factory.openSession();
			transaction=session.beginTransaction();
			User presentUser=session.get(User.class, user.getUserName());
			
			session.delete(presentUser);
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
