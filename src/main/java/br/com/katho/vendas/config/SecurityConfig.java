package br.com.katho.vendas.config;

import br.com.katho.vendas.domain.service.impl.UsuarioServiceImpl;
import br.com.katho.vendas.security.jwt.JwtAuthFilter;
import br.com.katho.vendas.security.jwt.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UsuarioServiceImpl usuarioService;

    @Autowired
    private JwtService jwtService;

    //RESPONSAVEL POR ENCRIPTOGRAFAR E DESCRIPTOGRAFAR O PASSWORD A SENHA DO USUARIO
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();

        // TODA VEZ QUE O USUARIO PASSA A SENHA O BCryptPasswordEncoder() GERA UM HASH E SEMPRE QUE A SENHA PASSADA SEJA A MESMA
        // ESSE MÉTODO GERA UM HASH DIFERENTE. E POR ISSO É UMA CRIPTOGRAFIA BEM SEGURA
    }

    //AJUDA A INTERCEPTAR AS REQUISIÇÕES -> FILTRO JWT - TAMBÉM É IMPLEMENTADO NO METODO CONFIGURE ABAIXO
    @Bean
    public OncePerRequestFilter jwtFilter() {
        return new JwtAuthFilter(jwtService, usuarioService);
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
                .antMatchers(HttpMethod.POST, "/api/usuarios/**")
                .permitAll()
                .anyRequest().authenticated()
                .and()
                .sessionManagement() //agora não temos mais sessão e assim gerenciamos nossas requisições
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) //aqui deixamos nossas requests stateless e temos que passar o jwt filter
                .and()
                .addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class) //o filter before coloca o jwt filter no contexto e depois usa o Username para verificar as roles que esse usuario tem acesso para manter no contexto que ele foi inserido
        ;
    }
        @Override
        public void configure(WebSecurity web) throws Exception {
            web.ignoring().antMatchers(
                    "/v2/api-docs",
                    "/configuration/ui",
                    "/swagger-resources/**",
                    "/configuration/security",
                    "/swagger-ui.html",
                    "/webjars/**");
        }

        /**
         * csrf() - é uma configuração que haja segurança entre uma aplicação e o backend, mas aqui estamos trabalhando com api rest - stateless
         * .antMatchers() - aqui é definido quem acessa o quê dentro da aplicação
         * .authenticated() - esse metodo verifica quem está autenticado, independente da HOLE ou AUTHORITY, com tanto que você esteja autenticado
         * .and() - volta para a raiz do objeto http.
         * .formLogin() - é o formulário de login do spring boot - você pode usar ele ou pode criar um form de login customizado
         */

        /*
        Resumo da implementação do token jwt:

        1.
        Na class JwtService criamos toda a codificação e decodificação do token

        2.
        Na class JwtAuthFilter é o filtro onde interceptamos todas as requisições e obtermos o token do header - Authorization
        Verifica se o token é valido, verifica o usuario, coloca ele no contexto

        3.
        Colocamos na classe Security config o nosso filtro para ele interceptar de fato as requisições e colocar o uauario
            e o token dentro do contexto de fato.
         */

}
