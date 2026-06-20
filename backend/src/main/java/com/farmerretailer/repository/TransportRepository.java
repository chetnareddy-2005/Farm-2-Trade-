package com.farmerretailer.repository;

import com.farmerretailer.entity.Transport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransportRepository extends JpaRepository<Transport, Long> {
    @org.springframework.data.jpa.repository.Query("SELECT t FROM Transport t LEFT JOIN FETCH t.order o LEFT JOIN FETCH t.driver d WHERE t.order.id = :orderId")
    Optional<Transport> findByOrderId(@org.springframework.data.repository.query.Param("orderId") Long orderId);

    @org.springframework.data.jpa.repository.Query("SELECT t FROM Transport t JOIN FETCH t.order o JOIN FETCH o.retailer r JOIN FETCH o.product p LEFT JOIN FETCH t.driver d WHERE t.status = :status")
    List<Transport> findByStatusWithDetails(@org.springframework.data.repository.query.Param("status") String status);

    List<Transport> findByStatus(String status);
    @org.springframework.data.jpa.repository.Query("SELECT t FROM Transport t LEFT JOIN FETCH t.order o LEFT JOIN FETCH o.retailer r LEFT JOIN FETCH t.driver d WHERE t.driver.id = :driverId")
    List<Transport> findByDriverId(@org.springframework.data.repository.query.Param("driverId") Long driverId);
    @org.springframework.data.jpa.repository.Modifying
    @org.springframework.transaction.annotation.Transactional
    void deleteByDriverId(Long driverId);
}
