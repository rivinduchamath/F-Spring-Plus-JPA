package lk.ijse.dep.pos.dao.custom.impl;

import lk.ijse.dep.pos.dao.custom.QueryDAO;
import lk.ijse.dep.pos.entity.CustomEntity;
import org.hibernate.Session;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class QueryDAOImpl implements QueryDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public CustomEntity getOrderInfo(int orderId) throws Exception {
        return null;

    }

    @Override
    public CustomEntity getOrderInfo2(int orderId) throws Exception {
        return null;
    }

    @Override
    public List<CustomEntity> getOrdersInfo(String query) throws Exception {
        List<Object[]> resultList = entityManager.createNativeQuery("SELECT O.id, C.customerId, C.name, O.date, SUM(OD.qty * OD.unit_price) AS Total  FROM Customer C INNER JOIN `Order` O ON C.customerId=O.customer_id " +
                "INNER JOIN OrderDetail OD on O.id = OD.order_id WHERE O.id LIKE ?1 OR C.customerId LIKE ?1 OR C.name LIKE ?1 OR O.date LIKE ?1 GROUP BY O.id")
                .setParameter(1, query)
                .getResultList();
        List<CustomEntity> al = new ArrayList<>();
        for (Object[] cols : resultList) {
            al.add(new CustomEntity((int) cols[0], (String) cols[1], (String) cols[2],
                    (Date) cols[3], (Double) cols[4]));
        }
        return al;
    }
}
