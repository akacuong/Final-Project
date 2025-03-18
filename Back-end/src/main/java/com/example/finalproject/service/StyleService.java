package com.example.finalproject.service;

import com.example.finalproject.dto.StyleDTO;
import com.example.finalproject.entity.Agent;
import com.example.finalproject.entity.Style;
import com.example.finalproject.repository.AgentRepository;
import com.example.finalproject.repository.StyleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StyleService {

    private final StyleRepository styleRepository;
    private final AgentRepository agentRepository;

    public StyleService(StyleRepository styleRepository, AgentRepository agentRepository) {
        this.styleRepository = styleRepository;
        this.agentRepository = agentRepository;
    }

    // ✅ Lấy tất cả Style
    public List<StyleDTO> getAllStyles() {
        return styleRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    // ✅ Lấy Style theo ID
    public Optional<StyleDTO> getStyleById(Long id) {
        return styleRepository.findById(id).map(this::convertToDTO);
    }

    // ✅ Lấy Style theo Agent ID
    public List<StyleDTO> getStylesByAgentId(Integer agentId) {
        return styleRepository.findByAgentId(agentId).stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    // ✅ Thêm Style mới
    public Optional<StyleDTO> createStyle(StyleDTO styleDTO) {
        Optional<Agent> agentOpt = agentRepository.findById(styleDTO.getAgentId());
        if (agentOpt.isEmpty()) {
            return Optional.empty(); // ❌ Agent không tồn tại
        }
        Style style = new Style(styleDTO.getName(), agentOpt.get());
        Style savedStyle = styleRepository.save(style);
        return Optional.of(convertToDTO(savedStyle)); // ✅ Convert Entity -> DTO
    }

    // ✅ Cập nhật Style
    public Optional<StyleDTO> updateStyle(Long id, StyleDTO styleDTO) {
        Optional<Style> styleOpt = styleRepository.findById(id);
        if (styleOpt.isEmpty()) {
            return Optional.empty(); // ❌ Style không tồn tại
        }
        Optional<Agent> agentOpt = agentRepository.findById(styleDTO.getAgentId());
        if (agentOpt.isEmpty()) {
            return Optional.empty(); // ❌ Agent không tồn tại
        }
        Style style = styleOpt.get();
        style.setName(styleDTO.getName());
        style.setAgent(agentOpt.get());
        Style updatedStyle = styleRepository.save(style);
        return Optional.of(convertToDTO(updatedStyle));
    }

    // ✅ Xóa Style
    public boolean deleteStyle(Long id) {
        if (!styleRepository.existsById(id)) {
            return false; // ❌ Style không tồn tại
        }
        styleRepository.deleteById(id);
        return true;
    }

    // ✅ Chuyển đổi Entity -> DTO
    private StyleDTO convertToDTO(Style style) {
        return new StyleDTO(style.getId(), style.getName(), style.getAgent().getId());
    }
}
