package com.syxu.yaofang.service.Impl;

import com.alibaba.fastjson.JSONObject;
import com.syxu.yaofang.mapper.ClientMapper;
import com.syxu.yaofang.model.Client;
import com.syxu.yaofang.service.ClientService;
import com.syxu.yaofang.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    private ClientMapper clientMapper;

    /**
     * 激活账户
     *
     * @param email
     * @param validateCode
     * @return
     * @throws ParseException
     */

    @Transactional
    @Override
    public Object activation(String email, String validateCode) throws ParseException {
        JSONObject jsonObject=new JSONObject();
        try {
            Client consumer=clientMapper.findByEmail(email);
            if (consumer == null) {
                jsonObject.put("msg","未查询到该邮箱，请核对信息！");
                return jsonObject;
            }
            String nowDate = DateUtil.getCurrentDateToString();
            Date start = consumer.getCreateTime();

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d HH:mm:ss");

            Date end = sdf.parse(nowDate);
            long cha = end.getTime() - start.getTime();
            double result = cha * 1.0 / (1000 * 60 * 60);
            if (result > 24) {
                jsonObject.put("msg","激活邮件已过期，请重试！");
                return jsonObject;
            }
            if (!validateCode.equals(consumer.getValidateCode())) {
                jsonObject.put("msg","激活码错误，请联系管理员！");
                return jsonObject;
            }
            if (consumer.getEmailStatus() == 1) {
                jsonObject.put("msg","账户已被激活，请勿重复操作！");
                return jsonObject;

            }
            consumer.setEmailStatus(1);

            clientMapper.updateUserMsg(consumer);
            jsonObject.put("code",1);
            jsonObject.put("msg","success");
            return jsonObject;
        } catch (Exception e) {
            e.printStackTrace();
            jsonObject.put("msg","激活账户异常，请稍后重试！");
            return jsonObject;
        }
    }

    @Override
    public Client getClientById(Integer Id) {
        return clientMapper.findClientById(Id);
    }

    @Override
    public Client findClientByName(String userName) {
        return clientMapper.findClientByName(userName);
    }

    @Override
    public Client findClientByEmail(String email) {
        return clientMapper.findByEmail(email);
    }

    @Override
    public int addClient(Client client) {
        return clientMapper.addClient(client);
    }

    @Override
    public Client findClientByNameAndPw(String userName, String password) {
        return clientMapper.findClientByNameAndPw(userName,password);
    }
}
