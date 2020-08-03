package com.thoughtmechanix.licenses.client;

import com.thoughtmechanix.licenses.dto.OrganizationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class OrganizationRestClient {
    private RestTemplate restTemplate;
    @Autowired
    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public OrganizationDTO getOrganization(long organizationId) {
        String serviceUri = "http://organizationservice/v1/organization/{organizationId}";
        ResponseEntity<OrganizationDTO> restExchange = restTemplate.exchange(serviceUri, HttpMethod.GET, null, OrganizationDTO.class, organizationId);
        return restExchange.getBody();
    }
}
