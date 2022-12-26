package pe.edu.pucp.odimaps.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.pucp.odimaps.models.ClienteModel;

/**
 *
 * @author USUARIO
 */
@Repository
public interface ClienteInterface extends JpaRepository<ClienteModel, Integer> {
    
}
