package br.com.katho.vendas.domain.repository;

import br.com.katho.vendas.domain.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Pedidos extends JpaRepository <Pedido, Integer> {
}
