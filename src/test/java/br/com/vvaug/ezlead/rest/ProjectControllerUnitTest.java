package br.com.vvaug.ezlead.rest;

import br.com.vvaug.ezlead.dto.LeadRequest;
import br.com.vvaug.ezlead.dto.ProjectRequest;
import br.com.vvaug.ezlead.service.ProjectService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.UUID;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = ProjectController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
@ActiveProfiles("dev")
public class ProjectControllerUnitTest {

    @Autowired
    private MockMvc mock;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProjectService projectService;

    private static final String ENDPOINT = "/projects";

    private static final String PROJECT_ID = UUID.randomUUID().toString();

    private static final String LEAD_ID = UUID.randomUUID().toString();

    private final ProjectRequest request =
            ProjectRequest.builder()
                    .name("Project 1")
                    .city("Sao Paulo")
                    .keyword("xpto")
                    .state("SP")
                    .neighbourhood("Bela Vista")
                    .place("Qualquer")
                    .build();

    private final LeadRequest leadRequest =
            LeadRequest.builder()
                    .name("Lead 1")
                    .phone("11999999999")
                    .state("SP")
                    .build();
    @Before
    public void setup(){
    }

    @Test
    public void find_all() throws Exception {
        mock.perform(MockMvcRequestBuilders
                .get(ENDPOINT)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectToJson(request))
        ).andExpect(status().is(OK.value()));
    }

    @Test
    public void create_project() throws Exception {
        mock.perform(MockMvcRequestBuilders
                .post(ENDPOINT)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectToJson(request))
        ).andExpect(status().is(CREATED.value()));
    }

    @Test
    public void create_project_invalid_field() throws Exception {
        request.setName(null);
        mock.perform(MockMvcRequestBuilders
                .post(ENDPOINT)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectToJson(request))
        ).andExpect(status().is(BAD_REQUEST.value()));
    }

    @Test
    public void update_project() throws Exception {
        mock.perform(MockMvcRequestBuilders
                .put(ENDPOINT + "/{id}", PROJECT_ID)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectToJson(request))
                ).andExpect(status().is(OK.value()));
    }

    @Test
    public void delete_project() throws Exception {
        mock.perform(MockMvcRequestBuilders
                .delete(ENDPOINT + "/{id}", PROJECT_ID)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectToJson(request))
        ).andExpect(status().is(NO_CONTENT.value()));
    }

    @Test
    public void add_lead_to_project() throws Exception {
        mock.perform(MockMvcRequestBuilders
                .post(ENDPOINT + "/{id}/leads", PROJECT_ID)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectToJson(leadRequest))
        ).andExpect(status().is(CREATED.value()));
    }

    @Test
    public void add_lead_to_project_invalid_fields() throws Exception {
        leadRequest.setName(null);
        mock.perform(MockMvcRequestBuilders
                .post(ENDPOINT + "/{id}/leads", PROJECT_ID)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectToJson(leadRequest))
        ).andExpect(status().is(BAD_REQUEST.value()));
    }

    @Test
    public void update_project_lead() throws Exception {
        mock.perform(MockMvcRequestBuilders
                .put(ENDPOINT + "/{id}/leads/{leadId}", PROJECT_ID, LEAD_ID)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectToJson(leadRequest))
        ).andExpect(status().is(OK.value()));
    }

    protected <T> String objectToJson(T object) throws JsonProcessingException {
        return objectMapper.writeValueAsString(object);
    }
}
