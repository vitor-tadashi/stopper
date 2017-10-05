package br.com.verity.pause.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.verity.pause.bean.CustomUserDetails;
import br.com.verity.pause.bean.UsuarioBean;
import br.com.verity.pause.integration.SavIntegration;

@Service
public class CustomUserDetailsBusiness implements UserDetailsService {
	
	@Autowired
	private SavIntegration sav;
	
	@Override
	public UserDetails loadUserByUsername(String usuario) throws UsernameNotFoundException{
		UsuarioBean usuarioBean = sav.getUsuario(usuario);
		if(usuarioBean.getId() == null){
			throw new UsernameNotFoundException("Usuário não localizado");
		}
		return new CustomUserDetails(usuarioBean);
	}
	public UsuarioBean usuarioLogado(){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UsuarioBean usuarioLogado = (UsuarioBean) auth.getPrincipal();
		return usuarioLogado;
	}
}
