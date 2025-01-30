package com.espinosa.barbara.examen.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.espinosa.barbara.examen.model.Branch;

@Repository
public interface BranchRepository extends MongoRepository<Branch, String> {

    
}