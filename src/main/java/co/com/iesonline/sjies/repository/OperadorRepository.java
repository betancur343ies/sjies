package co.com.iesonline.sjies.repository;

import co.com.iesonline.sjies.domain.Operador;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Operador entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OperadorRepository extends JpaRepository<Operador, Long> {

}
