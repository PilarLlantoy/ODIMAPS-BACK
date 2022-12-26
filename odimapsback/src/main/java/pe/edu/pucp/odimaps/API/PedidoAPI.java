/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.pucp.odimaps.API;

import java.util.ArrayList;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.edu.pucp.odimaps.models.CiudadModel;
import pe.edu.pucp.odimaps.models.ClienteModel;
import pe.edu.pucp.odimaps.models.PedidoModel;
import pe.edu.pucp.odimaps.services.CiudadService;
import pe.edu.pucp.odimaps.services.ClienteService;
import pe.edu.pucp.odimaps.services.PedidoService;

/**
 *
 * @author USUARIO
 */
@RestController
@RequestMapping("/pedido")
public class PedidoAPI {
    @Autowired
    PedidoService pedidoService;
    @Autowired
    CiudadService ciudadService;
    @Autowired
    ClienteService clienteService;
    
    @GetMapping()
    public ArrayList<PedidoModel> obtenerCiudades(){
        return pedidoService.obtenerPedidos();
    }
    
    @PostMapping("/{almacen}/{destino}/{cliente}")
    public PedidoModel guardarCiudad(@RequestBody PedidoModel pedido,@PathVariable int almacen,@PathVariable int destino,@PathVariable int cliente){
        pedido.setFechaRegistro(new Date(System.currentTimeMillis()));
        
        ClienteModel cli = clienteService.obtenerClientePorId(cliente);
        pedido.setCliente(cli);
        CiudadModel ciu1 = ciudadService.obtenerCiudadPorId(almacen);
        pedido.setAlmacen(ciu1);
        CiudadModel ciu2 = ciudadService.obtenerCiudadPorId(destino);
        pedido.setDestino(ciu2);
        
        if(ciu2.getRegion().contains("COSTA"))pedido.setFechaEntregaMax(new Date(System.currentTimeMillis()+1*24*60*60*1000));
        if(ciu2.getRegion().contains("SIERRA"))pedido.setFechaEntregaMax(new Date(System.currentTimeMillis()+2*24*60*60*1000));
        if(ciu2.getRegion().contains("SELVA"))pedido.setFechaEntregaMax(new Date(System.currentTimeMillis()+3*24*60*60*1000));
        return pedidoService.guardarPedido(pedido);
    }
}
