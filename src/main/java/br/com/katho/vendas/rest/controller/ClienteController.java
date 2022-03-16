package br.com.katho.vendas.rest.controller;

import br.com.katho.vendas.domain.entity.Cliente;
import br.com.katho.vendas.domain.repository.Clientes;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("api/clientes")
public class ClienteController {

    private Clientes clientes;

    public ClienteController(Clientes clientes) {
        this.clientes = clientes;
    }

    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity getClienteById(@PathVariable Integer id) {
        Optional<Cliente> cliente = clientes.findById(id);

        if(cliente.isPresent()) {
            return ResponseEntity.ok(cliente.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping()
    @ResponseBody
    public ResponseEntity save(@RequestBody Cliente cliente) {
        Cliente clienteSalvo = clientes.save(cliente);

        return ResponseEntity.ok(clienteSalvo);
    }

    @DeleteMapping("{id}")
    @ResponseBody
    public ResponseEntity delete(@PathVariable Integer id) {
        Optional<Cliente> cliente = clientes.findById(id);

        if (cliente.isPresent()) {
            clientes.delete(cliente.get());
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("{id}")
    @ResponseBody
    public ResponseEntity update(@PathVariable Integer id,
                                 @RequestBody Cliente cliente) {
        return clientes
                .findById(id) //findById() é um retorna um <Optional> e o optiona. permite utilizar o metodo map() abaixo.
                .map(clienteExistente ->  {
                    cliente.setId(clienteExistente.getId());//setamos o id pra garantir que o id foi retornado no findById()
                    clientes.save(cliente);
                    return ResponseEntity.noContent().build();
                }).orElseGet( () -> ResponseEntity.notFound().build());
        //acima é um suplier "() ->". Ela é uma classe funcional que tem um método que não tem nenhum parametro no "()"
        //e retorna qq coisa depois do "->"
    }

}
