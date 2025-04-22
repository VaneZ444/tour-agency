package xyz.vanez.client.service.impl;

import org.springframework.stereotype.Service;
import xyz.vanez.client.model.Client;
import xyz.vanez.client.service.ClientService;

import java.util.HashMap;
import java.util.Map;

@Service
public class ClientServiceImpl implements ClientService {
    private final Map<String, Client> clients = new HashMap<>(); //заглушка бд

    public ClientServiceImpl() {
        clients.put("CLT-123", new Client("CLT-123", "Иван Петров", true));
        clients.put("CLT-456", new Client("CLT-456", "Мария Сидорова", false));
    }

    @Override
    public boolean verifyClient(String clientId) {
        Client client = clients.get(clientId);
        return client != null && client.isVerified();
    }
}