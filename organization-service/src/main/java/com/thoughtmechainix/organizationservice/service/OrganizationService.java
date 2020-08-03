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
public class OrganizationService {
    OrganizationRepository organizationRepository;

    @Autowired
    public void setOrganizationRepository(OrganizationRepository organizationRepository) {
        this.organizationRepository = organizationRepository;
    }

    public Organization getOrg(long id) {
        return organizationRepository.findByOrganizationId(id);
    }

    @Transactional(rollbackOn = Exception.class)
    public Organization saveOrg(Organization organization) {
        return organizationRepository.save(organization);
    }

    @Transactional(rollbackOn = Exception.class)
    public Organization updateOrg(Organization organization) {
        return organizationRepository.save(organization);
    }

    @Transactional(rollbackOn = Exception.class)
    public void deleteOrg(long id) {
        organizationRepository.deleteByOrganizationId(id);
    }
}
