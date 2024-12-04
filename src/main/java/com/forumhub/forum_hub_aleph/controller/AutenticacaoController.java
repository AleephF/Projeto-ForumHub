package com.forumhub.forum_hub_aleph.controller;


import com.forumhub.forum_hub_aleph.infra.security.authentication.AutenticarAcesso;
import com.forumhub.forum_hub_aleph.infra.security.authentication.DadosTokenJWT;
import com.forumhub.forum_hub_aleph.infra.security.authentication.TokenService;
import com.forumhub.forum_hub_aleph.infra.security.usuario.UsuarioLogin;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AutenticacaoController {

    @Autowired
    private AuthenticationManager autenticacao;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity efetuarLogin(@RequestBody @Valid AutenticarAcesso autenticarAcesso){
        try {
            var token = new UsernamePasswordAuthenticationToken(autenticarAcesso.login(), autenticarAcesso.senha());

            var logar = autenticacao.authenticate(token);
            var tokenJWT = tokenService.gerarToken((UsuarioLogin) logar.getPrincipal());

            return ResponseEntity.ok(new DadosTokenJWT(tokenJWT));
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}