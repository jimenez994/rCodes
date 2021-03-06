package com.zeus.rcode.controllers;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.ocpsoft.prettytime.PrettyTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.zeus.rcode.models.Image;
import com.zeus.rcode.models.Question;
import com.zeus.rcode.models.User;
import com.zeus.rcode.services.ImageServices;
import com.zeus.rcode.services.QuestionServices;
import com.zeus.rcode.services.UserServices;

@Controller
public class DashboardController {
	@Autowired
	private QuestionServices questionServices;
	
	@Autowired
	private UserServices userServices;
	
	@Autowired
	private ImageServices imageServices; 
	
	@RequestMapping("/dashboard")
	public String dashboard(HttpSession session,Model model,@ModelAttribute("request")User request,@ModelAttribute("newQuestion") Question question){
		List<Question> questions = questionServices.getAll();
		List<Image> images = imageServices.getAll();
		List<User> users = userServices.notFriendsList((long)session.getAttribute("id"));
		PrettyTime prettyTime = new PrettyTime();
		if( session.getAttribute("id") != null ){
			model.addAttribute("images", images);
			model.addAttribute("users", users);
			model.addAttribute("questions", questions);
			model.addAttribute("pTime", prettyTime);
			model.addAttribute("cUser", userServices.findById((long)session.getAttribute("id")));
			return "dashboard";
		}else{
			return "redirect:/";
		}
	}
	
//	adding a question 
	@PostMapping("/create/quetion")
	public String postQuestion(@RequestParam("file") MultipartFile file,HttpSession session,@Valid @ModelAttribute("newQuestion") Question question,BindingResult result){
		if(result.hasErrors()) {
			return "redirect:/dashboard";
		}else{
			
		
		if (!file.isEmpty()) {
			try {
				byte[] bytes = file.getBytes();
				
				System.out.println(file.getOriginalFilename());

				// Creating the directory to store file
				File dir = new File("src/main/resources/static/images");
				if (!dir.exists())
					dir.mkdirs();

				// Create the file on server
				File serverFile = new File(dir.getAbsolutePath()
						+ File.separator + file.getOriginalFilename());
				BufferedOutputStream stream = new BufferedOutputStream(
						new FileOutputStream(serverFile));
				stream.write(bytes);
				stream.close();
				
//						adding this to the detabase
				User user = userServices.findById((long)session.getAttribute("id"));
				Question newQuestion = question;
				newQuestion.setUser(user);
				newQuestion.setPicture(file.getOriginalFilename());

				user.getQuetion().add(newQuestion);
				questionServices.addQuestion(newQuestion);

				return "redirect:/dashboard";
			} catch (Exception e) {
				return "redirect:/dashboard";
			}
		}else if(file.isEmpty()){
			User user = userServices.findById((long)session.getAttribute("id"));
			Question newQuestion = question;
			newQuestion.setUser(user);
			user.getQuetion().add(newQuestion);
			questionServices.addQuestion(newQuestion);
			
			return "redirect:/dashboard";
		}else {
			return "redirect:/dashboard";
		}
		}
}
			
// send friend Request
	@PostMapping("/request/{id}")
	public String request(@PathVariable("id") Long id,HttpSession session) {
		User user = userServices.findById((long)session.getAttribute("id"));
		User resiver = userServices.findById(id);
		List<User> requests = resiver.getRecieveRequests();
		requests.add(user);
		resiver.setRecieveRequests(requests);
		userServices.update(resiver);
		return "redirect:/dashboard";
	}
//	cancel friend Request
	@PostMapping("/cancel/{id}")
	public String cancelRequest(@PathVariable("id") int id,HttpSession session) {
		User user = userServices.findById((long)session.getAttribute("id"));
		User resiver = userServices.findById(id);
		List<User> requests = resiver.getRecieveRequests();
		System.out.println(requests.size());
		System.out.println(requests.toString());
		System.out.println(user.getSendRequests().toString());
		for(int i = 0; i < requests.size();i++) {
			if(user.getId() == requests.get(i).getId()) {
				requests.remove(i);
				resiver.setRecieveRequests(requests);

				userServices.update(resiver);
			}
		}
		return"redirect:/dashboard";
	}
//	accept friend request
	@PostMapping("/accept/{id}")
	public String acceptFriendRequest(@PathVariable("id") Long id,HttpSession session) {
//		current user
		User currentUser = userServices.findById((long)session.getAttribute("id"));
//		other user
		User otherUser = userServices.findById(id);
//		get list of friends of the other user
		List<User> otherUserfriends = otherUser.getUserFriends();
//		list of your friends
		List<User> originalFriends = currentUser.getUserFriends();
		
		System.out.println("*********-1");
		
		otherUserfriends.add(currentUser);
		otherUser.setUserFriends(otherUserfriends);
		userServices.update(otherUser);
		
		System.out.println("*********-2");
		
		originalFriends.add(otherUser);
		currentUser.setUserFriends(originalFriends);
		userServices.update(currentUser);
		
		System.out.println("*********-3");
		return "redirect:/dashboard";
	}
	
	@PostMapping("/uploadFile")
	public String handleFormUpload(@RequestParam("file") MultipartFile file,HttpSession session) {
		if (!file.isEmpty()) {
			try {
				byte[] bytes = file.getBytes();
				
				
				System.out.println(file.getOriginalFilename());

				// Creating the directory to store file
				File dir = new File("src/main/resources/static/images");
				if (!dir.exists())
					dir.mkdirs();

				// Create the file on server
				File serverFile = new File(dir.getAbsolutePath()
						+ File.separator + file.getOriginalFilename());
				BufferedOutputStream stream = new BufferedOutputStream(
						new FileOutputStream(serverFile));
				stream.write(bytes);
				stream.close();
				
//				adding this to the detabase
				User user = userServices.findById((long)session.getAttribute("id"));
				Image newImage = new Image(user,file.getOriginalFilename());
				imageServices.addImage(newImage);
				user.getImage().add(newImage);
				imageServices.addImage(newImage);

				return "redirect:/dashboard";
			} catch (Exception e) {
				return "redirect:/dashboard";
			}
		} else {
			return "redirect:/dashboard";
		}
	}	
}
