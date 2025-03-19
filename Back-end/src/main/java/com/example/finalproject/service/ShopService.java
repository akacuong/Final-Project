package com.example.finalproject.service;

import com.example.finalproject.dto.ShopDTO;
import com.example.finalproject.entity.Agent;
import com.example.finalproject.entity.Shop;
import com.example.finalproject.repository.AgentRepository;
import com.example.finalproject.repository.ShopRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ShopService {

    private final ShopRepository shopRepository;
    private final AgentRepository agentRepository;

    public ShopService(ShopRepository shopRepository, AgentRepository agentRepository) {
        this.shopRepository = shopRepository;
        this.agentRepository = agentRepository;
    }

    public List<ShopDTO> getAllShops() {
        return shopRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public List<ShopDTO> getShopsByAgentId(Integer agentId) {
        return shopRepository.findByAgentId(agentId).stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public Optional<ShopDTO> getShopById(Integer id) {
        return shopRepository.findById(id).map(this::convertToDTO);
    }

    public ShopDTO createShop(ShopDTO shopDTO) {
        Agent agent = agentRepository.findById(shopDTO.getAgentId())
                .orElseThrow(() -> new RuntimeException("❌ Agent không tồn tại!"));

        Shop shop = new Shop(agent, shopDTO.getLocation(), shopDTO.getPhoneNumber());
        return convertToDTO(shopRepository.save(shop));
    }

    public ShopDTO updateShop(Integer id, ShopDTO shopDTO) {
        Shop shop = shopRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("❌ Shop không tồn tại!"));

        Agent agent = agentRepository.findById(shopDTO.getAgentId())
                .orElseThrow(() -> new RuntimeException("❌ Agent không tồn tại!"));

        shop.setAgent(agent);
        shop.setLocation(shopDTO.getLocation());
        shop.setPhoneNumber(shopDTO.getPhoneNumber());

        return convertToDTO(shopRepository.save(shop));
    }

    public void deleteShop(Integer id) {
        if (!shopRepository.existsById(id)) {
            throw new RuntimeException("❌ Shop không tồn tại!");
        }
        shopRepository.deleteById(id);
    }

    private ShopDTO convertToDTO(Shop shop) {
        return new ShopDTO(shop.getId(), shop.getAgent().getId(), shop.getLocation(), shop.getPhoneNumber());
    }
}
