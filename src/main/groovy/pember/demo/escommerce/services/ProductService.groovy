package pember.demo.escommerce.services

import com.thirdchannel.eventsource.AggregateService
import com.thirdchannel.eventsource.EventSourceService
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import pember.demo.escommerce.entities.Product
import pember.demo.escommerce.entities.events.product.ImageSetEvent
import pember.demo.escommerce.entities.events.product.InventoryAdjustedEvent
import pember.demo.escommerce.entities.events.product.PriceChangedEvent
import pember.demo.escommerce.entities.events.product.SkuSetEvent
import pember.demo.escommerce.repositories.AggregateRepository
import pember.demo.escommerce.repositories.ProductRepository

import javax.transaction.Transactional

/**
 * @author Steve Pember
 */
@Slf4j
@Service
class ProductService {
    @Autowired
    EventSourceService eventSourceService

    @Autowired
    ProductRepository productRepository


    Product createProduct(String name) {
        Product product = new Product(aggregateDescription: name)
        log.info("Attempted to save product ${product}")
    }


    Product get(String uuid) {
        Product product = (Product)eventSourceService.get(UUID.fromString(uuid))
        if (product) {
            eventSourceService.loadCurrentState(product)
        }

        product
    }

    List<Product> list() {
        List<Product> products = (List<Product>)productRepository.findAll()
        eventSourceService.loadCurrentState(products)
        products
    }

    int count() { productRepository.count() }

    @Transactional
    boolean save(Product product) {
        eventSourceService.save(product)
    }


    void setSku(Product product, String sku) {
        product.applyChange(new SkuSetEvent(sku: sku))

    }

    void changePrice(Product product, BigDecimal price) {
        product.applyChange(new PriceChangedEvent(price: price))

    }

    void setImageUrl(Product product, String imageUrl) {
        product.applyChange(new ImageSetEvent(imageURL: imageUrl))
    }

    void updateInventory(Product product, int count) {
        product.applyChange(new InventoryAdjustedEvent(count: count))
    }

}
