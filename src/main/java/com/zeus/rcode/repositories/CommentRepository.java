package com.zeus.rcode.repositories;

import java.util.ArrayList;

import org.springframework.data.repository.CrudRepository;

import com.zeus.rcode.models.Comment;

public interface CommentRepository extends CrudRepository<Comment,Long>{
	ArrayList<Comment> findAll();
	
}
