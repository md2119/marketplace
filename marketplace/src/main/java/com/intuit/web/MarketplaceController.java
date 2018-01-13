package com.intuit.web;

import java.io.IOException;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.intuit.base.Bid;
import com.intuit.base.MarketplaceProject;
import com.intuit.base.User;
import com.intuit.engine.MarketplaceService;

@RestController
public class MarketplaceController {
	
	@Autowired 
	MarketplaceService mSrv;

	/*
	 * Get all projects
	 */
	@RequestMapping(value = "/api/projects", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<String> getAllProjects() {
	
		List<MarketplaceProject> projs = mSrv.getAllProjects();
		if(projs == null || projs.isEmpty())
			return new ResponseEntity<String>("No projects found!", HttpStatus.OK);	
		return new ResponseEntity(projs, HttpStatus.OK);
	}
	
	/*
	 * Get project by project id
	 */
	@RequestMapping(value = "/api/projects/project/{projectid:[0-9]+}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<String> getProjectById(@PathVariable("projectid") Long projectId) {
	
		MarketplaceProject proj = mSrv.getProjectById(projectId);
		if(proj == null)
			return new ResponseEntity<String>("Project not found!", HttpStatus.OK);
		return new ResponseEntity(proj, HttpStatus.OK);
	}
	
	/*
	 * Get projects by project status(OPEN/CLOSED)
	 */
	@RequestMapping(value = "/api/projects/{projectstatus:[a-z]+}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<String> getProjectByStatus(@PathVariable("projectstatus") String projectStatus) {
	
		List<MarketplaceProject> projs = mSrv.getProjectByStatus(projectStatus);
		if(projs == null || projs.isEmpty())
			return new ResponseEntity<String>("No projects found!", HttpStatus.OK);	
		return new ResponseEntity(projs, HttpStatus.OK);
	}

	/*
	 * Create new project
	 * input required : user id. Each project is associated with a seller
	 * e.g. input :
	 * 	{
	 * 		"name": "OldProject",
	 * 		"desc": "project description",
	 * 		"bidEndDate": "2019-01-12T13:15:30"
	 * 	}
	 */
	@RequestMapping(value = "/api/projects/create", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity createProject(@RequestParam(value="user", required=true) Long sellerId, @RequestBody String req) throws JsonParseException, JsonMappingException, IOException {

		MarketplaceProject proj = mSrv.createProject(sellerId, req);
		if(proj == null)
			return new ResponseEntity("Could not create project!", HttpStatus.OK);
		return new ResponseEntity(proj, HttpStatus.OK);
	}
	
	/*
	 * Create bid for project
	 * Input required : user id, project id, bid value
	 */
	@RequestMapping(value = "/api/projects/bid", method = RequestMethod.POST)
	@ResponseBody
	public  ResponseEntity<String> createBid(@RequestParam(value="user", required=true) Long uid, @RequestParam(value="project", required=true) Long pid, @RequestParam(value="bid", required=true) Long bidvalue) {
	
		Bid bid = mSrv.createBid(uid, pid, bidvalue);
		if(bid == null)
			return new ResponseEntity<String>("Bid unsuccessful!", HttpStatus.OK);
		if(bid.getBidValue() == -1)
			return new ResponseEntity<String>("Bid unsuccessful! Project not found!", HttpStatus.OK);
		if(bid.getBidValue() == -2)
			return new ResponseEntity<String>("Bid unsuccessful! Project is close!", HttpStatus.OK);
		if(bid.getBidValue() == -3)
			return new ResponseEntity<String>("Bid unsuccessful! User not authorized!", HttpStatus.OK);
		return new ResponseEntity(bid, HttpStatus.OK);
	}
	
	/*
	 * Get all users
	 */
	@RequestMapping(value = "/api/users", method = RequestMethod.GET)
	public ResponseEntity<String> getUsers() {
	
		List<User> users = mSrv.getUsers();
		if(users == null || users.isEmpty())
			return new ResponseEntity<String>("Users not found!", HttpStatus.OK);
		return new ResponseEntity(users, HttpStatus.OK);
	}
	
	/*
	 * Get user by user id
	 */
	@RequestMapping(value = "/api/users/{userid:[0-9]+}", method = RequestMethod.GET)
	public ResponseEntity<String> getUsersById(@PathVariable("userid") Long userId) {
	
		User user = mSrv.getUserById(userId);
		if(user == null)
			return new ResponseEntity<String>("User was not found!", HttpStatus.OK);
		return new ResponseEntity(user, HttpStatus.OK);
		
	}
	

	/*
	 * Create new user
	 * e.g. input : 
	 * 	{
  	 * 		"name" : "Obama",
  	 * 		"userType" : "buyer",
  	 * 		"attributes": {
  	 * 			"email" : "axy@gmail.com",
  	 * 			"contact" : "12233222222222"
  	 * 		}
  	 * 	}
	 */
	@RequestMapping(value = "/api/users/create", method = RequestMethod.POST)
	public ResponseEntity<String> createUser(@RequestBody String req) throws JsonParseException, JsonMappingException, IOException {
	
		User user = mSrv.createUser(req);
		if(user == null)
			return new ResponseEntity<String>("Could not create user!", HttpStatus.OK);		
		return new ResponseEntity(user, HttpStatus.OK);
	}
	
	
	
	@RequestMapping(value = "/api/projects/delete", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<String> deleteAllProjects() {
	
		mSrv.deleteAllProjects();
		return new ResponseEntity<String>("All projects deleted!", HttpStatus.OK);	
	}
	
}
