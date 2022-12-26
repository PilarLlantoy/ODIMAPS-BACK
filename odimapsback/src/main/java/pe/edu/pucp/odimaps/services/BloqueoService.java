/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.pucp.odimaps.services;

import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.pucp.odimaps.interfaces.BloqueoInterface;
import pe.edu.pucp.odimaps.models.BloqueoModel;

/**
 *
 * @author USUARIO
 */
@Service
public class BloqueoService {
    @Autowired
    BloqueoInterface bloqueoInterface;
    
    public ArrayList<BloqueoModel> obtenerBloqueos(){
        ArrayList<BloqueoModel> bloqueo = new ArrayList<>();
        bloqueo = (ArrayList<BloqueoModel>)bloqueoInterface.findAll();
        return bloqueo;
    }
    
    public BloqueoModel guardarBloqueo(BloqueoModel bloqueo){
        return bloqueoInterface.save(bloqueo);
    }
}
