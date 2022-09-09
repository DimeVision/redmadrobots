package com.dimevision.redmadrobots.repository;

import com.dimevision.redmadrobots.model.domain.Advertisement;
import com.dimevision.redmadrobots.model.domain.Status;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

/**
 * @author Dimevision
 * @version 0.1
 */

public interface AdvertisementRepository extends JpaRepository<Advertisement, Long> {

    @Query("""
            select a from Advertisement a where (a.status = ?1)
            and (?2 is null or a.id = ?2)
            and (?3 is null or a.createdAt >= ?3)
            and (?4 is null or a.title = ?4)
            and (?5 is null or a.contact = ?5)
            """)
    List<Advertisement> findAllActiveAdverts(Pageable pageable, Status status, Long advertisementId, LocalDate startDate, String title, String contact);

    List<Advertisement> findAll();
}
