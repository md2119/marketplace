package com.intuit.api;

import java.util.HashMap;
import java.util.Map;

public enum ProjectStatus {
	OPEN("open"), 
    CLOSED("closed"),
    SOLD("sold")
    ;

    private final String status;
    private static Map<String, ProjectStatus> map = new HashMap<String, ProjectStatus>();

    static {
    	for(ProjectStatus status : ProjectStatus.values()) {
    		map.put(status.status, status);
    	}
    }
    
    ProjectStatus(String status) {
        this.status = status;
    }
    
    public String status() {
        return this.status;
    }
    
    public static ProjectStatus enumTypeOf(String status) {
    	return (ProjectStatus) map.get(status);
    }

}
