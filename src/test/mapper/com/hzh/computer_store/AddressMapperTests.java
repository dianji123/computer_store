package com.hzh.computer_store;

import com.hzh.computer_store.entity.Address;
import com.hzh.computer_store.mapper.AddressMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class AddressMapperTests {

    @Autowired
    private AddressMapper addressMapper;

    @Test
    public void insert() {
        Address address = new Address();
        address.setUid(12);
        address.setPhone("15512345678");
        address.setName("老王");
        addressMapper.insert(address);
    }

    @Test
    public void countByUid() {
        addressMapper.countByUid(12);
    }

    @Test
    public void findByUid() {
        List<Address> addressList = addressMapper.findByUid(3);
        System.out.println(addressList);
    }

    @Test
    public void findByAid() {
        System.err.println(addressMapper.findByAid(4));
    }

    @Test
    public void updateNonDefault() {
        System.out.println(addressMapper.updateNonDefault(3));
    }

    @Test
    public void updateDefaultByAid() {
        addressMapper.updateDefaultByAid(6, "kk", new Date());
    }

    @Test
    public void deleteByAid() {
        System.out.println(addressMapper.deleteByAid(12));
    }

    @Test
    public void findLastModified() {
        System.out.println(addressMapper.findLastModified(12));
    }


}
