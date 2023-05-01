package com.zeus.rcode.repositories;

import java.util.ArrayList;

import org.springframework.data.repository.CrudRepository;

import com.zeus.rcode.models.Question;

public interface QuestionRepository extends CrudRepository<Question, Long>{
	ArrayList<Question> findAll();

}
