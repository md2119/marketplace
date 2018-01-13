package com.intuit.base;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapKeyColumn;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import com.intuit.api.UserType;

@Entity
@Table(name = "user")
public class User {

	@TableGenerator(name = "user_gen", table = "uid_gen", pkColumnName = "gen_name", valueColumnName = "gen_val", allocationSize = 1)
	@Id
	@GeneratedValue(generator = "user_gen")
	private Long id;
	
	@Column(name = "name")
	private String name;
	
	@Enumerated(EnumType.STRING)
	private UserType type;
	
	@ElementCollection
	@MapKeyColumn(name = "attrkey")
	@Column(name = "attrvalue")
	@CollectionTable(name = "buyer_attributes", joinColumns = @JoinColumn(name = "attr_id"))
	private Map<String, String> attributes = new HashMap<String, String>();
	
	
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

	public Map<String, String> getAttributes() {
		
		return this.attributes;
	}

	public void setAttributes(Map<String, String> attributes) {
		
		this.attributes.putAll(attributes);
	}
	
	public void addAttributes(String key, String value) {
		
		this.attributes.put(key, value);
	}

	public UserType getUserType() {
		
		return this.type;
	}

	public void setUserType(String userType) {
		
		this.type = UserType.enumTypeOf(userType);
	}


}
