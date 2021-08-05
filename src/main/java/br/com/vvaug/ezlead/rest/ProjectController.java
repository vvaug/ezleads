package br.com.vvaug.ezlead.rest;

import br.com.vvaug.ezlead.dto.LeadRequest;
import br.com.vvaug.ezlead.dto.ProjectRequest;
import br.com.vvaug.ezlead.dto.ProjectResponse;
import br.com.vvaug.ezlead.service.ProjectService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RequestMapping("/projects")
@RestController
@RequiredArgsConstructor
@Slf4j
public class ProjectController {

    private final ProjectService projectService;

    @GetMapping
    public List<ProjectResponse> findProjects(){
        return projectService.findProjects();
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public ProjectResponse create(@RequestBody @Validated ProjectRequest projectRequest){
        return projectService.create(projectRequest);
    }

    @PutMapping("/{id}")
    public ProjectResponse update(@PathVariable String id, @RequestBody ProjectRequest projectRequest){
        return projectService.update(id, projectRequest);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void delete(@PathVariable String id){
         projectService.remove(id);
    }

    @PostMapping("/{id}/leads")
    @ResponseStatus(CREATED)
    public ProjectResponse addLead(@PathVariable String id, @RequestBody @Validated LeadRequest leadRequest){
        return projectService.addLead(id, leadRequest);
    }

    @PutMapping("/{id}/leads/{leadId}")
    public ProjectResponse updateLead(@PathVariable("id") String projectId, @PathVariable("leadId") String leadId, @RequestBody LeadRequest leadRequest){
        return projectService.updateLead(projectId, leadId, leadRequest);
    }
}
