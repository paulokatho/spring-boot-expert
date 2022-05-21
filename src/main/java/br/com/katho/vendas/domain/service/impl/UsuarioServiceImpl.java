package br.com.katho.vendas.domain.service.impl;

import br.com.katho.vendas.domain.entity.Usuario;
import br.com.katho.vendas.domain.repository.UsuarioRepository;
import br.com.katho.vendas.exception.SenhaInvalidaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class UsuarioServiceImpl implements UserDetailsService {

    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private UsuarioRepository repository;

    @Transactional
    public Usuario salvar(Usuario usuario) {
        return repository.save(usuario);
    }

    public UserDetails autenticar(Usuario usuario) {
        UserDetails user = loadUserByUsername(usuario.getLogin());
    //TEM QUE SEMPRE SEGUIR ESSA ORDEM: COMPARAR A VINDA DO FRONT COM A SENHA DO BANCO DE DADOS
        boolean senhasBatem = encoder.matches(usuario.getSenha(), user.getPassword());
        if(senhasBatem) {
            return user;
        }
        throw new SenhaInvalidaException(); //se o usuario não estiver autenticado gera essa exception e não deixa o cara logar
    }

    // ESSE CARA SERVE PARA DEFINIR O CARREGAMENTO DE UM USUARIO ATRAVES DA BASE DE DADOS
    // O OBJETIVO DESSA CLASSE É CARREGAR O USUARIO ATRAVÉS DO SEU LOGIN
    // ANTES O USUARIO ERA PEGADO DA MEMÓRIA NO SECURITYCONFIG E AGORA O AuthenticationManagerBuilder vai pegar dessa classe
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
      Usuario usuario = repository.findByLogin(userName)
              .orElseThrow(() ->
              new UsernameNotFoundException("Usuário não encontrado na base de dados."));

      String[] roles = usuario.isAdmin() ?
              new String[]{"ADMIN", "USER"} : new String[]{"USER"};

      return User
              .builder()
              .username(usuario.getLogin())
              .password(usuario.getSenha())
              .roles(roles)
              .build();
    }
}
