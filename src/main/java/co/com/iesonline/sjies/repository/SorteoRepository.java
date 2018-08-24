package co.com.iesonline.sjies.repository;

import co.com.iesonline.sjies.domain.Sorteo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Sorteo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SorteoRepository extends JpaRepository<Sorteo, Long> {

}
