package com.course.reliability.repository;

import com.course.reliability.model.CustomerInformation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerInformationRepository extends CrudRepository<CustomerInformation, Integer> {

    List<CustomerInformation> findAllByOrderByIdDesc();

}
