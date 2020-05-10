package com.course.reliability.repository;

import com.course.reliability.model.CustomerInformation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerInformationRepository extends CrudRepository<CustomerInformation, Integer> {
}
