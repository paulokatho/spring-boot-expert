package br.com.katho.vendas.domain.repository;

import br.com.katho.vendas.domain.entity.ItemPedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemsPedido extends JpaRepository <ItemPedido, Integer> {
}
