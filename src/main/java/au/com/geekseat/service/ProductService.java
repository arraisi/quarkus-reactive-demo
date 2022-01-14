package au.com.geekseat.service;

import au.com.geekseat.helper.Decorator;
import au.com.geekseat.model.Product;
import io.quarkus.hibernate.reactive.panache.Panache;
import io.quarkus.hibernate.reactive.panache.PanacheRepository;
import io.smallrye.mutiny.Uni;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;

import static au.com.geekseat.service.BaseService.fromDecorate;
import static au.com.geekseat.service.BaseService.toDecorate;
import static javax.ws.rs.core.Response.ok;

@ApplicationScoped
public class ProductService implements PanacheRepository<Product> {
    public static final Decorator<Product> toDecorator = new Decorator<Product>() {
        public Product decorate(Product entity) {
            if (entity != null) {
                toDecorate(entity);
            }
            return entity;
        }
    };

    public static final Decorator<Product> fromDecorator = new Decorator<Product>() {
        public Product decorate(Product entity) {
            if (entity != null) {
                fromDecorate(entity);
            }
            return entity;
        }
    };

    public Uni<List<Product>> checkoutProduct() {
        return findAll().list()
                .map(products -> {
                    products.forEach(product -> {
                        if (product.getQuantity() < 1) {
                            throw new RuntimeException(product.getName() + " is out of stock");
                        }
                        product.setQuantity(product.getQuantity() - 1);
                        persist(product);
                    });
                    return products;
                });
    }

    public Uni<Response> update(Product product) {
        String query = "name = :name, mapData = :mapData, price = :price, quantity = :quantity, creator = :creator, editor = :editor, created = :created, createdBy = :createdBy, updated = :updated, updatedBy = :updatedBy " +
                "where id = :id";
        product.updatedBy();
        return Panache.withTransaction(() -> update(query, params(product)))
                .map(integer -> ok(integer).build());
    }

    private HashMap<String, Object> params(Product product) {
        Product entity = toDecorator.decorate(product);
        HashMap<String, Object> params = new HashMap<>();
        params.put("id", entity.getId());
        params.put("mapData", entity.getMapData());
        params.put("name", entity.getName());
        params.put("price", entity.getPrice());
        params.put("quantity", entity.getQuantity());
        params.put("creator", entity.getCreator());
        params.put("editor", entity.getEditor());
        params.put("created", entity.getCreated());
        params.put("createdBy", entity.getCreatedBy());
        params.put("updated", entity.getUpdated());
        params.put("updatedBy", entity.getUpdatedBy());
        return params;
    }
}
