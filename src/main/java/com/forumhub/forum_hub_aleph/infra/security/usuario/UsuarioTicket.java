package com.forumhub.forum_hub_aleph.infra.security.usuario;

public record UsuarioTicket(String login, String senha) {

    public UsuarioTicket(UsuarioLogin usuarioLogin) {
        this(usuarioLogin.getLogin(), usuarioLogin.getSenha());
    }

}
