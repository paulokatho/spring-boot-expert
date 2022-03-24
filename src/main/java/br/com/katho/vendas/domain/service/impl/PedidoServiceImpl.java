package br.com.katho.vendas.domain.service.impl;

import br.com.katho.vendas.domain.entity.Cliente;
import br.com.katho.vendas.domain.entity.ItemPedido;
import br.com.katho.vendas.domain.entity.Pedido;
import br.com.katho.vendas.domain.entity.Produto;
import br.com.katho.vendas.domain.repository.Clientes;
import br.com.katho.vendas.domain.repository.ItemsPedido;
import br.com.katho.vendas.domain.repository.Pedidos;
import br.com.katho.vendas.domain.repository.Produtos;
import br.com.katho.vendas.domain.service.PedidoService;
import br.com.katho.vendas.exception.RegraNegocioException;
import br.com.katho.vendas.rest.dto.ItemPedidoDTO;
import br.com.katho.vendas.rest.dto.PedidoDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PedidoServiceImpl implements PedidoService {

    private final Pedidos repository;
    private final Clientes clientesRepository;
    private final Produtos produtosRepository;
    private final ItemsPedido itemsPedidoRepository;

    @Override
    @Transactional
    public Pedido salvar(PedidoDTO dto) {
        Integer idCliente = dto.getCliente();
        Cliente cliente = clientesRepository
                .findById(idCliente)
                .orElseThrow( () -> new RegraNegocioException("Código de cliente inválido."));

        Pedido pedido = new Pedido();
        pedido.setTotal(dto.getTotal());
        pedido.setDataPedido(LocalDate.now());
        pedido.setCliente(cliente);

        List<ItemPedido> itemsPedido = converterItems(pedido, dto.getItems());
        repository.save(pedido);
        itemsPedidoRepository.saveAll(itemsPedido);
        pedido.setItens(itemsPedido);

        return pedido;
    }

    private List<ItemPedido> converterItems(Pedido pedido, List<ItemPedidoDTO> items){
        if(items.isEmpty()){
            throw new RegraNegocioException("Não é possível realizar um pedido sem items.");
        }

        return items
                .stream() //essa primeira stream é uma stream de DTO's que está sendo convertida para uma stream de <ItemPedido> e depois
                .map( dto -> { //Método map da stream retorna uma nova stream (stream contendo os novos items = ItemPedido e como faço isso?(com o .collect() lá embaixo
                    Integer idProduto = dto.getProduto();
                    Produto produto = produtosRepository
                            .findById(idProduto)
                            .orElseThrow(
                                    () -> new RegraNegocioException(
                                            "Código de produto inválido: "+ idProduto
                                    ));

                    ItemPedido itemPedido = new ItemPedido();
                    itemPedido.setQuantidade(dto.getQuantidade());
                    itemPedido.setPedido(pedido);
                    itemPedido.setProduto(produto);
                    return itemPedido;
                }).collect(Collectors.toList()); //para poder retornar a conversão do DTO para a stream ItemPedido

    }
}
