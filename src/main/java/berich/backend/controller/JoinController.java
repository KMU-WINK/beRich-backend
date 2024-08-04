package berich.backend.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import berich.backend.dto.JoinDTO;
import berich.backend.service.JoinService;

@RestController
@RequestMapping("/auth")

public class JoinController {

	private final JoinService joinService;

	public JoinController(JoinService joinService) {
		this.joinService = joinService;
	}

	@PostMapping("/join")
	public String JoinProcess(@RequestBody JoinDTO joinDto) {

		joinService.saveUser(joinDto);
		return "ok";
	}
}
