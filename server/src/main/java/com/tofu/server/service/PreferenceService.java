package com.tofu.server.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.tofu.server.models.Preference;
import com.tofu.server.repository.PreferenceRepository;

@Service
public class PreferenceService {
    
    @Autowired
    PreferenceRepository prefRepo;

    public List<Preference> getAllPreferencesByEmployeeId(Integer employeeId) {
        return prefRepo.getAllPreferencesByEmployeeId(employeeId);
    }

    public Preference getPreference(String preferenceId) {
        return prefRepo.getPreference(preferenceId);
    }

    public Boolean insertPreference(Preference preference) {
        Integer employeeId = preference.getEmployeeId();
        // Create preferenceId
        String lastPreferenceId = prefRepo.getLastPreferenceIdByEmployeeId(employeeId);
        String newPreferenceId;
        if (lastPreferenceId != null) {
            int lastSerialNumber = Integer.parseInt(lastPreferenceId.substring(lastPreferenceId.lastIndexOf('P') + 1));
            int newSerialNumber = lastSerialNumber + 1;
            newPreferenceId = String.format("%sP%03d", employeeId, newSerialNumber);
        } else {
            newPreferenceId = String.format("%sP%03d", employeeId, 1);
        }
        preference.setPreferenceId(newPreferenceId);
        return prefRepo.insertPreference(preference);
    }
    
    public Boolean updatePreference(Preference preference, String preferenceId) {
        return prefRepo.updatePreference(preference, preferenceId);
    }

    public Boolean deletePreference(String preferenceId) {
        return prefRepo.deletePreference(preferenceId);
    }

}
