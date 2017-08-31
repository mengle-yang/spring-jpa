/**
 * 
 */
package com.sanhang.springdatajpa;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @author liubin
 *
 */
@Configuration
@EnableJpaRepositories(repositoryFactoryBeanClass = DaoFactoryBean.class)
public class DaoConfig {

}
