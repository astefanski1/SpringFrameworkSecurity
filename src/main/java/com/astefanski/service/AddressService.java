package com.astefanski.service;

import com.astefanski.model.Address;
import com.astefanski.model.User;
import com.astefanski.repository.AddressRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@Slf4j
public class AddressService {

    @Resource
    private AddressRepository addressRepository;

    public Address createAddress(Address address) {
        return addressRepository.save(address);
    }

    public Address assignCustomerToAddress(Address address, User user) {
        address.setUserAddress(user);

        return addressRepository.save(address);
    }
}
