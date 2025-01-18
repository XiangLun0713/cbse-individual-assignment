package org.intellibear.cbseindividualassignment.repository;

import org.intellibear.cbseindividualassignment.model.entity.Clinician;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ClinicianRepo extends JpaRepository<Clinician, Long>, JpaSpecificationExecutor<Clinician> {
}
