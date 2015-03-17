package pember.demo.escommerce.entities

import groovy.transform.CompileStatic

import javax.persistence.DiscriminatorValue
import javax.persistence.Entity
import javax.persistence.Transient

/**
 * @author Steve Pember
 */
@CompileStatic
@Entity
@DiscriminatorValue("person")
class Person extends BaseAggregate {

    @Transient String name

    @Transient String address

    @Transient UUID shoppingCartId

    @Transient ShoppingCart shoppingCart

    @Transient int ordersPlaced = 0

    @Transient long totalSpentInCents = 0
}



