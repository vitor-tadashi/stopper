package br.com.verity.pause.exception;

import java.util.List;

public class BusinessException extends Exception {

	private static final long serialVersionUID = 1157185811664602433L;

	private List<String> erros;
	
	public BusinessException(String message) {
		super(message);
	}
	
	public BusinessException(String message,List<String> erros){
		super(message);
		this.erros = erros;
	}
	public List<String> getErros() {
		return erros;
	}
}