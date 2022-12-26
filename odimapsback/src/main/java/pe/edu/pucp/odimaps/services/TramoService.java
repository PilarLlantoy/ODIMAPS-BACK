/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.pucp.odimaps.services;

import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.pucp.odimaps.interfaces.TramoInterface;
import pe.edu.pucp.odimaps.models.TramoModel;

/**
 *
 * @author USUARIO
 */
@Service
public class TramoService {
    @Autowired
    TramoInterface tramoInterface;
    
    public ArrayList<TramoModel> obtenerTramos(){
        ArrayList<TramoModel> tramos = new ArrayList<>();
        tramos = (ArrayList<TramoModel>)tramoInterface.findAll();
        return tramos;
    }
    
    public TramoModel guardarTramo(TramoModel tramo){
        return tramoInterface.save(tramo);
    }

    public TramoModel obtenerTramoPorId(int id)
    {
        TramoModel tramo = tramoInterface.findById(id).get();
        return tramo;
    }
    
}
