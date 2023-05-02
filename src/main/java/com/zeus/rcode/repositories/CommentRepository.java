package com.zeus.rcode.repositories;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import com.zeus.rcode.models.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long>{
	ArrayList<Comment> findAll();
    void deleteByPostId(Long postId);

	
}
