package com.bizzdesk.group.organization.management.controller;

import com.bizzdesk.group.organization.management.service.OrganizationManagementService;
import com.gotax.framework.library.entity.helpers.*;
import com.gotax.framework.library.error.handling.GoTaxException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OrganizationController {

    private OrganizationManagementService organizationManagementService;

    @Autowired
    public OrganizationController(OrganizationManagementService organizationManagementService) {
        this.organizationManagementService = organizationManagementService;
    }

    @PostMapping("/v1/create/organization")
    public void createOrganization(@RequestBody OrganizationHelperRequest organizationHelper) throws GoTaxException {
        organizationManagementService.createOrganization(organizationHelper);
    }

    @PostMapping("/v1/create/sub/organization")
    public void createSubOrganization(@RequestBody SubOrganizationHelperRequest subOrganizationHelperRequest) throws GoTaxException {
        organizationManagementService.createSubOrganization(subOrganizationHelperRequest);
    }

    @PostMapping("/v1/create/organization/type")
    public void createOrganizationType(OrganizationTypeHelper organizationTypeHelper) throws GoTaxException {
        organizationManagementService.createOrganizationType(organizationTypeHelper);
    }

    @GetMapping("/v1/list/organization/types")
    public List<OrganizationTypeHelper> listOrganizationTypes() {
        return organizationManagementService.listOrganizationTypes();
    }

    @GetMapping("/v1/list/organizations")
    public List<OrganizationHelperResponse> listOrganizations() {
        return organizationManagementService.listOrganizations();
    }

    @GetMapping("/v1/list/sub/organizations")
    public List<SubOrganizationHelperResponse> listSubOrganizations() {
        return organizationManagementService.listSubOrganizations();
    }

    @GetMapping("/v1/list/sub/organizations/id")
    public List<SubOrganizationHelperResponse> listSubOrganizationsByOrganizations(@RequestParam("organizationId") Long organizationId) throws GoTaxException {
        return organizationManagementService.listSubOrganizationsByOrganizations(organizationId);
    }

    @GetMapping("/v1/list/organizations/retention")
    public List<OrganizationHelperResponse> listOrganizationsByRetention(@RequestParam("retention") boolean retention) {
        return organizationManagementService.listOrganizationsByRetention(retention);
    }

    @GetMapping("/v1/list/organizations/id")
    public List<OrganizationHelperResponse> listOrganizationsByOrganizationType(@RequestParam("organizationTypeId") String organizationTypeId) throws GoTaxException {
        return organizationManagementService.listOrganizationsByOrganizationType(organizationTypeId);
    }

    @GetMapping("/v1/list/organizations/code")
    public List<OrganizationHelperResponse> listOrganizationsByOrganizationCode(@RequestParam("organizationCode") String organizationCode) throws GoTaxException {
        return organizationManagementService.listOrganizationsByOrganizationCode(organizationCode);
    }

    @PutMapping("/v1/update/organization")
    public void updateOrganization(@RequestParam("organizationId") Long organizationId, @RequestBody OrganizationHelperRequest organizationHelper) throws GoTaxException {
        organizationManagementService.updateOrganization(organizationId, organizationHelper);
    }
}
