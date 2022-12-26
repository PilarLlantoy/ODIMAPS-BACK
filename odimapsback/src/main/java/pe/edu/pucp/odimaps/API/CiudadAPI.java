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
@RequestMapping("/ciudad")
public class CiudadAPI {
    @Autowired
    CiudadService ciudadService;
    @Autowired
    TramoService tramoService;
    
    @GetMapping()
    public ArrayList<CiudadModel> obtenerCiudades(){
        return ciudadService.obtenerCiudades();
    }
    
    @PostMapping()
    public CiudadModel guardarCiudad(@RequestBody CiudadModel ciudad){
        return ciudadService.guardarCiudad(ciudad);
    }

    @GetMapping("/{ciudad1}")
    public ArrayList<CiudadModel> obtenerCiudades2(@PathVariable long ciudad1){
        ArrayList<TramoModel> tramosElegidos = tramoService.obtenerTramos();
        ArrayList<Integer> ciudades = new ArrayList<Integer>();
        ArrayList<CiudadModel> ciudades2 = new ArrayList<CiudadModel>();
        for (int x=0;x<tramosElegidos.size();x++) {
            int origen = tramosElegidos.get(x).getOrigen().getId();
            int destino = tramosElegidos.get(x).getDestino().getId();
            if (origen == ciudad1 || destino == ciudad1){
                if((!ciudades.contains(origen)) && destino == ciudad1){
                    ciudades.add(origen);
                    System.out.println("origen " + origen + " agregado");
                    
                }else if((!ciudades.contains(destino)) && origen == ciudad1){
                    ciudades.add(destino);
                    System.out.println("destino " + destino + " agregado");
                }
            }
        }
        for (Integer ciudadUnica : ciudades) {
            CiudadModel ciudadCercana = ciudadService.obtenerCiudadPorId(ciudadUnica);
            ciudades2.add(ciudadCercana);
        }
        return ciudades2;
    }
}
