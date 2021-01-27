package com.megaProject.Application.service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.megaProject.Application.response.ReturnResponse;
@Service
public class Pagging {
	
	public ResponseEntity<?> PagedValues(List sourcelist,int pageNo,int pageSize) {
	    if(pageSize <= 0 || pageNo < 0) {
	        throw new IllegalArgumentException("invalid page size: " + pageSize);
	    }
	    int size = sourcelist.size();
	    int fromIndex = 0;
	    int toIndex = 0;
	    boolean lastPage = false;
	    fromIndex = pageNo * pageSize;
	    toIndex = fromIndex+pageSize;
	    
	    if(toIndex >= size) {
	    	toIndex = size;
			lastPage = true;
	    }
	    
	    if(fromIndex > toIndex) {
	    	return ResponseEntity.status(200).body(new ReturnResponse(401," Bad Request","invalid credentials index are out of bound" ));
	    }

	    List<Object> sublist = sourcelist.subList(fromIndex, toIndex);

		
		
		if(toIndex >= size)  {			
			return ResponseEntity.status(200).body(new ReturnResponse(200," ","Success",pageNo, size,lastPage ,sublist ));
		}
		

		
	    if(fromIndex >= size) {
	    	return ResponseEntity.status(200).body(new ReturnResponse(401," Bad Request","invalid credentials" ));
	    }
	    
		return ResponseEntity.status(200).body(new ReturnResponse(200," ","Success",pageNo,pageSize,lastPage,sublist));
	    

		
	}

}




