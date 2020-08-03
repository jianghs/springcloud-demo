package com.thoughtmechanix.licenses.client;

import com.thoughtmechanix.licenses.dto.OrganizationDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author jianghongsen
 */
@FeignClient("organizationservice")
public interface OrganizationFeignClient {
    @RequestMapping(method = RequestMethod.GET, value = "v1/organization/{organizationId}", consumes = "application/json")
    OrganizationDTO getOrganization(@PathVariable("organizationId") long organizationId);
}
