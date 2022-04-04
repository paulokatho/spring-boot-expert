package br.com.katho.vendas.config;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    // ESSE CARA VAI TRAZER OS OBJETOS QUE VÃO FAZER A AUTENTICAÇÃO DOS USUÁRIOS
    // DENTRO DO CONTEXTO DO SECURITY
    // VERIFICA SENHA DO USUARIO E A AUTENTICAÇÃO DELE
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        super.configure(auth);
    }

    // ESSA PARTE É A DE AUTORIZAÇÃO E A IDEIA É:
    // PEGUE ESSE USUÁRIO QUE ESTÁ AUTENTICADO E VERIFIQUE SE ELE TEM AUTORIZAÇÃO PARA ACESSAR ESSA PÁGINA EX:
    // API DE CLIENTES, QUEM TEM A "HOLE" E "AUTHORITY" PARA ACESSA A API DE CLIENTES
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        super.configure(http);
    }
}
