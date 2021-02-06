package br.com.zup.treino_mercado_ivre.email;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public interface Mailer {

    /**
     * @Param body
     * @Param subject
     * @Param nameFrom Nome para aperecer no provedor do email
     * @Param from
     * @param to
     * */
    void send(@NotBlank String body, @NotBlank String subject, @NotBlank String nameFrom, @NotBlank @Email String from,@NotBlank @Email String to);
}
