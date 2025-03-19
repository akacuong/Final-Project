package com.example.finalproject.service;

import com.example.finalproject.dto.PackageDTO;
import com.example.finalproject.entity.Agent;
import com.example.finalproject.entity.Package;
import com.example.finalproject.repository.AgentRepository;
import com.example.finalproject.repository.PackageRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PackageService {

    private final PackageRepository packageRepository;
    private final AgentRepository agentRepository;

    public PackageService(PackageRepository packageRepository, AgentRepository agentRepository) {
        this.packageRepository = packageRepository;
        this.agentRepository = agentRepository;
    }

    public List<PackageDTO> getAllPackages() {
        return packageRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public PackageDTO getPackageById(Integer id) {
        return packageRepository.findById(id)
                .map(this::convertToDto)
                .orElseThrow(() -> new RuntimeException("❌ Package not found!"));
    }

    public List<PackageDTO> getPackagesByAgentId(Integer agentId) {
        return packageRepository.findByAgent_Id(agentId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public PackageDTO createPackage(PackageDTO packageDTO) {
        Agent agent = agentRepository.findById(packageDTO.getAgentId())
                .orElseThrow(() -> new RuntimeException("❌ Agent not found!"));

        Package pkg = new Package(
                packageDTO.getName(),
                packageDTO.getPrice(),
                packageDTO.getStatus(),
                packageDTO.getStartDate(),
                packageDTO.getEndDate(),
                agent
        );

        return convertToDto(packageRepository.save(pkg));
    }

    public PackageDTO updatePackage(Integer id, PackageDTO packageDTO) {
        Package pkg = packageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("❌ Package not found!"));

        pkg.setName(packageDTO.getName());
        pkg.setPrice(packageDTO.getPrice());
        pkg.setStatus(packageDTO.getStatus());
        pkg.setStartDate(packageDTO.getStartDate());
        pkg.setEndDate(packageDTO.getEndDate());

        return convertToDto(packageRepository.save(pkg));
    }

    public void deletePackage(Integer id) {
        if (!packageRepository.existsById(id)) {
            throw new RuntimeException("❌ Package not found!");
        }
        packageRepository.deleteById(id);
    }

    private PackageDTO convertToDto(Package pkg) {
        return new PackageDTO(pkg.getId(), pkg.getAgent().getId(), pkg.getName(), pkg.getPrice(), pkg.getStatus(), pkg.getStartDate(), pkg.getEndDate());
    }
}
