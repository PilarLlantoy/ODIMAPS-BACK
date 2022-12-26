/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.pucp.odimaps.API;

import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pe.edu.pucp.odimaps.models.VehiculoModel;
import pe.edu.pucp.odimaps.services.VehiculoService;

/**
 *
 * @author USUARIO
 */
@RestController
@RequestMapping("/vehiculo")
public class VehiculoAPI {
    @Autowired
    VehiculoService vehiculoService;
    
    @GetMapping()
    public ArrayList<VehiculoModel> obtenerVehiculos(){
        return vehiculoService.obtenerVehiculos();
    }
    
    @PostMapping()
    public VehiculoModel guardarVehiculo(@RequestBody VehiculoModel vehiculo){
        return vehiculoService.guardarVehiculo(vehiculo);
    }
}
