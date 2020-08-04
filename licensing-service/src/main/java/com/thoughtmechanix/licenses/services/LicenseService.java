package com.thoughtmechanix.licenses.services;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.thoughtmechanix.licenses.client.OrganizationDiscoveryClient;
import com.thoughtmechanix.licenses.client.OrganizationFeignClient;
import com.thoughtmechanix.licenses.client.OrganizationRestClient;
import com.thoughtmechanix.licenses.dto.OrganizationDTO;
import com.thoughtmechanix.licenses.model.License;
import com.thoughtmechanix.licenses.repository.LicenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author jianghongsen
 */
@Service
public class LicenseService {

    LicenseRepository licenseRepository;

    @Autowired
    public void setLicenseRepository(LicenseRepository licenseRepository) {
        this.licenseRepository = licenseRepository;
    }

    @Autowired
    OrganizationDiscoveryClient organizationDiscoveryClient;
    @Autowired
    OrganizationFeignClient organizationFeignClient;
    @Autowired
    OrganizationRestClient organizationRestClient;

    public License getLicense(long licenseId) {
        return licenseRepository.findLicenseById(licenseId);
    }

    /**
     * 断路器演示：1、设置超时时间 2、设置后备模式 3、设置舱壁模式
     *
     * @param organizationId
     * @return
     */
    @HystrixCommand(
            commandProperties = {
                @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000"), // 超时时间，默认为1秒
                @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"), // 跳闸之前，10秒内必须发生连续调用的次数
                @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "75"), // 跳闸之前，超过连续调用次数后，调用失败的百分比
                @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "7000"), // 跳闸后，允许另外一个服务调用检查是否恢复的休眠时间
                @HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds", value = "15000"), // 监视服务调用问题的窗口大小，默认是10秒
                @HystrixProperty(name = "metrics.rollingStats.numBuckets", value = "5")// 滚动窗口中统计信息的次数，后面两个参数必须可以整除
            },
            fallbackMethod = "buildFallbackLicensesByOrg",
            threadPoolKey = "licensesByOrgThreadPool",
            threadPoolProperties = {
                    @HystrixProperty(name = "coreSize", value = "30"),
                    @HystrixProperty(name = "maxQueueSize", value = "10")
            })
    public List<License> getLicensesByOrg(long organizationId) {
        this.randomRunLong();
        return licenseRepository.findByOrganizationId(organizationId);
    }

    public OrganizationDTO getOrganizationById(long id, String clientType) {
        return retrieveOrgInfo(id, clientType);
    }

    @HystrixCommand(
            commandProperties = {
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000")
            },
            fallbackMethod = "buildFallbackRetrieveOrgInfo",
            threadPoolKey = "retrieveOrgInfoThreadPool",
            threadPoolProperties = {
                    @HystrixProperty(name = "coreSize", value = "30"),
                    @HystrixProperty(name = "maxQueueSize", value = "10")
            })
    private OrganizationDTO retrieveOrgInfo(long organizationId, String clientType) {
        OrganizationDTO organization = null;

        switch (clientType) {
            case "feign":
                System.out.println("I am using the feign client");
                organization = organizationFeignClient.getOrganization(organizationId);
                break;
            case "rest":
                System.out.println("I am using the rest client");
                organization = organizationRestClient.getOrganization(organizationId);
                break;
            case "discovery":
                System.out.println("I am using the discovery client");
                organization = organizationDiscoveryClient.getOrganization(organizationId);
                break;
            default:
                organization = organizationRestClient.getOrganization(organizationId);
        }

        return organization;
    }

    private void randomRunLong() {
        Random random = new Random();
        int randomNum = random.nextInt(3) + 1;
        System.out.println(randomNum);
        if (randomNum == 3) {
            try {
                Thread.sleep(5000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private List<License> buildFallbackLicensesByOrg(long organizationId) {
        List<License> fallbackList = new ArrayList<>();
        License license = new License();
        license.setId(1l);
        license.setOrganizationId(organizationId);
        license.setProductName("error");
        license.setType(null);
        fallbackList.add(license);
        return fallbackList;
    }

    private OrganizationDTO buildFallbackRetrieveOrgInfo(long organizationId, String clientType) {
        OrganizationDTO organizationDTO = new OrganizationDTO();
        organizationDTO.setId(organizationId);
        organizationDTO.setName("no exists");
        return organizationDTO;
    }

}
