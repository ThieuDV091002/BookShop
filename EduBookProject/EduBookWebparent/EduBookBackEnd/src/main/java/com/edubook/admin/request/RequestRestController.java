package com.edubook.admin.request;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RestController
public class RequestRestController {

	@Autowired
	private RequestService service;
	
	@PostMapping("/request-update-{IDrequest}-{status}")
	public Response updateStatus(@PathVariable("IDrequest") Integer IDrequest, @PathVariable("status") String status,
			RedirectAttributes ra) {
		service.updateStatus(IDrequest, status);
		return new Response(IDrequest, status);
	}
	
class Response{
		private Integer IDrequest;
		private String status;
		
		public Response(Integer iDrequest, String status) {
			this.IDrequest = iDrequest;
			this.status = status;
		}
		public Integer getIDrequest() {
			return IDrequest;
		}
		public void setIDrequest(Integer iDrequest) {
			IDrequest = iDrequest;
		}
		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}
		
	}
}
