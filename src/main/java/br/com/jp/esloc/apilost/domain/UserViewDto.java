package br.com.jp.esloc.apilost.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserViewDto {
	private Long id;
	private String apelido;
	private String role;
}
