package com.anx.reconciliation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RestController
public class ApiController {
	
	@PostMapping("/multiplefileupload")
	public ResponseEntity<?> changeEmailCode(@RequestBody String requestBody,
			@RequestParam("fileAddress") MultipartFile[] files, RedirectAttributes redirectAttributes) {
		if (files.length == 0) {

			return ResponseEntity.ok("test");
		}
		return ResponseEntity.ok("test");
	}
}
