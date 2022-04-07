package br.com.katho.vendas.rest.controller;

import br.com.katho.vendas.domain.entity.Cliente;
import br.com.katho.vendas.domain.entity.Produto;
import br.com.katho.vendas.domain.repository.Clientes;
import br.com.katho.vendas.domain.repository.Produtos;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/produtos")
public class ProdutoController {

    private Produtos produtos;

    public ProdutoController(Produtos produtos) {
        this.produtos = produtos;
    }

    @GetMapping("/{id}")
    public Produto getProdutoById(@PathVariable Integer id) {
        return produtos
                .findById(id)
                .orElseThrow( () ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "Produto não encontrado.")); //ESSA MSG É OPCIONAL NESSE EXCEPTION
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Produto save(@RequestBody @Valid Produto produto) {
        return produtos.save(produto);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        produtos.findById(id)
                .map(produto -> {
                    produtos.delete(produto);
                        return produto;
                })
                .orElseThrow( () ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "Produto não encontrado."));
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable @Valid Integer id,
                                 @RequestBody Produto produto) {
        produtos
                .findById(id) //findById() é um retorna um <Optional> e o optiona. permite utilizar o metodo map() abaixo.
                .map(produtoExistente ->  {
                    produto.setId(produtoExistente.getId());//setamos o id pra garantir que o id foi retornado no findById()
                    produtos.save(produto);
                    return produtoExistente;
                }).orElseThrow( () ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "Produto não encontrado."));
        //acima é um suplier "() ->". Ela é uma classe funcional que tem um método que não tem nenhum parametro no "()"
        //e retorna qq coisa depois do "->"
    }

    @GetMapping
    // ESSE MÉTODO RETORNA TODOS OS REGISTROS BASEADOS NO FILTRO (ATRIBUTOS) QUE VEM NO OBJETO (Produto filtro)
    public List<Produto> find(Produto filtro) {
        ExampleMatcher matcher = ExampleMatcher
                                    .matching()
                                    .withIgnoreCase()
                                    .withStringMatcher(
                                            ExampleMatcher.StringMatcher.CONTAINING);

        Example example = Example.of(filtro, matcher);

        return produtos.findAll(example);
    }

}
