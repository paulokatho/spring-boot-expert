package br.com.katho.vendas.domain.repository;

import br.com.katho.vendas.domain.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Produtos extends JpaRepository <Produto, Integer> {
}
