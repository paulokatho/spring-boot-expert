package br.com.katho.vendas.rest.controller;

import br.com.katho.vendas.exception.PedidoNaoEncontradoException;
import br.com.katho.vendas.exception.RegraNegocioException;
import br.com.katho.vendas.rest.ApiErrors;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ApplicationControllerAdvice {

    @ExceptionHandler(RegraNegocioException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handleRegraNegocioException(RegraNegocioException ex) {
        String mensagemErro = ex.getMessage();
        return new ApiErrors(mensagemErro);
    }

    @ExceptionHandler(PedidoNaoEncontradoException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiErrors handlePedidoNotFoundException(PedidoNaoEncontradoException ex) {
        return new ApiErrors(ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handleMethodNotValidException(MethodArgumentNotValidException ex) {
        // getBindingResult() - carrega os dados de validação e o que foi que falhou
        // getDefaultMessage() - busca a mensagem que colocamos na validação do nosso bean
        // collect() - conseguimos coletar somente a mensagem e não vem o array inteiro de error que o spring retorna

        List<String> errors = ex.getBindingResult().getAllErrors()
                .stream()
                .map(erro -> erro.getDefaultMessage())
                .collect(Collectors.toList());
        return  new ApiErrors(errors); // TEM QUE CRIAR O CONSTRUTOR PARA RETORNAR O List<String> errors
    }
}
