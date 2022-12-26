/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.pucp.odimaps.services;

import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.pucp.odimaps.interfaces.PedidoInterface;
import pe.edu.pucp.odimaps.models.PedidoModel;

/**
 *
 * @author USUARIO
 */
@Service
public class PedidoService {
    @Autowired
    PedidoInterface pedidoInterface;
    
    public ArrayList<PedidoModel> obtenerPedidos(){
        return (ArrayList<PedidoModel>)pedidoInterface.findAll();
    }
    
    public PedidoModel guardarPedido(PedidoModel pedido){
        return pedidoInterface.save(pedido);
    }
    
    public PedidoModel obtenerPedidoPorId(int id){
        return pedidoInterface.findById(id).get();
    }
}
