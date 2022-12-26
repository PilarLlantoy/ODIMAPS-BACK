/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.pucp.odimaps.services;

import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.pucp.odimaps.interfaces.CiudadInterface;
import pe.edu.pucp.odimaps.models.CiudadModel;

/**
 *
 * @author USUARIO
 */
@Service
public class CiudadService {
    @Autowired
    CiudadInterface ciudadInterface;
    
    public ArrayList<CiudadModel> obtenerCiudades(){
        return (ArrayList<CiudadModel>)ciudadInterface.findAll();
    }
    
    public CiudadModel guardarCiudad(CiudadModel ciudad){
        return ciudadInterface.save(ciudad);
    }

    public CiudadModel obtenerCiudadPorId(int id)
    {
        CiudadModel ciudad = ciudadInterface.findById(id).get();
        return ciudad;
    }
}
