package com.kernellabs.kernellabs.infrastructure;

import com.kernellabs.kernellabs.domain.Work;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkRepository extends JpaRepository<Work, Long> {

}
