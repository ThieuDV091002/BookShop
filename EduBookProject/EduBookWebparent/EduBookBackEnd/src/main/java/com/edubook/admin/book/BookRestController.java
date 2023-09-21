package com.edubook.admin.book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookRestController {

	@Autowired
	private BookService service;
	
	@PostMapping("/book-check_tensach")
	public String checkDuplicateTensach(@Param("tensach") String tensach) {
		return service.isTensachUnique(tensach) ? "OK" : "Duplicated";
	}
}
