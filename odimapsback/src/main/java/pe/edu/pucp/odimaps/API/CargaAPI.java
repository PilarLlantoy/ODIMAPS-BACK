/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.pucp.odimaps.API;

import java.util.ArrayList;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.edu.pucp.odimaps.models.CargaModel;
import pe.edu.pucp.odimaps.models.CiudadModel;
import pe.edu.pucp.odimaps.models.PedidoModel;
import pe.edu.pucp.odimaps.services.CargaService;
import pe.edu.pucp.odimaps.services.PedidoService;

/**
 *
 * @author USUARIO
 */
@RestController
@RequestMapping("/carga")
public class CargaAPI {
    @Autowired
    CargaService cargaService;
    @Autowired
    PedidoService pedidoService;
    
    @GetMapping()
    public ArrayList<CargaModel> obtenerCiudades(){
        return cargaService.obtenerCargas();
    }
    
    @PostMapping("/{pedido}/{plan}")
    public CargaModel guardarCiudad(@RequestBody CargaModel carga,@PathVariable int pedido, @PathVariable int plan){
        
        PedidoModel pedi = pedidoService.obtenerPedidoPorId(pedido);
        
        carga.setPedido(pedi);
        return cargaService.guardarCarga(carga);
    }
}
