package pember.demo.escommerce.services

import com.thirdchannel.eventsource.Event
import com.thirdchannel.eventsource.EventService
import com.thirdchannel.eventsource.EventSourceService
import groovy.json.JsonSlurper
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import pember.demo.escommerce.entities.events.cart.ProductAddedEvent
import pember.demo.escommerce.entities.events.cart.ProductCartEvent
import pember.demo.escommerce.entities.events.cart.ProductRemovedEvent
import pember.demo.escommerce.repositories.EventRepository

/**
 * @author Steve Pember
 */
@Slf4j
@Service
class ReportService {

    private EventSourceService eventSourceService
    private EventService eventService
    private EventRepository eventRepository

    @Autowired ReportService(EventSourceService ess, EventService es, EventRepository er) {
        eventSourceService = ess
        eventService = es
        eventRepository = er
    }

    /**
     * A simple projection to find all products that have been added then removed within 5 minutes, per user
     *
     * @return
     */
    Map findProducts() {
        JsonSlurper slurper = new JsonSlurper()
        /*
        This quick and dirty disaster of a function was thrown together the morning before the talk. I thought it would be
        in poor form to talk about how easy it is to find all products that were removed 5 minutes after being added, and
        then not show how to do it
         */

        Map report = [:]
        // normally, we'd use a date range or similar here

        rx.Observable.from(eventRepository.findAll(sortByRevisionAsc()))
        .filter({it instanceof ProductAddedEvent || it instanceof ProductRemovedEvent})
        .map({
            // this hydrate function should probably be accessible by the eventSourceService
            it.restoreData(slurper.parseText(it.data) as Map)
            it
        })
        .groupBy({Event it -> it.aggregateId})
        .flatMap({g1 ->
            // it will be an Observable of the stream broken down by user. Now need to break down by sku
            g1.groupBy({event-> event.productId})
            .flatMap({g2->
                //g2 is an Observable of the sub-stream broken down by product
                g2.reduce([:], {acc, item->
                    // track removed events against each added event
                    if (item instanceof ProductAddedEvent) {
                        acc[item.id.toString()] = [item.date]
                    } else if (item instanceof ProductRemovedEvent) {
                        checkAddedEventsForWindow(acc, item)
                    }
                    acc
                })
                .map({Map data->
                    // g2's key is the productId
                    //
                    // this could also all be mapped and filtered in stream, rather than each's and if's
                    //
                    List result = []
                    // only count date lists of more than 1
                    data.each {k, v->
                        if (v.size() > 1) {
                            result<< v
                        }
                    }
                    // return an empty map if we have no events
                    if (result.size() > 0) {
                        [(g2.key.toString()):result]
                    } else {
                        [:]
                    }
                })
            })
            // I should reduce further and actually use the person grouping. However, in this system there's only one user, so, yay.
            .reduce([:], {acc, item ->  acc += item })
        })
        .subscribe({ report = it as Map } ,{log.error("Error: ", it)}, {})
        log.debug("Report is ${report}")
        report
    }

    private Sort sortByRevisionAsc() {
        return new Sort(Sort.Direction.ASC, "revision");
    }

    private void checkAddedEventsForWindow(Map<String, List<Date>>acc, ProductCartEvent item) {
        acc.each {k,v ->
            if (item.date.getTime() - v[0].getTime() <= Math.abs(1000*60*5)) {
                acc[k] << item.date
            }
        }
    }
}
