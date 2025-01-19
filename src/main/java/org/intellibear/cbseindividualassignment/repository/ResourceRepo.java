package org.intellibear.cbseindividualassignment.repository;

import java.util.List;

import org.intellibear.cbseindividualassignment.model.entity.Resource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResourceRepo extends JpaRepository<Resource, Long> {
    List<Resource> findByCondition(String condition);

    List<Resource> findByTitleContainingIgnoreCase(String title);

    List<Resource> findByConditionIgnoreCase(String condition);
}
