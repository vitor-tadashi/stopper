package br.com.verity.pause.bean;

import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class HorasListWrapperBean{

	private List<HorasBean> horas;

	public List<HorasBean> getHoras() {
		return horas;
	}

	public void setHoras(List<HorasBean> horas) {
		this.horas = horas;
	}
}