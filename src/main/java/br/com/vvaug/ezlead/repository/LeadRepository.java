package br.com.vvaug.ezlead.repository;

import br.com.vvaug.ezlead.document.Lead;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LeadRepository extends MongoRepository<Lead, String> {
}
