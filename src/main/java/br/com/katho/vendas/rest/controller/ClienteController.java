package br.com.katho.vendas.rest.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("api/clientes")
public class ClienteController {

    @RequestMapping(value = "/hello/{nome}", method = RequestMethod.GET)
    @ResponseBody
    public String helloCliente(@PathVariable("nome") String nomeCliente) {
        return String.format("Hello %s ", nomeCliente);
    }

    @RequestMapping(value = "/hello/{nome}",
            method = RequestMethod.POST,
            consumes = {"application/json", "application/xml"},
            produces = {"application/json", "application/xml"},
    )
    @ResponseBody
    public Cliente helloCliente(@PathVariable("nome") String nomeCliente, @RequestBody Cliente cliente) {
        return String.format("Hello %s ", nomeCliente);
    }

}
