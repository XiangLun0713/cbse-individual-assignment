package org.intellibear.cbseindividualassignment.repository;

import org.intellibear.cbseindividualassignment.model.entity.MedicalRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicalRecordRepo extends JpaRepository<MedicalRecord, Long>, JpaSpecificationExecutor<MedicalRecord> {
}
