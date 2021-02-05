package br.com.zup.treino_mercado_ivre.validators;

import br.com.zup.treino_mercado_ivre.produto.NovoProdutoRequest;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class ProibeCaracteristicasComNomesIguaisValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return NovoProdutoRequest.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if (errors.hasErrors()){
            return;
        }

        NovoProdutoRequest request = (NovoProdutoRequest) target;
        if (request.temCaracteristicasIguais()){
            errors.rejectValue("caracteristicas",null,"Existem caracteristicas com nomes iguais!");
        }
    }
}
