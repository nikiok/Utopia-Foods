package com.sky.mapper;

import com.sky.annotation.AutoFill;
import com.sky.entity.OrderDetail;
import com.sky.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;


@Mapper
public interface OrderDetailMapper {


	void insertBatch(List<OrderDetail> orderDetails );


}
