package com.thoughtmechanix.licenses.controllers;

import com.thoughtmechanix.licenses.vo.LicenseVO;
import com.thoughtmechanix.licenses.dto.OrganizationDTO;
import com.thoughtmechanix.licenses.model.License;
import com.thoughtmechanix.licenses.services.LicenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

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

    @RequestMapping(value = "/licenses/{licenseId}", method = RequestMethod.GET)
    public License getLicense(@PathVariable("licenseId") long licenseId) {
        return licenseService.getLicense(licenseId);
    }

    @RequestMapping(value = "/licenses/organization/{organizationId}", method = RequestMethod.GET)
    public List<License> getLicense(@PathVariable("organizationId") int organizationId) {
        return licenseService.getLicensesByOrg(organizationId);
    }

    @RequestMapping(value = "/licensesAndOrganization/{licenseId}/{clientType}")
    public LicenseVO getLicencesAndOrganizationWithClient(@PathVariable("licenseId") long licenseId, @PathVariable("clientType")String clientType) {
        License license = licenseService.getLicense(licenseId);
        if (Objects.isNull(license)) {
            return null;
        }
        LicenseVO licenseVO = new LicenseVO();
        licenseVO.setId(license.getId());
        licenseVO.setProductName(license.getProductName());
        licenseVO.setType(license.getType());
        long organizationId = license.getOrganizationId();
        OrganizationDTO organizationDTO = licenseService.getOrganizationById(organizationId, clientType);
        if (Objects.nonNull(organizationDTO)) {
            licenseVO.setName(organizationDTO.getName());
            licenseVO.setContactEmail(organizationDTO.getContactEmail());
            licenseVO.setContactName(organizationDTO.getContactName());
            licenseVO.setContactPhone(organizationDTO.getContactPhone());
        }
        return licenseVO;
    }
}
