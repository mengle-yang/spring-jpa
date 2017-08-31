package com.sanhang.springdatajpa;

import java.lang.reflect.Field;

import javax.persistence.EntityManager;

import org.springframework.data.jpa.repository.support.Querydsl;

import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.AbstractJPAQuery;
import com.querydsl.jpa.impl.JPAQuery;

public class CustomQuerydsl extends Querydsl {

	public CustomQuerydsl(EntityManager em, PathBuilder<?> builder) {
		super(em, builder);
	}

	@Override
	public AbstractJPAQuery<Object, JPAQuery<Object>> createQuery(EntityPath<?>... paths) {
		AbstractJPAQuery<Object, JPAQuery<Object>> jpaQuery = createQuery();
		for(EntityPath<?> path:paths) {
			jpaQuery = jpaQuery.leftJoin(path);
			Class<?> clazz = path.getClass();
			Field[] fields = clazz.getDeclaredFields();
			for(Field field:fields) {
				Class<?> type = field.getType();
				if(EntityPath.class.isAssignableFrom(type)&&!type.equals(path.getClass())) {
					try {
						EntityPath<?> newPath = (EntityPath<?>)field.get(path);
						jpaQuery = jpaQuery.leftJoin(newPath);
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return jpaQuery;
	}
}
