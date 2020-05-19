package br.com.jp.esloc.apilost.resources.exceptions;

import java.util.Arrays;
import java.util.List;

import lombok.Getter;

public class ApiErrors {

	@Getter
	private List<String> errors;

	public ApiErrors(String errors) {
		this.errors = Arrays.asList(errors);
	}

	public ApiErrors(List<String> errors) {
		this.errors = errors;
	}
}