package com.tericadonnelly.donationstation.models.data;

import com.tericadonnelly.donationstation.models.Donor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface DonorDao extends CrudRepository<Donor, Integer> {

}