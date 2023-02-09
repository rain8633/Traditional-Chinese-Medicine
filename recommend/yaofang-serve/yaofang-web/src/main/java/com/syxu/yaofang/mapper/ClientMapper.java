package com.syxu.yaofang.mapper;

import com.syxu.yaofang.model.Client;

public interface ClientMapper {

    int updateUserMsg(Client consumer);

    Client findByEmail(String email);

    Client findClientById(Integer Id);

    Client findClientByName(String userName);

    int addClient(Client client);

    Client findClientByNameAndPw(String userName, String password);
}
