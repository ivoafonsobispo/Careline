package pt.ipleiria.careline.repositories;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

//@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class HeartbeatEntityRepositoryIntegrationTests {

    private HeartbeatRepository heartbeatRepository;

    @Autowired
    public HeartbeatEntityRepositoryIntegrationTests(HeartbeatRepository heartbeatRepository) {
        this.heartbeatRepository = heartbeatRepository;
    }

    /*@Test
    public void testThatHeartbeatCanBeCreatedAndRecalled() {
        HeartbeatEntity heartbeatEntity = TestDataUtil.createTestHeartbeatA(new PatientEntity("Ivo Bispo", "ivo.bispo@mail.com", "password", "123456789"));
        heartbeatRepository.save(heartbeatEntity);
        Optional<HeartbeatEntity> result = heartbeatRepository.findById(heartbeatEntity.getId());
        assert (result.isPresent());
        assert (result.get().getHeartbeat().equals(80));
    }

     */
}
