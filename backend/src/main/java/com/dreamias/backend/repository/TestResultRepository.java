package com.dreamias.backend.repository;

import com.dreamias.backend.entity.TestResult;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TestResultRepository extends JpaRepository<TestResult, Long> {
    List<TestResult> findByUserIdOrderByCreatedAtDesc(Long userId);
}
