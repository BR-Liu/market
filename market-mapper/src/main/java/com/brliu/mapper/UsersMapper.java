package com.brliu.mapper;

import com.brliu.mymapper.MyMapper;
import com.brliu.domain.entity.Users;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersMapper extends MyMapper<Users> {
}