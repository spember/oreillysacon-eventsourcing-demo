package pember.demo.escommerce.entities

import groovy.transform.CompileStatic

import javax.persistence.DiscriminatorValue
import javax.persistence.Entity
import javax.persistence.Inheritance
import javax.persistence.Transient

/**
 * @author Steve Pember
 */
@Entity
@DiscriminatorValue("product")
class Product extends BaseAggregate {

    @Transient
    String sku
    @Transient
    BigDecimal price

    @Transient
    String imageURL
    @Transient
    String getImageURL() {
        "/images/${imageURL}"
    }

    @Transient
    int inventoryOnHand
}
