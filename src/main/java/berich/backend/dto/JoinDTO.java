package berich.backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;


@Getter
public class JoinDTO {

	@NotBlank
	private String username;

	@Email
	@NotBlank
	private String email;

	@NotBlank
	private String password;

}
