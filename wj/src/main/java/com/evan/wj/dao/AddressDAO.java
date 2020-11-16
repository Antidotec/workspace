package com.evan.wj.dao;

import com.evan.wj.pojo.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressDAO extends JpaRepository<Address, Integer> {
    List<Address> findAllByUid(int uid);
    Address findAddressById(int id);
}
