package com.thoughtmechainix.organizationservice.service;

import com.thoughtmechainix.organizationservice.model.Organization;
import com.thoughtmechainix.organizationservice.repository.OrganizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * @author jianghongsen
 */
@Service
@Transactional(rollbackOn = Exception.class)
public class OrganizationService {
    @Autowired
    OrganizationRepository organizationRepository;

    public Organization getOrg(int id) {
        return organizationRepository.findByOrganizationId(id);
    }

    public Organization saveOrg(Organization organization) {
        return organizationRepository.save(organization);
    }

    public Organization updateOrg(Organization organization) {
        return organizationRepository.save(organization);
    }

    public void deleteOrg(int id) {
        organizationRepository.deleteByOrganizationId(id);
    }
}
