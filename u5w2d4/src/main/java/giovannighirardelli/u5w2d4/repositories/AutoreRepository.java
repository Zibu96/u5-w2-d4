package giovannighirardelli.u5w2d4.repositories;

import giovannighirardelli.u5w2d4.entities.Autore;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AutoreRepository extends JpaRepository<Autore, Integer> {

    boolean existsByNomeAndCognome(String nome, String cognome);
}
