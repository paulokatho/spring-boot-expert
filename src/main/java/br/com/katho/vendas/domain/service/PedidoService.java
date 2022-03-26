package br.com.katho.vendas.domain.service;

import br.com.katho.vendas.domain.entity.Pedido;
import br.com.katho.vendas.rest.dto.PedidoDTO;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface PedidoService {

    Pedido salvar(PedidoDTO dto);

    Optional<Pedido> obterPedidoCompleto(Integer id);
}
