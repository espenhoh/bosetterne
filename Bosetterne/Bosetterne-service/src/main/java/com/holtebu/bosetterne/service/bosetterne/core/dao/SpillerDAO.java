package com.holtebu.bosetterne.service.bosetterne.core.dao;

import java.util.List;

import org.hibernate.SessionFactory;

import com.google.inject.Inject;
import com.holtebu.bosetterne.service.bosetterne.core.Spiller;
import com.yammer.dropwizard.hibernate.AbstractDAO;

public class SpillerDAO extends AbstractDAO<Spiller> {
	
	@Inject
	public SpillerDAO(SessionFactory factory) {
		super(factory);
	}

	public Spiller findById(Long id) {
		return get(id);
	}

	public long create(Spiller spiller) {
		return persist(spiller).getId();
	}

	public List<Spiller> findAll() {
		return list(namedQuery("com.example.helloworld.core.Person.findAll"));
	}
}
