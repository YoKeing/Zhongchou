package com.crowd.service.impl;

import com.crowd.entity.po.AddressPO;
import com.crowd.entity.po.AddressPOExample;
import com.crowd.entity.po.OrderPO;
import com.crowd.entity.po.OrderProjectPO;
import com.crowd.entity.vo.AddressVO;
import com.crowd.entity.vo.OrderProjectVO;
import com.crowd.entity.vo.OrderVO;
import com.crowd.mapper.AddressPOMapper;
import com.crowd.mapper.OrderPOMapper;
import com.crowd.mapper.OrderProjectPOMapper;
import com.crowd.service.api.OrderService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true)
public class OrderServiceImpl implements OrderService {


    @Autowired
    private OrderProjectPOMapper orderProjectPOMapper;

    @Autowired
    private OrderPOMapper orderPOMapper;

    @Autowired
    private AddressPOMapper addressPOMapper;

    @Override
    public OrderProjectVO getOrderProjectVO(Integer projectId, Integer returnId) {
        return orderProjectPOMapper.selectOrderProjectVO(returnId);
    }

    @Override
    public List<AddressVO> getAddressVOList(Integer memberId) {

        AddressPOExample example = new AddressPOExample();

        example.createCriteria().andMemberIdEqualTo(memberId);

        List<AddressPO> addressPOList = addressPOMapper.selectByExample(example);
        ArrayList<AddressVO> addressVOList = new ArrayList<>();

        for (AddressPO addressPO : addressPOList){
            AddressVO addressVO = new AddressVO();
            BeanUtils.copyProperties(addressPO, addressVO);
            addressVOList.add(addressVO);
        }

        return addressVOList;
    }

    @Override
    public void saveAddress(AddressVO addressVO) {

        AddressPO addressPO = new AddressPO();

        BeanUtils.copyProperties(addressVO, addressPO);

        addressPOMapper.insert(addressPO);


    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    @Override
    public void saveOrder(OrderVO orderVO) {

        OrderPO orderPO = new OrderPO();
        BeanUtils.copyProperties(orderVO, orderPO);

        OrderProjectPO orderProjectPO = new OrderProjectPO();
        BeanUtils.copyProperties(orderVO.getOrderProjectVO(), orderProjectPO);

//        orderPOMapper.insert(orderPO);

        Integer id = orderPO.getId();
        orderProjectPO.setOrderId(id);

        orderProjectPOMapper.insert(orderProjectPO);

    }
}
