package com.intuit.engine;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.intuit.api.ProjectStatus;
import com.intuit.api.UserType;
import com.intuit.base.Bid;
import com.intuit.base.MarketplaceProject;
import com.intuit.base.User;
import com.intuit.repo.ProjectRepository;

@Component
public class MarketplaceManager {
	
	@Autowired
	ProjectRepository projRepo;
	
	/*
	 * Bid validation
	 * A bid is valid if :
	 * 1. Project exists and is not CLOSED.
	 * 2. User is Seller (For this project, assumption is Actor types are mutually exclusive)
	 * 
	 */
	public boolean isBidValid(Bid bid, MarketplaceProject proj, User user) {
		
		if(proj == null) {
			bid.setBidValue((long) -1);
			return false;
		}
		
		if(proj.getStatus() == ProjectStatus.CLOSED) {
			bid.setBidValue((long) -2); 
			return false;
		}
						
		if(user == null || user.getUserType() == UserType.SELLER) {
			bid.setBidValue((long) -3);
			return false;
		}
		
		return true;		
	}
	
	/*
	 * Update project's lowest bid if applicable
	 */
	public void updateLowestBid(MarketplaceProject proj, Bid bid) {
		
		if(proj.getLowestBid() == null || bid.getBidValue() < proj.getLowestBid().getBidValue()) {
			proj.setLowestBid(bid);
		}	
	
		projRepo.saveAndFlush(proj);

	}
	
	/*
	 * Update status of projects which are OPEN
	 */
	public void updateOpenProjectStatus() {
		
		List<MarketplaceProject> projs = projRepo.findByStatus(ProjectStatus.CLOSED);
		 for(MarketplaceProject proj : projs) {
			 proj.updateProjectStatus();
			 projRepo.saveAndFlush(proj);
		 }
		 
	}
	
	public void updateProjectStatus(MarketplaceProject proj) {
		 
		 proj.updateProjectStatus();
		 projRepo.saveAndFlush(proj); 
	}
	
	public String serializeExcept(MarketplaceProject proj, String attr) throws JsonProcessingException {
		
		ObjectMapper mapper = new ObjectMapper();
		FilterProvider filters = new SimpleFilterProvider()  
		      .addFilter("excludebids", SimpleBeanPropertyFilter.serializeAllExcept(attr));  
		ObjectWriter writer = mapper.writer(filters);  
		
		return writer.writeValueAsString(proj);
	}

}
