package br.com.jp.esloc.apilost.config;

import java.util.ArrayList;
import java.util.List;

public class LostResponse<T> {
	private T data;
	private List<Exception> errors;
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	public List<Exception> getErrors() {
		if (this.errors == null) {
			this.errors = new ArrayList<Exception>();
		}
		return errors;
	}
	public void setErrors(List<Exception> errors) {
		this.errors = errors;
	}

}
