package br.com.katho.vendas.domain.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioServiceImpl implements UserDetailsService {

    @Autowired
    private PasswordEncoder encoder;

    // ESSE CARA SERVE PARA DEFINIR O CARREGAMENTO DE UM USUARIO ATRAVES DA BASE DE DADOS
    // O OBJETIVO DESSA CLASSE É CARREGAR O USUARIO ATRAVÉS DO SEU LOGIN
    // ANTES O USUARIO ERA PEGADO DA MEMÓRIA NO SECURITYCONFIG E AGORA O AuthenticationManagerBuilder vai pegar dessa classe
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        if(!userName.equals("cicrano")) {
            throw new UsernameNotFoundException(("Usuário não encontrado na base."));
        }

        return User
                .builder()
                .username("cicrano")
                .password(encoder.encode("123"))
                .roles("USER", "ADMIN")
                .build();
    }
}
