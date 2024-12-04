package com.forumhub.forum_hub_aleph.infra.security.usuario;

public record UsuarioTicket(Long id, String nome, String email, String senha, String perfis) {
}
