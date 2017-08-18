package br.com.verity.regponto.business;

import javax.transaction.Transactional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.verity.regponto.bean.CustomUserDetails;
import br.com.verity.regponto.bean.UsuarioBean;
import br.com.verity.regponto.integration.SavIntegration;

@Service
public class CustomUserDetailsBusiness implements UserDetailsService {
	
	@Transactional
	@Override
	public UserDetails loadUserByUsername(String usuario) throws UsernameNotFoundException{
		SavIntegration sav = new SavIntegration();
		UsuarioBean usuarioBean = sav.getUsuario(usuario);
		if(usuarioBean.getId() == null){
			throw new UsernameNotFoundException("Usuário não localizado");
		}
		return new CustomUserDetails(usuarioBean);
	}
}
