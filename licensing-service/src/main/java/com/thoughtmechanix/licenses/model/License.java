package com.thoughtmechanix.licenses.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author jianghongsen
 */
@Entity
@Table(name = "license")
public class License {

    @Id
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "product_name", nullable = false)
    private String productName;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "organization_id", nullable = false)
    private int organizationId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(int organizationId) {
        this.organizationId = organizationId;
    }
}
