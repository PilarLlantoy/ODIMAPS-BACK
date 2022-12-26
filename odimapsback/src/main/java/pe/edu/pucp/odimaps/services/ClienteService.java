package pe.edu.pucp.odimaps.services;

import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.pucp.odimaps.interfaces.ClienteInterface;
import pe.edu.pucp.odimaps.models.ClienteModel;

/**
 *
 * @author USUARIO
 */
@Service
public class ClienteService {
    @Autowired
    ClienteInterface clienteInterface;
    
    public ArrayList<ClienteModel> obtenerClientes(){
        return (ArrayList<ClienteModel>)clienteInterface.findAll();
    }
    
    public ClienteModel guardarCliente(ClienteModel cliente){
        return clienteInterface.save(cliente);
    }

    public ClienteModel obtenerClientePorId(int id)
    {
        ClienteModel cliente = clienteInterface.findById(id).get();
        return cliente;
    }
}
