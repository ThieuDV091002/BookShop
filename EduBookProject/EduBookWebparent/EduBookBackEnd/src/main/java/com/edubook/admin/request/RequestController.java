package com.edubook.admin.request;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.edubook.common.entity.Request;

@Controller
public class RequestController {

	@Autowired
	private RequestService service;
	
	@GetMapping("/request")
	public String listFirstPage(Model model) {
		return listByPage(1, model, null);
	}
	
	@GetMapping("/request-page-{pageNum}")
	public String listByPage(@PathVariable(name = "pageNum") int pageNum, Model model,
			@Param("keyword")String keyword) {
		Page<Request> page = service.listRequestByPage(pageNum, keyword);
		List<Request> listRequests = page.getContent();
		
		long startCount = (pageNum - 1)*RequestService.REQUEST_PER_PAGE + 1;
		long endCount = startCount + RequestService.REQUEST_PER_PAGE - 1;
		if(endCount > page.getTotalElements()) {
			endCount = page.getTotalElements();
		}
		
		model.addAttribute("currentPage", pageNum);
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("startCount", startCount);
		model.addAttribute("endCount", endCount);
		model.addAttribute("totalItems", page.getTotalElements());
		model.addAttribute("listRequests", listRequests);
		model.addAttribute("keyword", keyword);
		return "request/request";
	}
	
	@GetMapping("/request-detail-{IDrequest}")
	public String viewRequestDetail(@PathVariable(name = "IDrequest") Integer IDrequest, 
			Model model, RedirectAttributes ra) {
		try {
			Request request = service.getRequest(IDrequest);
			model.addAttribute("request", request);
			return "request/request_detail";
		}catch(RequestNotFoundException ex){
			ra.addFlashAttribute("message", ex.getMessage());
			return "redirect:request";
		}
	}
	
}
