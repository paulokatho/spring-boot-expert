package br.com.katho.vendas.domain.repository;

import br.com.katho.vendas.domain.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Clientes extends JpaRepository<Cliente, Integer> {

    //CONSULTA SQL NATIVE
    @Query(value = " Select * from cliente c where c.nome like '%:nome%' ", nativeQuery = true)
    List<Cliente> encontrarPeloNome(@Param("nome") String nome);

    //CONSULTA HQL
    @Query(value = " Select c from Cliente c where c.nome like :nome")
    List<Cliente> encontrarPorNome(@Param("nome") String nome);

    List<Cliente> findByNomeLike(String nome);

    List<Cliente> findByNomeOrIdOrderById(String nome, Integer Id);

    Cliente findOneById(Integer Id);

    boolean existsByNome(String nome);

    @Query(" delete from Cliente c where c.nome =:nome") //esse não é necessário implementar, pois o query methods já faz a query por baixo dos panos.
    @Modifying //NECESSÁRIO PARA TRANSACIONAR NA BASE DE DADOS (UPDATE, DELETE)
    void deleteByNome(String nome);

    @Query(" select c from Cliente c left join fetch c.pedidos p on p.cd where c.id =:id  ")
    Cliente findClientesFetchPedidos(Integer id);
}
