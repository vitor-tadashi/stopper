package br.com.verity.pause.util;

import java.sql.Time;
import java.text.DecimalFormat;

import org.joda.time.LocalTime;
import org.joda.time.Period;

public class CalculoHoras {

	public void calcular(Time entrada, Time saida){
		LocalTime hrEntrada = new LocalTime(entrada);
		LocalTime hrSaida = new LocalTime(saida);
		Period hrDecimal = new Period(hrEntrada, hrSaida);
		
		int horas = hrDecimal.getHours();
		int minutos = hrDecimal.getMinutes();
		int segundos = hrDecimal.getSeconds();
		
		DecimalFormat formatDecimal = new DecimalFormat( "0.00" );
		Double totalDecimal = Double.parseDouble(formatDecimal.format(horas + (( minutos / 60.00)+(segundos / 3600.00))).replace(",", "."));
		
		System.out.println(totalDecimal);
	}
}
