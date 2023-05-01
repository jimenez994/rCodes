package com.zeus.rcode.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zeus.rcode.models.Comment;
import com.zeus.rcode.repositories.CommentRepository;

@Service
public class CommentServices {
	
	@Autowired
	private CommentRepository commentRepository;
	
	public ArrayList<Comment> getAll(){
		ArrayList<Comment> list = commentRepository.findAll();
		Collections.reverse(list);
		return list;
	}
	public Comment getComment(Long id) {
		Optional<Comment> comment = commentRepository.findById(id);
		if(comment.isPresent()) {			
			return comment.get();
		}
		return null;
	}
	public void addComment(Comment comment) {
		commentRepository.save(comment);
	}
	public void deleteComment(Long id) {
		commentRepository.deleteById(id);
	}

}
