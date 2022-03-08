package br.com.katho.vendas.domain.repositorio;

import br.com.katho.vendas.domain.entity.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class Clientes {

        //IMPLEMENTAÇÃO DE SQL NATIVO
    //private static String INSERT = "insert into cliente (nome) values (?) ";
    //private static String SELECT_ALL = "SELECT * FROM CLIENTE ";
    //private static String UPDATE = "update cliente set nome = ? where id = ? ";
    //private static String DELETE = "delete from cliente where id = ? ";

//    @Autowired
//    private JdbcTemplate jdbcTemplate;

        //IMPELEMTAÇÃO COM JPA
    @Autowired
    private EntityManager entityManager;

    @Transactional
    public Cliente salvar(Cliente cliente) {
        //jdbcTemplate.update(INSERT, new Object[] {cliente.getNome()});
        entityManager.persist(cliente);
        return cliente;
    }

    @Transactional
    public Cliente atualizar(Cliente cliente) {
//        jdbcTemplate.update(UPDATE, new Object[] {
//                cliente.getNome(), cliente.getId()
//        });
        entityManager.merge(cliente);
        return cliente;
    }

    @Transactional
    public void deletar(Cliente cliente) {
        //deletar(cliente.getId());
        if(!entityManager.contains(cliente)) {
            cliente = entityManager.merge(cliente);
        }
        entityManager.remove(cliente);
    }

    @Transactional
    public void deletar(Integer id) {
        //jdbcTemplate.update(DELETE, new Object[] {id});
        Cliente cliente = entityManager.find(Cliente.class, id);
        deletar(cliente);
    }

    @Transactional(readOnly = true)
    public List<Cliente> buscarPorNome(String nome) {
        //BUSCA SQL NATIVO
//        return jdbcTemplate.query(
//                SELECT_ALL.concat(" where nome like ? "),
//                new Object[] {"%" + nome + "%"},
//                obterClienteMapper());
        //BUSCA HQL
        String jpql = " select c from Cliente c where c.nome like :nome ";
        TypedQuery<Cliente> query = entityManager.createQuery(jpql, Cliente.class);
        query.setParameter("nome", "%" + nome + "%");
        return query.getResultList();
    }

    @Transactional(readOnly = true)
    public List<Cliente> obterTodos() {
        //return  jdbcTemplate.query(SELECT_ALL, obterClienteMapper());

        //BUSCA HQL
        return entityManager
                .createQuery("from Cliente", Cliente.class)
                .getResultList();
    }

    //COM A CONSULTA PELO ENTITYMANAGER NÃO PRECISA MAIS DO -> RowMapper
//    private RowMapper<Cliente> obterClienteMapper() {
//        return new RowMapper<Cliente>() {
//            @Override
//            public Cliente mapRow(ResultSet resultSet, int i) throws SQLException {
//                Integer id = resultSet.getInt("id");
//                String nome = resultSet.getString("nome");
//                return new Cliente(id, nome);
//            }
//        };
//    }

}
