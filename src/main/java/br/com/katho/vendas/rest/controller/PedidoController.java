package br.com.katho.vendas.rest.controller;

import br.com.katho.vendas.domain.entity.Pedido;
import br.com.katho.vendas.domain.service.PedidoService;
import br.com.katho.vendas.rest.dto.PedidoDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    private PedidoService service;

    public PedidoController(PedidoService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public Integer save( @RequestBody PedidoDTO dto ){
        Pedido pedido = service.salvar(dto);
        return pedido.getId();
    }

}
