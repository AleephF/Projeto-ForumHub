package com.forumhub.forum_hub_aleph.infra.security.authentication;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.forumhub.forum_hub_aleph.infra.security.usuario.UsuarioLogin;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${api.forumhub.security.tokenservice.chavesecreta}")
    private String chaveSecreta;

    public String gerarToken(UsuarioLogin usuarioLogin) {
        try {
            var algoritmo = Algorithm.HMAC256(chaveSecreta);
            return JWT.create()
                    .withIssuer("api-forumhub")
                    .withSubject(usuarioLogin.getLogin())
                    .withExpiresAt(tempoToken())
                    .sign(algoritmo);
        } catch (JWTCreationException exception){
            throw new RuntimeException("Erro na geração do token", exception);
        }
    }

    private Instant tempoToken() {
        return LocalDateTime.now().plusMinutes(30).toInstant(ZoneOffset.of("-03:00"));
    }

    public String getSubject(String token) {
        try {
            var algoritmo = Algorithm.HMAC256(chaveSecreta);
            return JWT.require(algoritmo)
                    .withIssuer("api-forumhub")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException exception){
            throw new RuntimeException("Token não enviado, inválido ou expirado!" + token);
        }
    }
}
