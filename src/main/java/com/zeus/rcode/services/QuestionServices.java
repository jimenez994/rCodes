package com.zeus.rcode.services;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.zeus.rcode.models.Question;
import com.zeus.rcode.models.User;
import com.zeus.rcode.repositories.QuestionRepository;

@Service
public class QuestionServices {
	private QuestionRepository questionRepo;
	
	public QuestionServices(QuestionRepository questionRepo) {
		this.questionRepo = questionRepo;
	}
	public List<Question> getAll(){
		List<Question> list = questionRepo.findAll();
		Collections.reverse(list);
		return list;
	}
	public List<Question> getUsersQuestions(User user){
		List<Question> list = user.getQuetion();
		Collections.reverse(list);
		return list;
		
	}
	public Question getQuestion(Long id) {
		Optional<Question> question = questionRepo.findById(id);
		if(question.isPresent()) {
			return question.get();
		}
		return null;
	}
	public void  addQuestion(Question question) {
		questionRepo.save(question);
	}
	
	public void deleteQuestion(Long id) {
		questionRepo.deleteById(id);
	}
}
