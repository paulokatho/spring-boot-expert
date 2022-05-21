package br.com.katho.vendas.security.jwt;

import br.com.katho.vendas.domain.service.impl.UsuarioServiceImpl;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//ESSA CLASSE VAI INTERCEPTAR UMA REQUISIÇÃO E SE O USUARIO ESTIVIER AUTENTICADO VAI COLOCAR ESSE USUARIO NO CONTEXTO DO SPRING SECURITY
//E QUANDO ELE ENTRAR NO METODO doFilter() ELE JÁ VAI ACRESCENTAR AS AUTHORITIES DESSE USUARIO
//DEPOIS DE IMPLEMENTADA ESSA CLASSE, ELA TEM QUE SER CONFIGURADA PARA PODER INTERCEPTAR AS REQUISIÇÕES
public class JwtAuthFilter extends OncePerRequestFilter {

    private JwtService jwtService;
    private UsuarioServiceImpl usuarioService;

    public JwtAuthFilter(JwtService jwtService, UsuarioServiceImpl usuarioService) {
        this.jwtService = jwtService;
        this.usuarioService = usuarioService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse,
            FilterChain filterChain) throws ServletException, IOException {

        //Como o token vem do front ou cliente
        // Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJmdWxhbm8iLCJleHAiOjE2NTI4ODkzOTR9.gYw1whpZLH3DNAq2lswgqDsbXNDhmcqXYBPGFDuqKigKhOGbUNNuQ5i0EgUzS_JHBpb-eKTr4VJyhy8qGIsgDw
        String authorization = httpServletRequest.getHeader("Authorization"); //http request consegue pegar qualquer propriedade do header nesse caso

        if(authorization != null && authorization.startsWith("Bearer")) { //Bearer é o que vem no inicio do header
            String token = authorization.split(" ") [1]; //dá um split onde tiver " " (espaço) e pega a posição 1 que é o token em si.
            boolean isValid = jwtService.tokenValido(token);

            if(isValid) {
                String loginUsuario = jwtService.obterLoginUsuario(token);
                UserDetails usuario = usuarioService.loadUserByUsername(loginUsuario); //aqui já pegamos o usuario
                //agora vamos pegar esse usuario e vamos colocar dentro do contexto do spring security
                //a partir do nosso user details estamos criando uma instância de UsernamePasswordAuthenticationToken
                //esse token abaixo não é o token que o usuario envia
                UsernamePasswordAuthenticationToken user = new
                        UsernamePasswordAuthenticationToken(usuario, null,
                        usuario.getAuthorities());

                //essa linha abaixo diz para o contexto do spring security que se trata de uma aplicação e autenticação web
                user.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));

                SecurityContextHolder.getContext().setAuthentication(user);
            }
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
