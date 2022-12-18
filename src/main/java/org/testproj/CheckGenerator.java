package org.testproj;

import org.testproj.Dao.Dao;
import org.testproj.Dao.ProductDao;
import org.testproj.Entities.DiscountCard;
import org.testproj.Entities.Product;
import org.testproj.Exceptions.DiscountCardAlreadyPresentedException;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

// TODO: change all injections to Autowired annotations
// TODO: maybe add some 'broker' between dao and checkGenerator
public class CheckGenerator {
    private StringBuilder checkText;
    private Map<Integer, Integer> productsInfo = new HashMap<>(); // product id - quantity
    private Integer discountCardId;
    private Dao<Product> productDao;

    public CheckGenerator() {
    }

    public CheckGenerator(ProductDao productDao) {
        this.productDao = productDao;
    }

    public void setDiscountCardId(Integer discountCardId)
            throws DiscountCardAlreadyPresentedException {
        if (this.discountCardId == null) {
            this.discountCardId = discountCardId;
        } else {
            throw new DiscountCardAlreadyPresentedException(discountCardId.toString());
        }
    }

    public void setProductDao(Dao<Product> productDao) {
        this.productDao = productDao;
    }

    public void addProduct(int productId, int quantity) {
        productsInfo.put(productId, quantity);
    }

    // TODO: implement method
    public String generateCheck() {
        return "";
    }

    // TODO: decide : keep or not
    public String getCheck() {
        return checkText.toString();
    }
}
