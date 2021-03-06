/**
 * 
 */
package com.sanhang.springdatajpa;

import java.io.Serializable;

import javax.persistence.EntityManager;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;

/**
 * @author yangmengle
 *
 */
public class DaoFactoryBean<R extends JpaRepository<T, I>, T, I extends Serializable>
		extends JpaRepositoryFactoryBean<R, T, I> {

	protected RepositoryFactorySupport createRepositoryFactory(EntityManager em) {
		return new DaoFactory<T, I>(em);
	}

	private static class DaoFactory<T, I extends Serializable> extends JpaRepositoryFactory {

		public DaoFactory(EntityManager em) {
			super(em);
		}

		protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {
			return BaseDaoImpl.class;
		}
	}
}
