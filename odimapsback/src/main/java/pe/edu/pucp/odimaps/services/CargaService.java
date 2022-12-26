/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.pucp.odimaps.services;

import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.pucp.odimaps.interfaces.CargaInterface;
import pe.edu.pucp.odimaps.models.CargaModel;

/**
 *
 * @author USUARIO
 */
@Service
public class CargaService {
    @Autowired
    CargaInterface cargaInterface;
    
    public ArrayList<CargaModel> obtenerCargas(){
        return (ArrayList<CargaModel>)cargaInterface.findAll();
    }
    
    public CargaModel guardarCarga(CargaModel pedido){
        return cargaInterface.save(pedido);
    }
    
    public CargaModel obtenerCargaPorId(int id){
        return cargaInterface.findById(id).get();
    }
}
