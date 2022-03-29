package br.com.katho.vendas.domain.repository;

import br.com.katho.vendas.domain.entity.Cliente;
import br.com.katho.vendas.domain.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Produtos extends JpaRepository <Produto, Integer> {

    @Query(value = " Select p from Produto p where p.descricao like :descricao")
    List<Cliente> buscarPorDescricao(@Param("descricao") String descricao);

}
