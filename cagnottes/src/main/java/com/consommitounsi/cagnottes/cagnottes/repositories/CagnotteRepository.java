package com.consommitounsi.cagnottes.cagnottes.repositories;

import com.consommitounsi.cagnottes.cagnottes.entities.Cagnotte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CagnotteRepository extends JpaRepository<Cagnotte, String> {
}
