package com.thoughtmechanix.licenses.controllers;

import com.thoughtmechanix.licenses.model.License;
import com.thoughtmechanix.licenses.services.LicenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author jianghongsen
 */
@RestController
@RequestMapping(value = "/v1")
public class LicenseServiceController {

    LicenseService licenseService;

    @Autowired
    public void setLicenseService(LicenseService licenseService) {
        this.licenseService = licenseService;
    }

    @RequestMapping(value = "/organizations/{organizationId}/licenses/{licenseId}", method = RequestMethod.GET)
    public License getLicense(@PathVariable("organizationId") int organizationId,
                              @PathVariable("licenseId") int licenseId) {
        return licenseService.getLicense(organizationId, licenseId);
    }

    @RequestMapping(value = "/organizations/{organizationId}", method = RequestMethod.GET)
    public List<License> getLicense(@PathVariable("organizationId") int organizationId) {
        return licenseService.getLicensesByOrg(organizationId);
    }
}
