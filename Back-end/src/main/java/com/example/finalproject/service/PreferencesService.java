package com.example.finalproject.service;

import com.example.finalproject.dto.PreferencesDTO;
import com.example.finalproject.entity.Customer;
import com.example.finalproject.entity.Preferences;
import com.example.finalproject.repository.CustomerRepository;
import com.example.finalproject.repository.PreferencesRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PreferencesService {

    private final PreferencesRepository preferencesRepository;
    private final CustomerRepository customerRepository;

    public PreferencesService(PreferencesRepository preferencesRepository, CustomerRepository customerRepository) {
        this.preferencesRepository = preferencesRepository;
        this.customerRepository = customerRepository;
    }

    public List<PreferencesDTO> getAllPreferences() {
        return preferencesRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<PreferencesDTO> getPreferencesByCustomerId(Integer customerId) {
        return preferencesRepository.findByCustomer_Id(customerId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public PreferencesDTO getPreferenceById(Integer id) {
        return preferencesRepository.findById(id)
                .map(this::convertToDto)
                .orElseThrow(() -> new RuntimeException("❌ Preference not found!"));
    }

    public PreferencesDTO createPreference(PreferencesDTO preferencesDTO) {
        Customer customer = customerRepository.findById(preferencesDTO.getCustomerId())
                .orElseThrow(() -> new RuntimeException("❌ Customer not found!"));

        Preferences preference = new Preferences(customer, preferencesDTO.getName());
        return convertToDto(preferencesRepository.save(preference));
    }

    public PreferencesDTO updatePreference(Integer id, PreferencesDTO preferencesDTO) {
        Preferences preference = preferencesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("❌ Preference not found!"));

        Customer customer = customerRepository.findById(preferencesDTO.getCustomerId())
                .orElseThrow(() -> new RuntimeException("❌ Customer not found!"));

        preference.setName(preferencesDTO.getName());
        preference.setCustomer(customer);

        return convertToDto(preferencesRepository.save(preference));
    }

    public void deletePreference(Integer id) {
        if (!preferencesRepository.existsById(id)) {
            throw new RuntimeException("❌ Preference not found!");
        }
        preferencesRepository.deleteById(id);
    }

    private PreferencesDTO convertToDto(Preferences preference) {
        return new PreferencesDTO(preference.getId(), preference.getCustomer().getId(), preference.getName());
    }
}
