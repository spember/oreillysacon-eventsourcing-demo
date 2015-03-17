package pember.demo.escommerce.config

import com.thirdchannel.eventsource.AggregateService
import com.thirdchannel.eventsource.EventService
import com.thirdchannel.eventsource.EventSourceService
import groovy.transform.CompileStatic
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * @author Steve Pember
 */
@CompileStatic
@Configuration
class EventSourceConfiguration {

    @Autowired
    private AggregateService baseAggregateService

    @Autowired
    private EventService baseEventService

    @Bean
    EventSourceService eventSourceService() {
        new EventSourceService(aggregateService: baseAggregateService, eventService: baseEventService)
    }
}
