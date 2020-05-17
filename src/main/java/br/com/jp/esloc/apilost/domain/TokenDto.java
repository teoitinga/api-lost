package br.com.jp.esloc.apilost.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TokenDto {
	private String login;
	private String nome;
	private String token;
}
