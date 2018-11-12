package com.travel.book.easy.travelbookeasy.services.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.travel.book.easy.travelbookeasy.db.model.Resource;
import com.travel.book.easy.travelbookeasy.db.repository.ResourceRepository;
import com.travel.book.easy.travelbookeasy.services.interfaces.ResourceService;

@Service
public class ResourceServiceImpl implements ResourceService {

	@Autowired
	private ResourceRepository resourceRepository;

	@Override
	public Resource createResource(String id, String value) {

		Resource resource = new Resource();
		resource.setId(id);
		resource.setValue(value);
		resource.setTimeCreated(new Date());

		Resource savedResource = resourceRepository.saveAndFlush(resource);

		return savedResource;
	}

}
