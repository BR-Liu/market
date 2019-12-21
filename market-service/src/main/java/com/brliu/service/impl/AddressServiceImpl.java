package com.brliu.service.impl;

import com.brliu.domain.bo.AddressBO;
import com.brliu.domain.entity.UserAddress;
import com.brliu.enums.YesOrNoEnum;
import com.brliu.mapper.UserAddressMapper;
import com.brliu.service.interfaces.AddressService;
import lombok.RequiredArgsConstructor;
import org.n3r.idworker.Sid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.weekend.WeekendSqls;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AddressServiceImpl implements AddressService {

    private final UserAddressMapper userAddressMapper;

    private final Sid sid;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<UserAddress> queryAll(String userId) {

        UserAddress ua = new UserAddress();
        ua.setUserId(userId);

        return userAddressMapper.select(ua);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void addNewUserAddress(AddressBO addressBO) {

        // 1. 判断当前用户是否存在地址，如果没有，则新增为‘默认地址’
        Integer isDefault = 0;
        List<UserAddress> addressList = this.queryAll(addressBO.getUserId());
        if (addressList == null || addressList.isEmpty() || addressList.size() == 0) {
            isDefault = 1;
        }

        String addressId = sid.nextShort();

        // 2. 保存地址到数据库
        UserAddress newAddress = UserAddress.builder()
                .id(addressId)
                .isDefault(isDefault)
                .createdTime(new Date())
                .updatedTime(new Date())
                .build();
        BeanUtils.copyProperties(addressBO, newAddress);

        userAddressMapper.insertSelective(newAddress);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void updateUserAddress(AddressBO addressBO) {

        String addressId = addressBO.getAddressId();

        UserAddress pendingAddress = new UserAddress();
        BeanUtils.copyProperties(addressBO, pendingAddress);

        pendingAddress.setId(addressId);
        pendingAddress.setUpdatedTime(new Date());

        userAddressMapper.updateByPrimaryKeySelective(pendingAddress);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void deleteUserAddress(String userId, String addressId) {

        userAddressMapper.deleteByExample(
                Example.builder(UserAddress.class)
                        .where(WeekendSqls.<UserAddress>custom()
                                .andEqualTo(UserAddress::getId, addressId)
                                .andEqualTo(UserAddress::getUserId, userId))
                        .build()
        );
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void updateUserAddressToBeDefault(String userId, String addressId) {

        // 1. 查找默认地址，设置为不默认
        List<UserAddress> list = userAddressMapper.selectByExample(
                Example.builder(UserAddress.class)
                        .where(WeekendSqls.<UserAddress>custom()
                                .andEqualTo(UserAddress::getIsDefault, YesOrNoEnum.YES.type)
                                .andEqualTo(UserAddress::getUserId, userId))
                        .build()
        );
        for (UserAddress ua : list) {
            ua.setIsDefault(YesOrNoEnum.NO.type);
            userAddressMapper.updateByPrimaryKeySelective(ua);
        }

        // 2. 根据地址id修改为默认的地址
        userAddressMapper.updateByPrimaryKeySelective(
                UserAddress.builder()
                        .id(addressId)
                        .userId(userId)
                        .isDefault(YesOrNoEnum.YES.type)
                        .build()
        );
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public UserAddress queryUserAddres(String userId, String addressId) {

        return userAddressMapper.selectOneByExample(
                Example.builder(UserAddress.class)
                        .where(WeekendSqls.<UserAddress>custom()
                                .andEqualTo(UserAddress::getId, addressId)
                                .andEqualTo(UserAddress::getUserId, userId))
                        .build()
        );
    }
}
