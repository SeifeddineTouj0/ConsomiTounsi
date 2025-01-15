package com.jellali.forum.Repository;

import com.jellali.forum.Entities.Commentaire;
import com.jellali.forum.Entities.Sujet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentaireRepository extends JpaRepository<Commentaire, Long>  {


    Commentaire save(Commentaire commentaire);

    @Query("SELECT c FROM Commentaire c JOIN Evaluation e ON c.id = e.cibleId GROUP BY c.id ORDER BY SUM(e.rating) DESC")
    List<Commentaire> findMostRelevantComments();

    List<Commentaire> findTop10ByOrderByDateCreationDesc();
}
