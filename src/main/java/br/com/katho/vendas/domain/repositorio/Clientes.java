package br.com.katho.vendas.domain.repositorio;

import br.com.katho.vendas.domain.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Clientes extends JpaRepository<Cliente, Integer> {

    List<Cliente> findByNomeLike(String nome);

    List<Cliente> findByNomeOrIdOrderById(String nome, Integer Id);

    Cliente findOneById(Integer Id);

    boolean existsByNome(String nome);
}
