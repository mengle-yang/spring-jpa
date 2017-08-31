/**
 * 
 */
package com.sanhang.springdatajpa;

import java.io.Serializable;
import java.util.List;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.data.jpa.repository.support.CrudMethodMetadata;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.QueryDslJpaRepository;
import org.springframework.data.jpa.repository.support.Querydsl;
import org.springframework.data.querydsl.EntityPathResolver;
import org.springframework.data.querydsl.SimpleEntityPathResolver;

import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.AbstractJPAQuery;

/**
 * @author liubin
 *
 */
public class BaseDaoImpl<T, ID extends Serializable> extends QueryDslJpaRepository<T, ID> implements BaseDao<T, ID> {
	
	final Log log = LogFactory.getLog(getClass());
	
	protected final JpaEntityInformation<T, ID> entityInformation;
	
	private static final EntityPathResolver DEFAULT_ENTITY_PATH_RESOLVER = SimpleEntityPathResolver.INSTANCE;

    private final EntityPath<T> path;
    private final PathBuilder<T> builder;
    private final Querydsl querydsl;

	public BaseDaoImpl(JpaEntityInformation<T, ID> entityInformation, EntityManager entityManager,
			EntityPathResolver resolver) {
		super(entityInformation, entityManager, resolver);

		this.entityInformation = entityInformation;
		
		this.path = resolver.createPath(entityInformation.getJavaType());
        this.builder = new PathBuilder<>(path.getType(), path.getMetadata());
        this.querydsl = new CustomQuerydsl(entityManager, builder);
	}

	public BaseDaoImpl(JpaEntityInformation<T, ID> entityInformation, EntityManager entityManager) {
		this(entityInformation, entityManager, DEFAULT_ENTITY_PATH_RESOLVER);
	}
	
	public JpaEntityInformation<T, ID> getEntityInformation() {
		return this.entityInformation;
	}

	@Override
	public List<T> search(Predicate predicate) {
		List<T> list = findAll(predicate);
		return list;
	}
	
	@Override
	protected JPQLQuery<?> createQuery(Predicate... predicate) {

		AbstractJPAQuery<?, ?> query = querydsl.createQuery(path).where(predicate);
		CrudMethodMetadata metadata = getRepositoryMethodMetadata();

		if (metadata == null) {
			return query;
		}

		LockModeType type = metadata.getLockModeType();
		query = type == null ? query : query.setLockMode(type);

		for (Entry<String, Object> hint : getQueryHints().entrySet()) {
			query.setHint(hint.getKey(), hint.getValue());
		}

		return query;
	}

}