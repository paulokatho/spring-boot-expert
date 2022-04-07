package br.com.katho.vendas.validation;

import br.com.katho.vendas.validation.constraintvalidation.NotEmptyListValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)// PARA VERIFICAR EM TEMPO DE EXECUÇÃO
@Target(ElementType.FIELD)// O TARGET VAI DIZER ONDE PODEMOS COLOCAR A ANOTATION - NESSE CASO VAI SER EM CIMA DE UM CAMPO
@Constraint(validatedBy = NotEmptyListValidator.class)// ESSE CARA QUE VAI DIZER QUE ESSA É UMA ANOTAÇÃO DE VALIDAÇÃO. É a classe que criamos
public @interface NotEmptyList {

    String message() default "A lista não pode ser vazia.";

    // ESSES 2 METODOS ABAIXO SÃO OBRIGATÓRIOS E DENTRO DO @NotNull, por exemplo, podemos ver que eles são obrigatórios.
    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
