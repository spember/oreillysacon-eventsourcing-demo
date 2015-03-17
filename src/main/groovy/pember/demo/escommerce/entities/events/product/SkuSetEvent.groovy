package pember.demo.escommerce.entities.events.product

import com.thirdchannel.eventsource.Aggregate
import com.thirdchannel.eventsource.annotation.EventData
import groovy.transform.CompileStatic
import groovy.transform.ToString
import pember.demo.escommerce.entities.Product
import pember.demo.escommerce.entities.events.BaseEvent

import javax.persistence.DiscriminatorValue
import javax.persistence.Entity
import javax.persistence.Transient

/**
 * @author Steve Pember
 */
@CompileStatic
@ToString
@Entity
@DiscriminatorValue("product-set-sku")
class SkuSetEvent extends BaseEvent {

    @Transient
    @EventData
    String sku

    @Override
    void process(Aggregate root) {
        ((Product)root).sku = sku
    }
}
