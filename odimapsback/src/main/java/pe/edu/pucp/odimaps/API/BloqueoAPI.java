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
import pe.edu.pucp.odimaps.models.BloqueoModel;
import pe.edu.pucp.odimaps.models.TramoModel;
import pe.edu.pucp.odimaps.services.BloqueoService;
import pe.edu.pucp.odimaps.services.TramoService;

/**
 *
 * @author USUARIO
 */
@RestController
@RequestMapping("/bloqueo")
public class BloqueoAPI {
    @Autowired
    BloqueoService bloqueoService;
    @Autowired
    TramoService tramoService;

    @GetMapping()
    public ArrayList<BloqueoModel> obtenerBloqueos(){
        return bloqueoService.obtenerBloqueos();
    }
    
    @PostMapping("/{tramo}")
    public BloqueoModel guardarBloqueo(@RequestBody BloqueoModel bloqueo,@PathVariable int tramo){
        TramoModel tra = tramoService.obtenerTramoPorId(tramo);
        bloqueo.setTramo(tra);
        return bloqueoService.guardarBloqueo(bloqueo);
    }
}
