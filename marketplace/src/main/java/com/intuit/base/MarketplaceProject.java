package com.intuit.base;



import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.intuit.api.IProject;
import com.intuit.api.ProjectStatus;
import com.intuit.engine.MiscUtil;

@Entity
@Table(name = "project")
public class MarketplaceProject implements IProject {

	@TableGenerator(name = "project_gen", table = "pid_gen", pkColumnName = "gen_name", valueColumnName = "gen_val", allocationSize = 1)
	@Id
	@GeneratedValue(generator = "project_gen")
	private Long id;

	@Column(name = "name")
	private String name;
	
	@Column(name = "description")
	private String desc;
	
	@OneToOne
	@JoinColumn(name = "seller_id")
	private User seller;
	
	@Enumerated(EnumType.STRING)
	ProjectStatus status;
	
	@JsonProperty(access = Access.WRITE_ONLY)
	@Column(name = "bidEndDate")
	private LocalDateTime bidEndDate;
	
	@JsonProperty("Bid End Date")
	String displayBidEndDate;
	
	@JsonProperty(access = Access.WRITE_ONLY)
	@ElementCollection
	@CollectionTable(name = "project_bids", joinColumns = @JoinColumn(name = "id"))
	@AttributeOverrides({@AttributeOverride(name = "buyer", column = @Column(name = "bidder"))})
	private List<Bid> bids = new ArrayList<Bid>();
	
	@Column(name = "lowest_bid")
	Bid lowestBid;
	
	public Long getId() {
		
		return this.id;
	}

	public void setId(Long id) {
		
		this.id = id;
	}

	public String getName() {
		
		return this.name;
	}

	public void setName(String name) {
		
		this.name = name;
	}

	public String getDesc() {
		
		return this.desc;
	}

	public void setDesc(String desc) {
		
		this.desc = desc;
	}

	public User getSeller() {
		
		return this.seller;
	}

	public void setSeller(User seller) {
		
		this.seller = seller;
	}

	public LocalDateTime getBidEndDate() {
		
		return this.bidEndDate;
	}

	public void setBidEndDate(LocalDateTime bidEndDate) {

		this.bidEndDate = bidEndDate;
	}

	public String getDisplayBidEndDate() {
		
		return this.displayBidEndDate;
	}

	public void setDisplayBidEndDate(LocalDateTime bidEndDate) {
		
		this.displayBidEndDate = MiscUtil.getISOStringForDate(bidEndDate);
	}
	
	public List<Bid> getBids() {
		
		return this.bids;
	}

	public void addBid(Bid bid) {
		
		this.bids.add(bid);
	}

	public Bid getLowestBid() {
		
		return this.lowestBid;
	}

	public void setLowestBid(Bid bid) {
		
		this.lowestBid = bid;
	}

	public ProjectStatus getStatus() {
		
		updateProjectStatus();
		return this.status;
	}
	
	public void setStatus(ProjectStatus status) {
		this.status = status;
	}

	public boolean projToClose() {
		
		return (LocalDateTime.now().isAfter(this.bidEndDate)) && (this.status != ProjectStatus.CLOSED);
	}
	
	public void updateProjectStatus() {
		
		if(this.projToClose())
			this.setStatus(ProjectStatus.CLOSED);
	}
	

}
