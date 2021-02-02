package br.com.zup.treino_mercado_ivre.validators;

import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class ExistValueValidator implements ConstraintValidator<ExistValue, Object> {
    private Class<?> domainClass;
    private String fieldName;

    @PersistenceContext
    private EntityManager manager;

    @Override
    public void initialize(ExistValue param) {
        domainClass = param.domainClass();
        fieldName = param.fieldName();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if(value==null){
            return true;
        }

        Query query = manager.createQuery("SELECT 1 FROM " + domainClass.getName() + " WHERE " + fieldName + " = :value ")
                             .setParameter("value", value);
        List<?> list = query.getResultList();
        Assert.isTrue(list.size()<=1, "Existe mais de um " + domainClass.getName() + " com atributo " + fieldName);

        return !list.isEmpty();
    }
}
