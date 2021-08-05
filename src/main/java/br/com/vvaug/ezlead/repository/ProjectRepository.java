package br.com.vvaug.ezlead.repository;

import br.com.vvaug.ezlead.document.Project;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends MongoRepository<Project, String> {

    List<Project> findByUserEmail(String email);
}
