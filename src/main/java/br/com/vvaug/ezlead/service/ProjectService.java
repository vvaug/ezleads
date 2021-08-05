package br.com.vvaug.ezlead.service;

import br.com.vvaug.ezlead.document.User;
import br.com.vvaug.ezlead.dto.LeadRequest;
import br.com.vvaug.ezlead.dto.ProjectRequest;
import br.com.vvaug.ezlead.dto.ProjectResponse;
import br.com.vvaug.ezlead.repository.LeadRepository;
import br.com.vvaug.ezlead.repository.ProjectRepository;
import br.com.vvaug.ezlead.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final LeadRepository leadRepository;
    private final UserRepository userRepository;
    private final SecurityService securityService;

    public List<ProjectResponse> findProjects() {
        var user = securityService.getAuthenticatedUser();
        log.info("Finding projects for user: {}", user);
        return  user.getProjects().stream().map(ProjectResponse::new).collect(Collectors.toList());
    }

    public ProjectResponse create(ProjectRequest projectRequest) {
        var user = securityService.getAuthenticatedUser();
        log.info("Creating project for user: {}", user);
        log.info("Payload: {}", projectRequest);
        var document = projectRequest.convert();
        projectRepository.save(document);
        user.setId(user.getId());
        user.getProjects().add(document);
        userRepository.save(user);
        return new ProjectResponse(document);
    }

    public ProjectResponse update(String id, ProjectRequest projectRequest) {
        var user = securityService.getAuthenticatedUser();
        log.info("Updating a project for user: {}", user);
        log.info("Project Id: {}", id);
        log.info("Payload: {}", projectRequest);
        //TODO create a custom exception
        var document = user.getProjects().stream()
                .filter(doc -> doc.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Project not found for this user."));
        document.update(projectRequest);
        projectRepository.save(document);
        userRepository.save(user);
        return new ProjectResponse(document);
    }

    public void remove(String id) {
        var user = securityService.getAuthenticatedUser();
        log.info("Removing a project for user: {}", user);
        log.info("Project Id: {}", id);
        //TODO create a custom exception
        var document = user.getProjects().stream()
                .filter(doc -> doc.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Project not found for this user."));
        projectRepository.delete(document);
        user.getProjects().remove(document);
        userRepository.save(user);
    }

    public ProjectResponse addLead(String id, LeadRequest leadRequest) {
        var user = securityService.getAuthenticatedUser();
        log.info("Adding a Lead to a project for user: {}", user);
        log.info("Project Id: {}", id);
        log.info("Payload: {}", leadRequest);
        var document = leadRequest.convert();
        leadRepository.save(document);
        //TODO create a custom exception
        var project = user.getProjects().stream()
                .filter(doc -> doc.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Project not found for this user."));
        project.getLeads().add(document);
        projectRepository.save(project);
        userRepository.save(user);
        return new ProjectResponse(project);
    }

    public ProjectResponse updateLead(String projectId, String leadId, LeadRequest leadRequest) {
        var user = securityService.getAuthenticatedUser();
        log.info("Updating a Lead in a project for user: {}", user);
        log.info("Project Id: {}", projectId);
        log.info("Lead Id: {}", leadId);
        log.info("Payload: {}", leadRequest);
        //TODO create a custom exception
        var project = user.getProjects().stream()
                .filter(doc -> doc.getId().equals(projectId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Project not found for this user."));

        var lead = project.getLeads().stream()
                .filter(l -> l.getId().equals(leadId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Lead not found for this Project."));

        lead.update(leadRequest);

        leadRepository.save(lead);
        projectRepository.save(project);
        userRepository.save(user);
        return new ProjectResponse(project);
    }
}
