package br.com.katho.vendas;

import br.com.katho.vendas.domain.entity.Cliente;
import br.com.katho.vendas.domain.entity.Pedido;
import br.com.katho.vendas.domain.repository.Clientes;
import br.com.katho.vendas.domain.repository.Pedidos;
import ch.qos.logback.core.net.SyslogOutputStream;
import ch.qos.logback.core.net.server.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@SpringBootApplication
public class VendasApplication {

	@Bean
	public CommandLineRunner init(
			@Autowired Clientes clientes,
			@Autowired Pedidos pedidos
	) {

		return args -> {

			System.out.println("Salvando clientes.");
			System.out.println("");
			Cliente fulano = new Cliente("Fulano");
			clientes.save(fulano);

			Pedido p = new Pedido();
			p.setCliente(fulano);
			p.setDataPedido(LocalDate.now());
			p.setTotal(BigDecimal.valueOf(100));

			pedidos.save(p);

			Cliente cliente = clientes.findClienteFetchPedidos(fulano.getId());
			System.out.println(cliente);
			System.out.println(cliente.getPedidos());

			System.out.println("Listando Pedidos de clientes do repository de pedidos.");
			System.out.println("");
			pedidos.findByCliente(fulano).forEach(System.out::println);

//			System.out.println("Salvando clientes.");
//			System.out.println("");
//			clientes.save(new Cliente("Katho"));
//			clientes.save(new Cliente("Mais 1 cliente..."));

//			List<Cliente> todosClientes = clientes.findAll();
//			todosClientes.forEach(System.out::println);
//			System.out.println("");
//
//			System.out.println("Imprimindo consulta HQL.");
//			System.out.println("");
//			String resposta = null;
//			boolean existe = clientes.existsByNome("Katho");
//			if(existe == true) resposta = "Sim";
//			else resposta = "Não";
//			System.out.println("Existe um cliente com o nome Katho? " + resposta);
//			System.out.println("");
//
//			List<Cliente> result = clientes.encontrarPorNome("Katho");
//			result.forEach(System.out::println);
//			System.out.println("");
//
//			System.out.println("Imprimindo consulta SQL NATIVE.");
//			System.out.println("");
//			List<Cliente> results = clientes.encontrarPeloNome("Mau");
//			results.forEach(System.out::println);
//			System.out.println("");


		};
	}

	public static void main(String[] args) {
		SpringApplication.run(VendasApplication.class, args);
	}

}
