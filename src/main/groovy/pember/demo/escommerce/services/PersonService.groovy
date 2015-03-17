package pember.demo.escommerce.services

import com.thirdchannel.eventsource.EventSourceService
import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import pember.demo.escommerce.entities.Person
import pember.demo.escommerce.repositories.PersonRepository

/**
 * @author Steve Pember
 */
@Slf4j
@CompileStatic
@Service
class PersonService {

    private final PersonRepository personRepository
    private final EventSourceService eventSourceService

    @Autowired
    PersonService(PersonRepository pr, EventSourceService ess) {
        personRepository = pr
        eventSourceService = ess
    }

    Person getCurrentPerson() {
        // this naive example always pulls the same user
        Person person = personRepository.findAll()[0]
        eventSourceService.loadCurrentState(person)
        // also load the cart
        person
    }


}
