package br.com.verity.pause.business;

import java.util.Arrays;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.verity.pause.bean.FeriadoBean;
import br.com.verity.pause.integration.SavIntegration;

@Service
public class FeriadoBusiness {
	
	@Autowired
	private SavIntegration savIntegration;
	
	/**
     * Verifica se data á sábado ou domingo.
     *
     * @param   data Um objeto Calendar
     * @return  Calendar
     */
	public Boolean verificarFeriado(Date data)
    {
    	Boolean response = false;
    	
    	try {
    		//TODO colocar em cache
    		Optional<FeriadoBean> optional = Arrays.stream(savIntegration.listFeriados().toArray(new FeriadoBean[0]))
    		         .filter(x -> x.getDataFeriado().compareTo(data) == 0).findFirst();
    		        
    		response = optional.isPresent();
    	} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
        return response;
    }
}
