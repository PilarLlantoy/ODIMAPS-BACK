/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.pucp.odimaps.API;

import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pe.edu.pucp.odimaps.models.ClienteModel;
import pe.edu.pucp.odimaps.services.ClienteService;

/**
 *
 * @author USUARIO
 */
@RestController
@RequestMapping("/cliente")
public class ClienteAPI {
    @Autowired
    ClienteService clienteService;
    
    @GetMapping()
    public ArrayList<ClienteModel> obtenerClientees(){
        return clienteService.obtenerClientes();
    }
    
    @PostMapping()
    public ClienteModel guardarCliente(@RequestBody ClienteModel cliente){
        return clienteService.guardarCliente(cliente);
    }
}
