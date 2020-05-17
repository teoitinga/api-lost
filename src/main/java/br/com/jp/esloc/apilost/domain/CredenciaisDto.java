package br.com.jp.esloc.apilost.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CredenciaisDto {
	private String login;
	private String senha;
}
