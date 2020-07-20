package com.thoughtmechainix.organizationservice.controller;

import com.thoughtmechainix.organizationservice.model.Organization;
import com.thoughtmechainix.organizationservice.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * @author jianghongsen
 */
@RestController
@RequestMapping(value="v1")
public class OrganizationServiceController {
    @Autowired
    private OrganizationService orgService;


    @RequestMapping(value="/organization/{organizationId}",method = RequestMethod.GET)
    public Organization getOrganization(@PathVariable("organizationId") int organizationId) {
        return orgService.getOrg(organizationId);
    }

    @RequestMapping(value="/organization/{organizationId}",method = RequestMethod.PUT)
    public void updateOrganization( @PathVariable("organizationId") int orgId, @RequestBody Organization org) {
        org.setOrganizationId(orgId);
        orgService.updateOrg( org );
    }

    @RequestMapping(value="/organization",method = RequestMethod.POST)
    public void saveOrganization(@RequestBody Organization org) {
        orgService.saveOrg( org );
    }

    @RequestMapping(value="/organization/{organizationId}",method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrganization( @PathVariable("organizationId") int orgId) {
        orgService.deleteOrg(orgId);
    }
}
