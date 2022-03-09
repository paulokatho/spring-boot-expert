package br.com.katho.vendas;

import br.com.katho.vendas.domain.entity.Cliente;
import br.com.katho.vendas.domain.repositorio.Clientes;
import net.bytebuddy.implementation.bind.MethodDelegationBinder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class VendasApplication {

	@Bean
	public CommandLineRunner init(@Autowired Clientes clientes) {
		return args -> {

			System.out.println("Salvando clientes.");
			System.out.println("");
			clientes.save(new Cliente("Katho"));
			clientes.save(new Cliente("Mais 1 cliente..."));

			List<Cliente> todosClientes = clientes.findAll();
			todosClientes.forEach(System.out::println);
			System.out.println("");

			System.out.println("Imprimindo consulta HQL.");
			System.out.println("");
			String resposta = null;
			boolean existe = clientes.existsByNome("Katho");
			if(existe == true) resposta = "Sim";
			else resposta = "Não";
			System.out.println("Existe um cliente com o nome Katho? " + resposta);
			System.out.println("");

			List<Cliente> result = clientes.encontrarPorNome("Katho");
			result.forEach(System.out::println);
			System.out.println("");

			System.out.println("Imprimindo consulta SQL NATIVE.");
			System.out.println("");
			List<Cliente> results = clientes.encontrarPeloNome("Mau");
			results.forEach(System.out::println);
			System.out.println("");




		};
	}

	public static void main(String[] args) {
		SpringApplication.run(VendasApplication.class, args);
	}

}
