package com.megaProject.Application.response;



public class ReturnResponse {
	private int status;
	private String error = "";
	private String message = "";
	private int pageNo;
	private int pageSize;
	private boolean lastPage;
	private Object data;

	
	public ReturnResponse() {
		
	}
	
	public ReturnResponse(int status, String error, String message) {
		super();
		this.status = status;
		this.error = error;
		this.message = message;
	}


	
	

	public ReturnResponse(int status, String error, String message, Object data) {
		super();
		this.status = status;
		this.error = error;
		this.message = message;
		this.data = data;
	}

	public ReturnResponse(int status, String error, String message, int pageNo, 
			int pageSize, boolean lastPage, Object data) {
		super();
		this.status = status;
		this.error = error;
		this.message = message;
		this.pageNo = pageNo;
		this.pageSize = pageSize;
		this.lastPage = lastPage;
		this.data = data;

	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}



	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public boolean isLastPage() {
		return lastPage;
	}

	public void setLastPage(boolean lastPage) {
		this.lastPage = lastPage;
	}


	
	

	
	
	
	
	
	
	
	
	

}
