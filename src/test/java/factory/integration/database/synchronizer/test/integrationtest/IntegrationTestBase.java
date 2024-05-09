package factory.integration.database.synchronizer.test.integrationtest;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import factory.integration.database.synchronizer.SynchronizerApplication;

@ActiveProfiles("test")
@SpringBootTest(classes = {SynchronizerApplication.class})
public abstract class IntegrationTestBase {
}
