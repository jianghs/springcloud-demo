package com.thoughtmechanix.licenses.client;

import com.thoughtmechanix.licenses.dto.OrganizationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;

@Component
public class OrganizationDiscoveryClient {

    private DiscoveryClient discoveryClient;

    @Autowired
    public void setDiscoveryClient(DiscoveryClient discoveryClient) {
        this.discoveryClient = discoveryClient;
    }

    public OrganizationDTO getOrganization(long organizationId) {
        RestTemplate restTemplate = new RestTemplate();
        List<ServiceInstance> instanceList = discoveryClient.getInstances("organizationservice");

        if (Objects.isNull(instanceList) || instanceList.size() == 0) {
            return null;
        }
        String serviceUri = String.format("%s/v1/organization/%s", instanceList.get(0).getUri().toString(), organizationId);

        ResponseEntity<OrganizationDTO> restExchange = restTemplate.exchange(serviceUri, HttpMethod.GET, null, OrganizationDTO.class, organizationId);
        return restExchange.getBody();
    }
}
