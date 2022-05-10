package br.com.katho.vendas;

import br.com.katho.vendas.domain.entity.Usuario;
import ch.qos.logback.core.net.SyslogOutputStream;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
public class JwtService {

    @Value("${security.jwt.expiracao}")
    private String expiracao;

    @Value("${security.jwt.chave-assinatura}")
    private String chaveAssinatura;

    public String gerarToken(Usuario usuario) { //gerar token jwt
        long expString = Long.valueOf(expiracao); //valor de 30 minutos que foi declaro no file application.properties
        LocalDateTime dataHoraExpiracao = LocalDateTime.now().plusMinutes(expString); //pega a data atual do servidor e acrescenta os 30 minutos
        Instant instant = dataHoraExpiracao.atZone(ZoneId.systemDefault()).toInstant(); //pega os 30 minutos depois da hora atual do servidor default (nesse caso Brasil)
        Date data = Date.from(instant);
        return Jwts
                .builder()
                .setSubject(usuario.getLogin()) //podemos colocar qq coisa, mas o ideal é o login do usuario(esse é o payload do jwt). E também sabemos facil quem está fazendo a requisicao
                .setExpiration(data)
                .signWith(SignatureAlgorithm.HS512, chaveAssinatura) //parametro 1 é o algoritmo, o HS512 é bem seguro. Parametro 2 é a chave de assinatura do token. Essa parte é muito importante
                .compact(); //esse cara é que vai retornar uma string, transforma o token na string
    }

    public static void main(String[] args) {
        ConfigurableApplicationContext contexto = SpringApplication.run(VendasApplication.class); //inicia nossa aplicação dentro de um contexto
        JwtService service = contexto.getBean(JwtService.class); //a partir do contexto, conseguimos acesso a qq @Bean e nesse caso é o JwtService para poder injetar a <expiracao e chave de assinatura)
        Usuario usuario = Usuario.builder().login("fulano").build();
        String token = service.gerarToken(usuario);
        System.out.println(token);
    }
}
