package br.com.katho.vendas.rest.dto;

import br.com.katho.vendas.domain.entity.ItemPedido;

import java.math.BigDecimal;
import java.util.List;

public class PedidoDTO {

    private Integer cliente;
    private BigDecimal total;
    private List<ItemPedidoDTO> items;
}
