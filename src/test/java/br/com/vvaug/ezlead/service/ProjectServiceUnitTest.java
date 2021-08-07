package br.com.vvaug.ezlead.service;

import br.com.vvaug.ezlead.document.Project;
import br.com.vvaug.ezlead.document.Role;
import br.com.vvaug.ezlead.document.User;
import br.com.vvaug.ezlead.dto.ProjectRequest;
import br.com.vvaug.ezlead.dto.ProjectResponse;
import br.com.vvaug.ezlead.repository.LeadRepository;
import br.com.vvaug.ezlead.repository.ProjectRepository;
import br.com.vvaug.ezlead.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@ActiveProfiles("dev")
public class ProjectServiceUnitTest {

    @InjectMocks
    private ProjectService projectService;

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private LeadRepository leadRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private SecurityService securityService;

    private static final String PROJECT_ID = UUID.randomUUID().toString();

    private final ProjectRequest request =
            ProjectRequest.builder()
                    .name("Project 1")
                    .city("Sao Paulo")
                    .keyword("xpto")
                    .state("SP")
                    .neighbourhood("Bela Vista")
                    .place("Qualquer")
                    .build();

    private final Project projectDocument = request.convert();

    private final List<Project> projectsMock = new ArrayList<>();

    private final User userMock =
            User.builder()
                    .name("Admin")
                    .phone("1199999999")
                    .projects(projectsMock)
                    .email("admin@admin.com")
                    .password("123")
                    .roles(List.of(Role.builder()
                            .name("ADMIN_ROLE")
                            .build()))
                    .build();


    @Before
    public void setup(){
        MockitoAnnotations.openMocks(this);

        projectsMock.add(projectDocument);

        when(securityService.getAuthenticatedUser()).thenReturn(userMock);
        when(projectRepository.findAll()).thenReturn(projectsMock);

        projectDocument.setId(PROJECT_ID);

        when(projectRepository.save(request.convert())).thenReturn(projectDocument);
    }

    @Test
    public void find_all(){
        var object = projectService.findProjects();
        var expected = projectsMock.stream().map(ProjectResponse::new).collect(Collectors.toList());
        assertEquals(object, expected);
    }

    @Test
    public void create_project(){
        var object = projectService.create(request);
        var expected = new ProjectResponse(projectDocument);
        assertEquals(object, expected);
    }

        /*
        TODO
        missing methods.
     */
}
