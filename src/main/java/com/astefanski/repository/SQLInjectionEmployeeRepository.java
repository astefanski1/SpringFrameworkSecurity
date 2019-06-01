package com.astefanski.repository;

import com.astefanski.dto.CustomerDTO;
import com.astefanski.mapper.CustomerMapper;
import com.astefanski.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class SQLInjectionEmployeeRepository {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private CustomerMapper customerMapper;

    public List<CustomerDTO> unsafeJpaFindCustomersByCustomerId(String customerId) {
        String jql = "from Customer where id = " + customerId;
        TypedQuery<User> q = em.createQuery(jql, User.class);

        List<User> users = new ArrayList<>(q.getResultList());
        return customerMapper.map(users);
    }

    public List<CustomerDTO> safeFindAccountsByCustomerId(String customerId) {
        String jql = "from Customer where id = :customerId";
        TypedQuery<User> q = em.createQuery(jql, User.class)
                .setParameter("customerId", Long.valueOf(customerId));

        List<User> users = new ArrayList<>(q.getResultList());
        return customerMapper.map(users);
    }

    public List<CustomerDTO> safeFindAccountsByCustomerIdUsingJpaCriteria(String customerId) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<User> cq = cb.createQuery(User.class);
        Root<User> root = cq.from(User.class);
        cq.select(root).where(cb.equal(root.get("id"), customerId));

        // Execute query and return mapped results omitted
        TypedQuery<User> q = em.createQuery(cq);
        List<User> users = new ArrayList<>(q.getResultList());
        return customerMapper.map(users);
    }

}