package com.intuit.engine;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.intuit.api.ProjectStatus;
import com.intuit.base.Bid;
import com.intuit.base.MarketplaceProject;
import com.intuit.base.User;
import com.intuit.repo.ProjectRepository;
import com.intuit.repo.UserRepository;

@Service
public class MarketplaceService {
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private ProjectRepository projRepo;
	
	@Autowired 
	MarketplaceManager manager;
	
	/*
	 * Create new project.
	 * Every project is attached to SELLER
	 * Project status is set to OPEN.
	 */
	public MarketplaceProject createProject(Long sellerId, String req) throws JsonParseException, JsonMappingException, IOException {
	
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		TypeReference<MarketplaceProject> mapType = new TypeReference<MarketplaceProject>(){};
		MarketplaceProject project = mapper.readValue(req, mapType);
		
		User seller = userRepo.findById(sellerId);
		project.setSeller(seller);
		project.setStatus(ProjectStatus.OPEN);
		mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		project.setDisplayBidEndDate(project.getBidEndDate());

		return projRepo.save(project);
	}
	
	/*
	 * Find all projects. 
	 * Update the status of projects to CLOSED if applicable
	 * 
	 */
	public List<MarketplaceProject> getAllProjects() {
	
		manager.updateOpenProjectStatus();
		
		return projRepo.findAll();
	}
	
	/*
	 * Find project by id
	 */
	public MarketplaceProject getProjectById(Long projectId) {
	
		MarketplaceProject proj = projRepo.findById(projectId);
		manager.updateProjectStatus(proj);
		
		return proj;
	}
	
	/*
	 * Find project by status
	 */
	public List<MarketplaceProject> getProjectByStatus(String projectStatus) {
	
		manager.updateOpenProjectStatus();
		
		return projRepo.findByStatus(ProjectStatus.enumTypeOf(projectStatus));
	}

	/*
	 * Find all projects. Internal Utiltiy Function
	 * 
	 */
	public void deleteAllProjects() {
	
		projRepo.deleteAll();
	}
	
	/*
	 *  Create new Bid for project.
	 *  Before adding the bid to project, check if bid is valid
	 */
	public Bid createBid(Long userId, Long projectId, Long bidValue) {
	
		Bid bid = new Bid();
		MarketplaceProject proj = projRepo.findById(projectId);
		User buyer = userRepo.findById(userId);
		
		// check if bid is valid
		if(!manager.isBidValid(bid, proj, buyer))
			return bid;
		
		bid.setBuyer(buyer);
		bid.setBidValue(bidValue);
		bid.setBidTimeStamp(LocalDateTime.now());
		
		// add bid to project's bid list
		proj.getBids().add(bid);
		
		// update project's lowests
		manager.updateLowestBid(proj, bid);
		
		return bid;
	}
	
	/*
	 * Create new user
	 */
	public User createUser(String req) throws JsonParseException, JsonMappingException, IOException {
		
		// Map request to User object
		ObjectMapper mapper = new ObjectMapper();
	    TypeReference<User> mapType = new TypeReference<User>(){};
	    User user = mapper.readValue(req, mapType);
			
		return userRepo.save(user);
	}
	
	/*
	 * Find all users
	 */
	public List<User> getUsers() {
	
		List<User> users = new ArrayList<User>();
		users.addAll(userRepo.findAll());
		
		return users;
	}

	/*
	 * Find user by id
	 */
	public User getUserById(Long userId) {
		
		return userRepo.findById(userId);	
	}

	/*
	 * Find user by type (SELLER/BUYER)
	 */
	public List<User> getUsersByType(String userType) {
		
		return userRepo.findByType(userType);
	}

}
