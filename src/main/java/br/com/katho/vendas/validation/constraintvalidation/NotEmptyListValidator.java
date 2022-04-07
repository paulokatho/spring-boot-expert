package br.com.katho.vendas.validation.constraintvalidation;

import br.com.katho.vendas.validation.NotEmptyList;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class NotEmptyListValidator implements ConstraintValidator<NotEmptyList, List> {


    @Override// TEM QUE IMPLEMENTAR ESSES 2 MÉTODOS PARA REALIZAR UMA VALIDAÇÃO DE List<> de PedidoDTO
    // Aula: spring boot expert - 63 - Criando uma annotation de validação customizada
    public boolean isValid(List list, ConstraintValidatorContext constraintValidatorContext) {
        return list != null && !list.isEmpty();
    }

    @Override
    public void initialize(NotEmptyList constraintAnnotation) {
        // AQUI PODEMOS IMPLEMENTAR OUTROS ELEMENTOS DA ANOTATION, NESSE CASO SÓ VAMOS TER O message()
    }
}
