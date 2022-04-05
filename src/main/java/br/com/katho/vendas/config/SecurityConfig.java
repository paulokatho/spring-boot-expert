package br.com.katho.vendas.config;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

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
