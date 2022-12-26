/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.pucp.odimaps.API;

import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import pe.edu.pucp.odimaps.models.CiudadModel;
import pe.edu.pucp.odimaps.models.TramoModel;
import pe.edu.pucp.odimaps.services.CiudadService;
import pe.edu.pucp.odimaps.services.TramoService;

/**
 *
 * @author USUARIO
 */
@RestController
@RequestMapping("/tramo")
public class TramoAPI {
    @Autowired
    TramoService tramoService;
    @Autowired
    CiudadService ciudadService;

    @GetMapping()
    public ArrayList<TramoModel> obtenerCiudades(){
        return tramoService.obtenerTramos();
    }
    
    @PostMapping("/{ciudad1}/{ciudad2}")
    public TramoModel guardarCiudad(@RequestBody TramoModel tramo,@PathVariable int ciudad1,@PathVariable int ciudad2){
        CiudadModel ciu1 = ciudadService.obtenerCiudadPorId(ciudad1);
        CiudadModel ciu2 = ciudadService.obtenerCiudadPorId(ciudad2);
        tramo.setOrigen(ciu1);
        tramo.setDestino(ciu2);
        return tramoService.guardarTramo(tramo);
    }


}
