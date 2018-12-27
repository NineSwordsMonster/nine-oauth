package com.nineswords.oauth.dal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

/**
 * Create by Jarvis.wang on me's device
 *
 * @author Jarvis.wang  me
 * @date 2018-11-23 10:09
 */
@NoRepositoryBean
public interface BaseRepo<T, ID extends Serializable> extends JpaSpecificationExecutor<T>, JpaRepository<T, ID>, QuerydslPredicateExecutor<T>  {
}
