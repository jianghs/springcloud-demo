package com.thoughtmechainix.organizationservice.repository;

import com.thoughtmechainix.organizationservice.model.Organization;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author jianghongsen
 */
@Repository
public interface OrganizationRepository extends CrudRepository<Organization, String> {

    Organization findByOrganizationId(long id);

    void deleteByOrganizationId(long id);

}
