package com.zeus.rcode.controllers;


import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;


import org.ocpsoft.prettytime.PrettyTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.zeus.rcode.models.Comment;
import com.zeus.rcode.models.Post;
import com.zeus.rcode.models.User;
import com.zeus.rcode.services.CommentServices;
import com.zeus.rcode.services.FileUpload;
import com.zeus.rcode.services.PostServices;
import com.zeus.rcode.services.UserServices;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/home")
public class HomeController {
	@Autowired
	 private  FileUpload fileUpload;
	 
	 @Autowired 
	private UserServices userServices; 
	
	@Autowired
	private PostServices postServices;
	
	@Autowired
	private CommentServices commentServices;
	
	@RequestMapping("")
	public String home(@ModelAttribute("newPost") Post post,Model model,HttpSession session,@ModelAttribute("newComment") Comment comment) {
		User user = userServices.findById((long)session.getAttribute("id"));
		PrettyTime prettyTime = new PrettyTime();
		ArrayList<Post> posts = postServices.getAllFriendsPost(user.getId());
		List<Post> postsND = new ArrayList<>(new LinkedHashSet<>(posts));  
		if(postsND.isEmpty()) {
			postsND = user.getPosts();
		}
		model.addAttribute("posts", postsND);		
		model.addAttribute("cUser", user);
		model.addAttribute("pTime", prettyTime);
		return "home";
	}
	
	
	
	 @PostMapping("/upload")
	    public String uploadFile(@RequestParam("image")MultipartFile multipartFile,Model model) throws IOException {
		 System.out.println("got to upload ");
		 String imageURL = fileUpload.uploadFile(multipartFile);
		 System.out.println(imageURL);

	        model.addAttribute("imageURL",imageURL);
	        return "home";
	    }
	
	
	
	
	
	@PostMapping("/post")
	public String createPost(@Valid @ModelAttribute("newPost") Post post,@RequestParam("image") MultipartFile multipartFile,HttpSession session){
		User cUser = userServices.findById((long)session.getAttribute("id"));
		
		if(multipartFile.isEmpty() && post.getMessage().equals("")) {
			return "redirect:/home";
		}else if(!multipartFile.isEmpty()) {
			
			try {
				String imageURL = fileUpload.uploadFile(multipartFile);
//				adding it to my datebase
				post.setUser(cUser);
				post.setPicture(imageURL);
				cUser.getPosts().add(post);
				postServices.addPost(post);
				return "redirect:/home";
			}catch (Exception e) {
				return "redirect:/home";
			}
		}else if(multipartFile.isEmpty() && !post.getMessage().equals("")) {
			post.setUser(cUser);
			cUser.getPosts().add(post);
			postServices.addPost(post);
			return "redirect:/home";
		}
		return "redirect:/home";
	}
	@PostMapping("/addComment/{id}")
	public String addComment(@Valid @ModelAttribute("newComment") Comment comment,@PathVariable("id") Long id,HttpSession session,@RequestParam("commentImage") MultipartFile multipartFile) {
		User cUser = userServices.findById((long)session.getAttribute("id"));
		Post cPost = postServices.getPost(id);
		System.out.println("******1");
		if(multipartFile.isEmpty() && comment.getComment().equals("")) {
			return "redirect:/home";
		}else if(!multipartFile.isEmpty()) {
			try {
				String imageURL = fileUpload.uploadFile(multipartFile);
//				adding it to my datebase
				comment.setUser(cUser);
				comment.setPost(cPost);
				comment.setPicture(imageURL);
				Comment newComment = comment;
				cUser.getComments().add(newComment);
				commentServices.addComment(newComment);
				
				
				
				return "redirect:/home";
			}catch (Exception e) {
				return "redirect:/home";
			}
		}else if(multipartFile.isEmpty() && !comment.getComment().equals("")) {
			comment.setUser(cUser);
			comment.setPost(cPost);
			System.out.println(cPost.getId() +"****** this is the current post");	
			Comment newComment = comment;
			cUser.getComments().add(newComment);
			commentServices.addComment(newComment);
			return "redirect:/home";
		}
		return "redirect:/home";
	}
	
	
	
	
	
	
	
	
	
	

}
