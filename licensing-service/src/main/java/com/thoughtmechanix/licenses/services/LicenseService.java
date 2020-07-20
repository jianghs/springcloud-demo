package com.thoughtmechanix.licenses.services;

import com.thoughtmechanix.licenses.model.License;
import com.thoughtmechanix.licenses.repository.LicenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author jianghongsen
 */
@Service
public class LicenseService {

    LicenseRepository licenseRepository;

    @Autowired
    public void setLicenseRepository(LicenseRepository licenseRepository) {
        this.licenseRepository = licenseRepository;
    }

    public License getLicense(int organizationId, int licenseId) {
        return licenseRepository.findByOrganizationIdAndId(organizationId, licenseId);
    }

    public List<License> getLicensesByOrg(int organizationId) {
        return licenseRepository.findByOrganizationId(organizationId);
    }

}
