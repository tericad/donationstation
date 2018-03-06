package com.tericadonnelly.donationstation.models.data;

import com.tericadonnelly.donationstation.models.Charity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface CharityDao extends CrudRepository<Charity, Integer> {

        List<Charity> findByUsername(String username);

        List<Charity>findByPhoneNumber(Long phoneNumber);

}