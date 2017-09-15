package br.com.verity.pause.bean;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class CustomUserDetails extends UsuarioBean implements UserDetails {

	private static final long serialVersionUID = 1L;

	public CustomUserDetails(final UsuarioBean usuario) {
		super(usuario);
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return getPerfis().get(0).getFuncionalidades().stream().map(role -> new SimpleGrantedAuthority(
				"ROLE_" + role.getNome().toUpperCase().replaceAll(" ", "_"))).collect(Collectors.toList());
	}

	@Override
	public String getPassword() {
		return super.getSenha();
	}

	@Override
	public String getUsername() {
		return super.getUsuario();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return super.getAtivo();
	}

}
