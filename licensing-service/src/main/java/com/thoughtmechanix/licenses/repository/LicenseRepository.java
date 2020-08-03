package com.thoughtmechanix.licenses.repository;

import com.thoughtmechanix.licenses.model.License;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author jianghongsen
 */
public interface LicenseRepository extends CrudRepository<License, String> {

    /**
     * find By OrganizationId
     *
     * @param organizationId
     * @return Result<License>
     */
    List<License> findByOrganizationId(long organizationId);

    /**
     * find By licenseId
     * @param id
     * @return Result<License>
     */
    License findLicenseById(long id);
}
