package com.intuit.base;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Date;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;


@Embeddable
public class Bid {
	
	@OneToOne
	@JoinColumn(name = "bidder_id")
	private User buyer;
	private Long bidValue;
	private LocalDateTime bidTimeStamp;

	public User getBuyer() {
		
		return this.buyer;
	}

	public void setBuyer(User buyer) {
		
		this.buyer = buyer;
	}

	public Long getBidValue() {
		
		return this.bidValue;
	}

	public void setBidValue(Long bidValue) {
		
		this.bidValue = bidValue;
	}

	public LocalDateTime getBidTimestamp() {
		
		return this.bidTimeStamp;
	}

	public void setBidTimeStamp(LocalDateTime bidTimeStamp) {
		
		this.bidTimeStamp = bidTimeStamp;
	}

}
