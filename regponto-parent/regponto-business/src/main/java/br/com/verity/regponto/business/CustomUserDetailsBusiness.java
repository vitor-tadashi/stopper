package br.com.verity.regponto.business;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.verity.regponto.bean.CustomUserDetails;
import br.com.verity.regponto.bean.UsuarioBean;

@Service
public class CustomUserDetailsBusiness implements UserDetailsService {

	
	@Transactional
	@Override
	public UserDetails loadUserByUsername(String usuario) throws UsernameNotFoundException {
//		Optional<UsuarioEntity> optionalUsuarios = usuarioDAO.findByUsuario(usuario);
//		
//		optionalUsuarios.orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
//		
//		UsuarioBean usuarioBean = new UsuarioBean();
//		
//		usuarioBean = usuarioConverter.convertEntityToBean(optionalUsuarios.get());
//		usuarioBean.setFuncionario(funcionarioConverter.convertEntityToBean(optionalUsuarios.get().getFuncionario()));
//		usuarioBean.setPerfis(perfilConverter.convertEntityToBean(optionalUsuarios.get().getPerfis()));
//		for(int i = 0; i < usuarioBean.getPerfis().size();i++) {
//			usuarioBean.getPerfis().get(i).setFuncionalidades(permissaoConverter.convertEntityToBean(
//					optionalUsuarios.get().getPerfis().get(i).getPermissoes()));
//		}
//		//perfil deve estar associado a um sistema
//		//TODO retornar uma lista com apenas um perfil, aquele que ele está acessando.
		
		UsuarioBean usuarioBean = new UsuarioBean();
		
		usuarioBean.setUsuario("Guilherme");
		usuarioBean.setSenha("xablau");
		usuarioBean.setAtivo(true);
		
		return new CustomUserDetails(usuarioBean);
	}
}
