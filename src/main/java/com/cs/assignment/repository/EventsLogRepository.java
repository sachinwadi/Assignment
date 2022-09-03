package com.cs.assignment.repository;

import com.cs.assignment.entity.EventsLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventsLogRepository extends JpaRepository<EventsLogEntity, Long> {
}
