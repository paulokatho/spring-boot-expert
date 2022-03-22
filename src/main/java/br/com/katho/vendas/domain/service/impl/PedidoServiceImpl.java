package br.com.katho.vendas.domain.service.impl;

import br.com.katho.vendas.domain.repository.Pedidos;
import br.com.katho.vendas.domain.service.PedidoService;
import org.springframework.stereotype.Service;

@Service
public class PedidoServiceImpl implements PedidoService {

    private Pedidos repository;

    public PedidoServiceImpl(Pedidos repository) {
        this.repository = repository;
    }
}
