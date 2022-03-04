package br.com.katho.springbootexpert.service;


import br.com.katho.springbootexpert.model.Cliente;
import br.com.katho.springbootexpert.repository.ClientesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientesService {

    private ClientesRepository repository;

    public ClientesService( ClientesRepository repository ){
        this.repository = repository;
    }

    public void salvarCliente(Cliente cliente){
        validarCliente(cliente);
        this.repository.persistir(cliente);
    }

    public void validarCliente(Cliente cliente){
        //aplica validações
    }
}
