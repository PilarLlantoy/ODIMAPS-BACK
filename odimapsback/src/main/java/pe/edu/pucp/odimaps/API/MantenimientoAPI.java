/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.pucp.odimaps.API;

import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.edu.pucp.odimaps.models.MantenimientoModel;
import pe.edu.pucp.odimaps.models.VehiculoModel;
import pe.edu.pucp.odimaps.services.MantenimientoService;
import pe.edu.pucp.odimaps.services.VehiculoService;

/**
 *
 * @author USUARIO
 */
@RestController
@RequestMapping("/mantenimiento")
public class MantenimientoAPI {
    @Autowired
    MantenimientoService mantenimientoService;
    @Autowired
    VehiculoService vehiculoService;

    @GetMapping()
    public ArrayList<MantenimientoModel> obtenerBloqueos(){
        return mantenimientoService.obtenerMantenimiento();
    }
    
    @PostMapping("/{vehiculo}")
    public MantenimientoModel guardarBloqueo(@RequestBody MantenimientoModel mantenimiento,@PathVariable int vehiculo){
        VehiculoModel ve = vehiculoService.obtenerVehiculoPorId(vehiculo);
        mantenimiento.setVehiculo(ve);
        return mantenimientoService.guardarMantenimiento(mantenimiento);
    }
}
