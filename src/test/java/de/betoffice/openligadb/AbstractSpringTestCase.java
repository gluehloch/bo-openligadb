package de.betoffice.openligadb;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import de.betoffice.database.config.TestPropertiesConfiguration;
import de.winkler.betoffice.conf.PersistenceJPAConfiguration;

@ActiveProfiles(profiles = "test")
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { PersistenceJPAConfiguration.class, TestPropertiesConfiguration.class })
@ComponentScan({"de.winkler.betoffice", "de.betoffice"})
public abstract class AbstractSpringTestCase {

}
