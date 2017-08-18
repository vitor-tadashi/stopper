package br.com.verity.regponto.dao;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface GenericDAO<T> extends JpaRepository<T, Serializable>{

}
