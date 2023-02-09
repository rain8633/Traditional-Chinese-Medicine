package com.syxu.yaofang.service;

import com.syxu.yaofang.model.Client;

import java.text.ParseException;

public interface ClientService {

    Object activation(String email, String validateCode) throws ParseException;

    Client getClientById(Integer conId);

    Client findClientByName(String userName);

    Client findClientByEmail(String email);

    int addClient(Client client);

    Client findClientByNameAndPw(String userName, String password);
}
