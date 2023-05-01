package com.zeus.rcode.repositories;

import java.util.ArrayList;

import org.springframework.data.repository.CrudRepository;

import com.zeus.rcode.models.Answer;

public interface AnswerRepository extends CrudRepository<Answer, Long>{
	ArrayList<Answer> findAll();

}
