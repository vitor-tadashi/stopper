package br.com.verity.pause.bean;

import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class ApontamentosListWrapperBean{

	private List<ApontamentosBean> apontamentos;

	public List<ApontamentosBean> getApontamentos() {
		return apontamentos;
	}

	public void setApontamentos(List<ApontamentosBean> apontamentos) {
		this.apontamentos = apontamentos;
	}
}