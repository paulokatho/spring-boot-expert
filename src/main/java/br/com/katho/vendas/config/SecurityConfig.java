package br.com.katho.vendas.config;

import br.com.katho.vendas.domain.service.impl.UsuarioServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UsuarioServiceImpl usuarioService;

    //RESPONSAVEL POR ENCRIPTOGRAFAR E DESCRIPTOGRAFAR O PASSWORD A SENHA DO USUARIO
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();

        // TODA VEZ QUE O USUARIO PASSA A SENHA O BCryptPasswordEncoder() GERA UM HASH E SEMPRE QUE A SENHA PASSADA SEJA A MESMA
        // ESSE MÉTODO GERA UM HASH DIFERENTE. E POR ISSO É UMA CRIPTOGRAFIA BEM SEGURA
    }

    // ESSE CARA VAI TRAZER OS OBJETOS QUE VÃO FAZER A AUTENTICAÇÃO DOS USUÁRIOS
    // DENTRO DO CONTEXTO DO SECURITY
    // VERIFICA SENHA DO USUARIO E A AUTENTICAÇÃO DELE
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
            .userDetailsService(usuarioService)
            .passwordEncoder(passwordEncoder());

    }

    // ESSA PARTE É A DE AUTORIZAÇÃO E A IDEIA É:
    // PEGUE ESSE USUÁRIO QUE ESTÁ AUTENTICADO E VERIFIQUE SE ELE TEM AUTORIZAÇÃO PARA ACESSAR ESSA PÁGINA EX:
    // API DE CLIENTES, QUEM TEM A "HOLE" E "AUTHORITY" PARA ACESSAr A API DE CLIENTES
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                    .antMatchers("/api/clientes/**")
                        .hasAnyRole("USER", "ADMIN")
                    .antMatchers("/api/pedidos/**")
                        .hasAnyRole("USER", "ADMIN")
                    .antMatchers("/api/produtos**")
                        .hasAnyRole("ADMIN")
                .and()
                    .httpBasic();

        /**
         * csrf() - é uma configuração que haja segurança entre uma aplicação e o backend, mas aqui estamos trabalhando com api rest - stateless
         * .antMatchers() - aqui é definido quem acessa o quê dentro da aplicação
         * .authenticated() - esse metodo verifica quem está autenticado, independente da HOLE ou AUTHORITY, com tanto que você esteja autenticado
         * .and() - volta para a raiz do objeto http.
         * .formLogin() - é o formulário de login do spring boot - você pode usar ele ou pode criar um form de login customizado
         */
    }
}
