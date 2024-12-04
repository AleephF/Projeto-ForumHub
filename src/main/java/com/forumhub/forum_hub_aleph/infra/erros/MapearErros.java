package com.forumhub.forum_hub_aleph.infra.erros;

import org.springframework.validation.FieldError;

public record MapearErros(String campo, String mensagem) {
    public MapearErros(FieldError erro){
        this(erro.getField(), erro.getDefaultMessage());
    }
}
