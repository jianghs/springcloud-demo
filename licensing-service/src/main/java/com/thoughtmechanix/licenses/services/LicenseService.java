package com.thoughtmechanix.licenses.services;

import com.thoughtmechanix.licenses.client.OrganizationDiscoveryClient;
import com.thoughtmechanix.licenses.client.OrganizationFeignClient;
import com.thoughtmechanix.licenses.client.OrganizationRestClient;
import com.thoughtmechanix.licenses.dto.OrganizationDTO;
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

    @Autowired
    OrganizationDiscoveryClient organizationDiscoveryClient;
    @Autowired
    OrganizationFeignClient organizationFeignClient;
    @Autowired
    OrganizationRestClient organizationRestClient;

    public License getLicense(long licenseId) {
        return licenseRepository.findLicenseById(licenseId);
    }

    public List<License> getLicensesByOrg(long organizationId) {
        return licenseRepository.findByOrganizationId(organizationId);
    }

    public OrganizationDTO getOrganizationById(long id, String clientType) {
        return retrieveOrgInfo(id, clientType);
    }

    private OrganizationDTO retrieveOrgInfo(long organizationId, String clientType){
        OrganizationDTO organization = null;

        switch (clientType) {
            case "feign":
                System.out.println("I am using the feign client");
                organization = organizationFeignClient.getOrganization(organizationId);
                break;
            case "rest":
                System.out.println("I am using the rest client");
                organization = organizationRestClient.getOrganization(organizationId);
                break;
            case "discovery":
                System.out.println("I am using the discovery client");
                organization = organizationDiscoveryClient.getOrganization(organizationId);
                break;
            default:
                organization = organizationRestClient.getOrganization(organizationId);
        }

        return organization;
    }

}
