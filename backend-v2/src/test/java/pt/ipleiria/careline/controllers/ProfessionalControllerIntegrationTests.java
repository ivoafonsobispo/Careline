package pt.ipleiria.careline.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import pt.ipleiria.careline.TestDataUtil;
import pt.ipleiria.careline.domain.dto.ProfessionalDTO;
import pt.ipleiria.careline.domain.entities.users.ProfessionalEntity;
import pt.ipleiria.careline.services.ProfessionalService;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class ProfessionalControllerIntegrationTests {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private ProfessionalService professionalService;

    @Autowired
    public ProfessionalControllerIntegrationTests(MockMvc mockMvc, ProfessionalService professionalService) {
        this.mockMvc = mockMvc;
        this.professionalService = professionalService;
        this.objectMapper = new ObjectMapper();
    }

    @Test
    public void testThatCreateProfessionalSuccessfullyReturnsHttp201Created() throws Exception {
        ProfessionalEntity testProfessionalA = TestDataUtil.createProfessionalEntityA();
        testProfessionalA.setId(null);
        String professionalJson = objectMapper.writeValueAsString(testProfessionalA);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/professionals")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(professionalJson)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        );
    }

    @Test
    public void testThatCreateProfessionalSuccessfullyReturnsSavedAuthor() throws Exception {
        ProfessionalEntity testProfessionalA = TestDataUtil.createProfessionalEntityA();
        testProfessionalA.setId(null);
        String professionalJson = objectMapper.writeValueAsString(testProfessionalA);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/professionals")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(professionalJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value("Ivo Bispo")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.email").value("ivo.bispo@gmail.com")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.password").value("password")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.nus").value("123456789")
        );
    }

    @Test
    public void testThatListProfessionalsReturnsHttpStatus200() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/professionals")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatProfessionalsReturnsHttpStatus200WhenProfessionalExists() throws Exception {
        ProfessionalEntity testProfessionalA = TestDataUtil.createProfessionalEntityA();
        professionalService.save(testProfessionalA);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/professionals/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatProfessionalReturnsHttpStatus404WhenNoProfessionalExists() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/professionals/0")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }

    @Test
    public void testThatProfessionalReturnsProfessionalWhenProfessionalExists() throws Exception {
        ProfessionalEntity testProfessionalA = TestDataUtil.createProfessionalEntityA();
        professionalService.save(testProfessionalA);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/professionals/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").value(1)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value("Ivo Bispo")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.email").value("ivo.bispo@gmail.com")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.password").value("password")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.nus").value("123456789")
        );
    }

    @Test
    public void testThatFullUpdateProfessionalReturnsHttpStatus404WhenNoProfessionalExists() throws Exception {
        ProfessionalDTO testProfessionalA = TestDataUtil.createProfessionalDTOA();
        String professionalJson = objectMapper.writeValueAsString(testProfessionalA);
        mockMvc.perform(
                MockMvcRequestBuilders.put("/api/professionals/0")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(professionalJson)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }

    @Test
    public void testThatFullUpdateProfessionalReturnsHttpStatus200WhenProfessionalExists() throws Exception {
        ProfessionalEntity testProfessionalA = TestDataUtil.createProfessionalEntityA();
        professionalService.save(testProfessionalA);

        ProfessionalDTO testProfessionalDTOA = TestDataUtil.createProfessionalDTOA();
        String professionalJson = objectMapper.writeValueAsString(testProfessionalDTOA);
        mockMvc.perform(
                MockMvcRequestBuilders.put("/api/professionals/" + testProfessionalA.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(professionalJson)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatFullUpdateUpdatesExistingProfessional() throws Exception {
        ProfessionalEntity testProfessionalA = TestDataUtil.createProfessionalEntityA();
        professionalService.save(testProfessionalA);

        ProfessionalEntity testProfessionalB = TestDataUtil.createProfessionalEntityB();
        testProfessionalB.setId(testProfessionalA.getId());
        String professionalDtoUpdateJson = objectMapper.writeValueAsString(testProfessionalB);
        mockMvc.perform(
                MockMvcRequestBuilders.put("/api/professionals/" + testProfessionalA.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(professionalDtoUpdateJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").value(1)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value("Ana Martins")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.email").value("ana.martins@gmail.com")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.password").value("password")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.nus").value("987654321")
        );
    }

    @Test
    public void testThatPartialUpdateProfessionalReturnsHttpStatus404WhenNoProfessionalExists() throws Exception {
        ProfessionalDTO testProfessionalA = TestDataUtil.createProfessionalDTOA();
        String professionalJson = objectMapper.writeValueAsString(testProfessionalA);
        mockMvc.perform(
                MockMvcRequestBuilders.patch("/api/professionals/0")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(professionalJson)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }

    @Test
    public void testThatPartialUpdateProfessionalReturnsHttpStatus200WhenProfessionalExists() throws Exception {
        ProfessionalEntity testProfessionalA = TestDataUtil.createProfessionalEntityA();
        professionalService.save(testProfessionalA);

        ProfessionalDTO professionalDTO = TestDataUtil.createProfessionalDTOA();
        professionalDTO.setName("UPDATED");
        String professionalJson = objectMapper.writeValueAsString(professionalDTO);
        mockMvc.perform(
                MockMvcRequestBuilders.patch("/api/professionals/" + testProfessionalA.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(professionalJson)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatPartialUpdateUpdatesExistingProfessionalReturnUpdatedProfessional() throws Exception {
        ProfessionalEntity testProfessionalA = TestDataUtil.createProfessionalEntityA();
        professionalService.save(testProfessionalA);

        ProfessionalDTO testProfessionalDTOA = TestDataUtil.createProfessionalDTOA();
        testProfessionalDTOA.setName("UPDATED");
        String professionalJson = objectMapper.writeValueAsString(testProfessionalDTOA);
        mockMvc.perform(
                MockMvcRequestBuilders.patch("/api/professionals/" + testProfessionalA.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(professionalJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").value(1)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value("UPDATED")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.email").value("ivo.bispo@gmail.com")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.password").value("password")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.nus").value("123456789")
        );
    }

    @Test
    public void testThatDeleteProfessionalReturnsHttpStatus204WhenProfessionalExists() throws Exception {
        ProfessionalEntity testProfessionalA = TestDataUtil.createProfessionalEntityA();
        professionalService.save(testProfessionalA);

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/api/professionals/" + testProfessionalA.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNoContent()
        );
    }

    @Test
    public void testThatDeleteProfessionalReturnsHttpStatus404WhenProfessionalNotExists() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/api/professionals/0")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }

    @Test
    public void testThatProfessionalReturnsHttpStatus200WhenProfessionalNusExists() throws Exception {
        ProfessionalEntity testProfessionalA = TestDataUtil.createProfessionalEntityA();
        professionalService.save(testProfessionalA);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/professionals/nus/" + testProfessionalA.getNus())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatProfessionalReturnsHttpStatus404WhenNoProfessionalNusExists() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/professionals/nus/0")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }

    @Test
    public void testThatProfessionalReturnsProfessionalWhenProfessionalNusExists() throws Exception {
        ProfessionalEntity testProfessionalA = TestDataUtil.createProfessionalEntityA();
        professionalService.save(testProfessionalA);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/professionals/nus/" + testProfessionalA.getNus())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").value(1)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value("Ivo Bispo")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.email").value("ivo.bispo@gmail.com")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.password").value("password")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.nus").value("123456789")
        );
    }
}
