package com.hzh.computer_store.service;

import com.hzh.computer_store.entity.Address;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class AddressServiceTests {

    @Autowired
    private IAddressService addressService;

    @Test
    public void addNewAddress() {
        Address address = new Address();
        address.setPhone("12345678");
        address.setName("小李");
        addressService.addNewAddress(11, "admin", address);
    }

    @Test
    public void getByUid() {
        System.out.println(addressService.getByUid(3));
    }

    @Test
    public void setDefault() {
        addressService.setDefault(5, 3, "yo");
    }

    @Test
    public void delete() {
        addressService.delete(8, 3, "admin删除");
    }


}
