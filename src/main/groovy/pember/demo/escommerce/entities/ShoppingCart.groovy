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
@DiscriminatorValue("cart")
class ShoppingCart extends BaseAggregate {

    @Transient
    Map<UUID,Integer> products = [:]

    void setProduct(Product product, int count) {
        products[product.id] = count
    }

    void removeProduct(Product product) {
        products.remove(product.id)
    }

    int count() {
        int count = 0
        products.each {UUID k, int v->
            count += v
        }
        count
    }
}
