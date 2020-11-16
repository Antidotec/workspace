package com.evan.wj.service;

import com.evan.wj.dao.AddressDAO;
import com.evan.wj.pojo.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class AddressService {
    @Autowired
    AddressDAO addressDAO;

    public List<Address> findAllByUid(int uid) {
        List<Address> addrs = addressDAO.findAllByUid(uid);
        for(Address a:addrs)
            a.initialFormat();
        return addrs;
    }

    public void addOrUpdate(Address address) {
        addressDAO.save(address);
    }

    public void delAddress(int id) {
        addressDAO.deleteById(id);
    }

    public Address findAddressById(int id) {
        Address address = addressDAO.findAddressById(id);
        address.initialFormat();
        return address;
    }
}

