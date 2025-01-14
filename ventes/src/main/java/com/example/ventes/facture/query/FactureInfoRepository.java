package com.example.ventes.facture.query;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.coreapi.ventes.facture.FactureInfo;
import com.example.coreapi.ventes.facture.TypeFacture;

public interface FactureInfoRepository extends JpaRepository<FactureInfo, String> {

    Iterable<FactureInfo> findByUser(String userId);

    Iterable<FactureInfo> findByTypeFacture(TypeFacture typeFacture);
}