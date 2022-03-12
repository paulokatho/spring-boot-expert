package br.com.katho.vendas.domain.repository;

import br.com.katho.vendas.domain.entity.Cliente;
import br.com.katho.vendas.domain.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface Pedidos extends JpaRepository <Pedido, Integer> {

    List<Pedido> findByCliente(Cliente cliente);
}
