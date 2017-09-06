package br.com.verity.pause.util;

import org.springframework.stereotype.Component;

@Component
public class VerificarData {

	public  static String qualDia(Integer dia){
		if(dia == 0){
			return "Domingo";
		}else if(dia == 1){
			return "Segunda-Feira";
		}else if(dia == 2){
			return "Terça-Feira";
		}else if(dia == 3){
			return "Quarta-Feira";
		}else if(dia == 4){
			return "Quinta-Feira";
		}else if(dia == 5){
			return "Sexta-Feira";
		}else{
			return "Sábado";
		}
	}
	
	public String qualDiaSimples(Integer dia){
		if(dia == 0){
			return "Dom";
		}else if(dia == 1){
			return "Seg";
		}else if(dia == 2){
			return "Ter";
		}else if(dia == 3){
			return "Qua";
		}else if(dia == 4){
			return "Qui";
		}else if(dia == 5){
			return "Sex";
		}else{
			return "Sáb";
		}
	}
	
	public String qualSemestre(Integer mes){
		if(mes == 1 || mes == 2 || mes == 3){
			return "1º";
		}else if(mes == 4 || mes == 5 || mes == 6){
			return "2º";
		}else if(mes == 7 || mes == 8 || mes == 9){
			return "3º";
		}else{
			return "4º";
		}
	}
}
