package com.intuit.api;

import java.util.HashMap;
import java.util.Map;

public enum UserType {
	SELLER("seller"), 
    BUYER("buyer"),
    ;

    private final String type;
    private static Map<String, UserType> map = new HashMap<String, UserType>();

    static {
    	for(UserType userType : UserType.values()) {
    		map.put(userType.type, userType);
    	}
    }
    
    UserType(String type) {
        this.type = type;
    }
    
    public String type() {
        return this.type;
    }
    
    public static UserType enumTypeOf(String userType) {
    	return (UserType) map.get(userType);
    }
}
