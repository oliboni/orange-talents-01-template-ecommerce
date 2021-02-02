package br.com.zup.treino_mercado_ivre.validators;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class UniqueValueValidator implements ConstraintValidator<UniqueValue, Object> {
    private Class<?> domainClass;
    private String attribute;

    @PersistenceContext
    private EntityManager manager;

    @Override
    public void initialize(UniqueValue param) {
        domainClass = param.domainClass();
        attribute = param.fieldName();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        Query query = manager.createQuery("SELECT 1 FROM " + domainClass.getName() + " WHERE " + attribute + " = :value ")
                             .setParameter("value", value);

        List<?> list = query.getResultList();

        return list.isEmpty();
    }
}
