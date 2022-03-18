package br.com.katho.vendas.rest.controller;

import br.com.katho.vendas.domain.entity.Cliente;
import br.com.katho.vendas.domain.repository.Clientes;
import org.apache.coyote.Response;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/clientes")
public class ClienteController {

    private Clientes clientes;

    public ClienteController(Clientes clientes) {
        this.clientes = clientes;
    }

    @GetMapping("/{id}")
    public Cliente getClienteById(@PathVariable Integer id) {
        return clientes
                .findById(id)
                .orElseThrow( () ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "Cliente não encontrado.")); //ESSA MSG É OPCIONAL NESSE EXCEPTION
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Cliente save(@RequestBody Cliente cliente) {
        return clientes.save(cliente);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        clientes.findById(id)
                .map(cliente -> {
                        clientes.delete(cliente);
                        return cliente;
                })
                .orElseThrow( () ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "Cliente não encontrado."));
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable Integer id,
                                 @RequestBody Cliente cliente) {
        clientes
                .findById(id) //findById() é um retorna um <Optional> e o optiona. permite utilizar o metodo map() abaixo.
                .map(clienteExistente ->  {
                    cliente.setId(clienteExistente.getId());//setamos o id pra garantir que o id foi retornado no findById()
                    clientes.save(cliente);
                    return clienteExistente;
                }).orElseThrow( () ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "Cliente não encontrado."));
        //acima é um suplier "() ->". Ela é uma classe funcional que tem um método que não tem nenhum parametro no "()"
        //e retorna qq coisa depois do "->"
    }

    @GetMapping
    // ESSE MÉTODO RETORNA TODOS OS REGISTROS BASEADOS NO FILTRO (ATRIBUTOS) QUE VEM NO OBJETO (Cliente filtro)
    public List<Cliente> find(Cliente filtro) {
        ExampleMatcher matcher = ExampleMatcher
                                    .matching()
                                    .withIgnoreCase()
                                    .withStringMatcher(
                                            ExampleMatcher.StringMatcher.CONTAINING);

        Example example = Example.of(filtro, matcher);

        return clientes.findAll(example);
    }

}
