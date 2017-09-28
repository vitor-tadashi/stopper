package br.com.verity.pause.converter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public interface Converter<E, B> {

	E convertBeanToEntity(B bean);

	B convertEntityToBean(E entity);

	default List<B> convertEntityToBean(List<E> entities) {
		if (entities == null)
			return null;

		List<B> beans = new ArrayList<B>();

		for (E entity : entities) {
			beans.add(convertEntityToBean(entity));
		}
		return beans;
	}

	default List<E> convertBeanToEntity(List<B> beans) {
		if (beans == null)
			return null;

		List<E> entities = new ArrayList<E>();

		for (B bean : beans) {
			entities.add(convertBeanToEntity(bean));
		}
		return entities;
	}

	default Set<B> convertEntityToBean(Set<E> entities) {
		if (entities == null)
			return null;

		Set<B> beans = new HashSet<B>();

		for (E entity : entities) {
			beans.add(convertEntityToBean(entity));
		}
		return beans;
	}

	default Set<E> convertBeanToEntity(Set<B> beans) {
		if (beans == null)
			return null;

		Set<E> entities = new HashSet<E>();

		for (B bean : beans) {
			entities.add(convertBeanToEntity(bean));
		}
		return entities;
	}
}
