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
import pt.ipleiria.careline.domain.dto.PatientDTO;
import pt.ipleiria.careline.domain.entities.users.PatientEntity;
import pt.ipleiria.careline.services.PatientService;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class PatientControllerIntegrationTests {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private PatientService patientService;

    @Autowired
    public PatientControllerIntegrationTests(MockMvc mockMvc, PatientService patientService) {
        this.mockMvc = mockMvc;
        this.patientService = patientService;
        this.objectMapper = new ObjectMapper();
    }

    @Test
    public void testThatCreatePatientSuccessfullyReturnsHttp201Created() throws Exception {
        PatientEntity testPatientA = TestDataUtil.createPatientEntityA();
        testPatientA.setId(null);
        String patientJson = objectMapper.writeValueAsString(testPatientA);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/patients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(patientJson)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        );
    }

    @Test
    public void testThatCreatePatientSuccessfullyReturnsSavedAuthor() throws Exception {
        PatientEntity testPatientA = TestDataUtil.createPatientEntityA();
        testPatientA.setId(null);
        String patientJson = objectMapper.writeValueAsString(testPatientA);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/patients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(patientJson)
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
    public void testThatListPatientsReturnsHttpStatus200() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/patients")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatListPatientsReturnsListOfAuthors() throws Exception {
        PatientEntity testPatientA = TestDataUtil.createPatientEntityA();
        patientService.save(testPatientA);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/patients")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].name").value("Ivo Bispo")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].email").value("ivo.bispo@gmail.com")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].password").value("password")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].nus").value("123456789")
        );
    }

    @Test
    public void testThatPatientReturnsHttpStatus200WhenPatientExists() throws Exception {
        PatientEntity testPatientA = TestDataUtil.createPatientEntityA();
        patientService.save(testPatientA);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/patients/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatPatientReturnsHttpStatus404WhenNoPatientExists() throws Exception {
        PatientEntity testPatientA = TestDataUtil.createPatientEntityA();
        patientService.save(testPatientA);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/patients/0")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }

    @Test
    public void testThatPatientReturnsPatientWhenPatientExists() throws Exception {
        PatientEntity testPatientA = TestDataUtil.createPatientEntityA();
        patientService.save(testPatientA);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/patients/1")
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
    public void testThatFullUpdatePatientReturnsHttpStatus404WhenNoPatientExists() throws Exception {
        PatientDTO testPatientA = TestDataUtil.createPatientDTOA();
        String patientJson = objectMapper.writeValueAsString(testPatientA);
        mockMvc.perform(
                MockMvcRequestBuilders.put("/api/patients/0")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(patientJson)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }

    @Test
    public void testThatFullUpdatePatientReturnsHttpStatus200WhenPatientExists() throws Exception {
        PatientEntity testPatientA = TestDataUtil.createPatientEntityA();
        patientService.save(testPatientA);

        PatientDTO testPatientDTOA = TestDataUtil.createPatientDTOA();
        String patientJson = objectMapper.writeValueAsString(testPatientDTOA);
        mockMvc.perform(
                MockMvcRequestBuilders.put("/api/patients/" + testPatientA.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(patientJson)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatFullUpdateUpdatesExistingPatient() throws Exception {
        PatientEntity testPatientA = TestDataUtil.createPatientEntityA();
        patientService.save(testPatientA);

        PatientEntity testPatientB = TestDataUtil.createPatientEntityB();
        testPatientB.setId(testPatientA.getId());
        String patientDtoUpdateJson = objectMapper.writeValueAsString(testPatientB);
        mockMvc.perform(
                MockMvcRequestBuilders.put("/api/patients/" + testPatientA.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(patientDtoUpdateJson)
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
    public void testThatPartialUpdatePatientReturnsHttpStatus404WhenNoPatientExists() throws Exception {
        PatientDTO testPatientA = TestDataUtil.createPatientDTOA();
        String patientJson = objectMapper.writeValueAsString(testPatientA);
        mockMvc.perform(
                MockMvcRequestBuilders.patch("/api/patients/0")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(patientJson)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }

    @Test
    public void testThatPartialUpdatePatientReturnsHttpStatus200WhenPatientExists() throws Exception {
        PatientEntity testPatientA = TestDataUtil.createPatientEntityA();
        patientService.save(testPatientA);

        PatientDTO testPatientDTOA = TestDataUtil.createPatientDTOA();
        testPatientDTOA.setName("UPDATED");
        String patientJson = objectMapper.writeValueAsString(testPatientDTOA);
        mockMvc.perform(
                MockMvcRequestBuilders.patch("/api/patients/" + testPatientA.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(patientJson)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatPartialUpdateUpdatesExistingPatientReturnUpdatedPatient() throws Exception {
        PatientEntity testPatientA = TestDataUtil.createPatientEntityA();
        patientService.save(testPatientA);

        PatientDTO testPatientDTOA = TestDataUtil.createPatientDTOA();
        testPatientDTOA.setName("UPDATED");
        String patientJson = objectMapper.writeValueAsString(testPatientDTOA);
        mockMvc.perform(
                MockMvcRequestBuilders.patch("/api/patients/" + testPatientA.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(patientJson)
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
}
