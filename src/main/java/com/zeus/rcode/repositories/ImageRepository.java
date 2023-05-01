package com.zeus.rcode.repositories;

import java.util.ArrayList;

import org.springframework.data.repository.CrudRepository;

import com.zeus.rcode.models.Image;


public interface ImageRepository extends CrudRepository<Image , Long>{
	ArrayList<Image> findAll();
}
