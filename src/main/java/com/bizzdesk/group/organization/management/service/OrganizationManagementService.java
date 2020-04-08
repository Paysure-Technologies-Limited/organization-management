package com.bizzdesk.group.organization.management.service;

import com.bizzdesk.group.organization.management.entities.Organization;
import com.bizzdesk.group.organization.management.entities.OrganizationType;
import com.bizzdesk.group.organization.management.entities.SubOrganization;
import com.bizzdesk.group.organization.management.mapper.MapperModel;
import com.bizzdesk.group.organization.management.repositories.OrganizationRepository;
import com.bizzdesk.group.organization.management.repositories.OrganizationTypeRepository;
import com.bizzdesk.group.organization.management.repositories.SubOrganizationRepository;
import com.gotax.framework.library.entity.helpers.*;
import com.gotax.framework.library.error.handling.GoTaxException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrganizationManagementService {
    Logger logger = LoggerFactory.getLogger(OrganizationManagementService.class.getName());

    private OrganizationRepository organizationRepository;
    private OrganizationTypeRepository organizationTypeRepository;
    private SubOrganizationRepository subOrganizationRepository;

    @Autowired
    public OrganizationManagementService(OrganizationRepository organizationRepository, OrganizationTypeRepository organizationTypeRepository, SubOrganizationRepository subOrganizationRepository) {
        this.organizationRepository = organizationRepository;
        this.organizationTypeRepository = organizationTypeRepository;
        this.subOrganizationRepository = subOrganizationRepository;
    }

    public void createOrganization(OrganizationHelperRequest organizationHelperRequest) throws GoTaxException {
        OrganizationType organizationType = organizationTypeRepository.findById(organizationHelperRequest.getOrganizationTypeId()).orElseThrow(
                () -> new GoTaxException(MessageFormat.format("The Organization Type With Id {0} Does Not Exist", organizationHelperRequest.getOrganizationTypeId()))
        );
        Organization organization = new Organization().setOrganizationCode(organizationHelperRequest.getOrganizationCode())
                .setOrganizationName(organizationHelperRequest.getOrganizationName())
                .setOrganizationType(organizationType)
                .setContactEmail(organizationHelperRequest.getContactEmail())
                .setContactPhoneNumber(organizationHelperRequest.getContactPhoneNumber())
                .setPercentageRetention(organizationHelperRequest.getPercentageRetention())
                .setPhysicalAddress(organizationHelperRequest.getPhysicalAddress())
                .setRetention(organizationHelperRequest.isRetention());
        organizationRepository.save(organization);
    }

    public void createSubOrganization(SubOrganizationHelperRequest subOrganizationHelperRequest) throws GoTaxException {
        Organization organization = organizationRepository.findById(subOrganizationHelperRequest.getOrganizationId()).orElseThrow(
                () -> new GoTaxException(MessageFormat.format("Organization Id {0} Does Not Exist", subOrganizationHelperRequest.getOrganizationId()))
        );
        SubOrganization subOrganization = new SubOrganization().setOrganization(organization)
                .setSubOrganizationCode(subOrganizationHelperRequest.getSubOrganizationCode())
                .setSubOrganizationName(subOrganizationHelperRequest.getSubOrganizationName())
                .setContactEmail(subOrganizationHelperRequest.getContactEmail())
                .setContactPhoneNumber(subOrganizationHelperRequest.getContactPhoneNumber())
                .setPhysicalAddress(subOrganizationHelperRequest.getPhysicalAddress());
        subOrganizationRepository.save(subOrganization);
    }

    public void createOrganizationType(OrganizationTypeHelper organizationTypeHelper) throws GoTaxException {
        Optional<OrganizationType> optionalOrganizationType = organizationTypeRepository.findById(organizationTypeHelper.getOrganizationTypeId());
        if(optionalOrganizationType.isPresent()) {
            throw new GoTaxException(MessageFormat.format("Organization Type With Id {0} Already Exist", organizationTypeHelper.getOrganizationTypeId()));
        } else {
            OrganizationType organizationType = new OrganizationType().setOrganizationType(organizationTypeHelper.getOrganizationTypeId())
                    .setOrganizationDescription(organizationTypeHelper.getOrganizationTypeDescription());
            organizationTypeRepository.save(organizationType);
        }
    }

    public List<OrganizationTypeHelper> listOrganizationTypes() {
        List<OrganizationTypeHelper> organizationTypeHelperList = new ArrayList<>();
        List<OrganizationType> organizationTypeList = organizationTypeRepository.findAll();
        if(!organizationTypeList.isEmpty()) {
            organizationTypeList.forEach(organizationType -> {
                OrganizationTypeHelper organizationTypeHelper = new OrganizationTypeHelper().setOrganizationTypeId(organizationType.getOrganizationType())
                        .setOrganizationTypeDescription(organizationType.getOrganizationDescription());
                organizationTypeHelperList.add(organizationTypeHelper);
            });
        }
        return organizationTypeHelperList;
    }

    public List<OrganizationHelperResponse> listOrganizations() {
        List<OrganizationHelperResponse> organizationHelperList = new ArrayList<>();
        List<Organization> organizationList = organizationRepository.findAll();
        return getOrganizationHelpers(organizationHelperList, organizationList);
    }

    public List<SubOrganizationHelperResponse> listSubOrganizations() {
        List<SubOrganizationHelperResponse> subOrganizationHelperResponseList = new ArrayList<>();
        List<SubOrganization> subOrganizationList = subOrganizationRepository.findAll();
        return getSubOrganizationHelperResponses(subOrganizationHelperResponseList, subOrganizationList);
    }

    public List<SubOrganizationHelperResponse> listSubOrganizationsByOrganizations(Long organizationId) throws GoTaxException {
        List<SubOrganizationHelperResponse> subOrganizationHelperResponseList = new ArrayList<>();
        Organization organization = organizationRepository.findById(organizationId).orElseThrow(
                () -> new GoTaxException(MessageFormat.format("Organization With Id {0} Does Not Exist", organizationId))
        );
        List<SubOrganization> subOrganizationList = subOrganizationRepository.findByOrganization(organization);
        return getSubOrganizationHelperResponses(subOrganizationHelperResponseList, subOrganizationList);
    }

    private List<SubOrganizationHelperResponse> getSubOrganizationHelperResponses(List<SubOrganizationHelperResponse> subOrganizationHelperResponseList, List<SubOrganization> subOrganizationList) {
        if(!subOrganizationList.isEmpty()) {
            subOrganizationList.forEach(subOrganization -> {
                SubOrganizationHelperResponse subOrganizationHelperResponse = MapperModel.mapSubOrganizationToResponse(subOrganization);
                subOrganizationHelperResponseList.add(subOrganizationHelperResponse);
            });
        }
        return subOrganizationHelperResponseList;
    }

    public List<OrganizationHelperResponse> listOrganizationsByRetention(boolean retention) {
        List<OrganizationHelperResponse> organizationHelperList = new ArrayList<>();
        List<Organization> organizationList = organizationRepository.findByRetention(retention);
        return getOrganizationHelpers(organizationHelperList, organizationList);
    }

    public List<OrganizationHelperResponse> listOrganizationsByOrganizationType(String organizationTypeId) throws GoTaxException {
        List<OrganizationHelperResponse> organizationHelperList = new ArrayList<>();
        OrganizationType organizationType = organizationTypeRepository.findById(organizationTypeId).orElseThrow(
                () -> new GoTaxException(MessageFormat.format("Organization Type Id {0} Does Not Exist", organizationTypeId))
        );
        List<Organization> organizationList = organizationRepository.findByOrganizationType(organizationType);
        return getOrganizationHelpers(organizationHelperList, organizationList);
    }

    public List<OrganizationHelperResponse> listOrganizationsByOrganizationCode(String organizationCode) throws GoTaxException {
        List<OrganizationHelperResponse> organizationHelperList = new ArrayList<>();
        List<Organization> organizationList = organizationRepository.findByOrganizationCode(organizationCode);
        return getOrganizationHelpers(organizationHelperList, organizationList);
    }

    private List<OrganizationHelperResponse> getOrganizationHelpers(List<OrganizationHelperResponse> organizationHelperList, List<Organization> organizationList) {
        if(!organizationList.isEmpty()) {
            organizationList.forEach(organization -> {
                OrganizationHelperResponse organizationHelper = MapperModel.mapOrganizationToHelper(organization);
                organizationHelperList.add(organizationHelper);
            });
        }
        return organizationHelperList;
    }

    public void updateOrganization(Long organizationId, OrganizationHelperRequest organizationHelper) throws GoTaxException {
        Organization organization = organizationRepository.findById(organizationId).orElseThrow(
                () -> new GoTaxException(MessageFormat.format("Organization With Id {0} Does Not Exist", organizationId))
        );
        organization.setContactEmail(organizationHelper.getContactEmail())
                .setContactPhoneNumber(organizationHelper.getContactPhoneNumber())
                .setPercentageRetention(organizationHelper.getPercentageRetention())
                .setRetention(organizationHelper.isRetention());
        organizationRepository.save(organization);
    }

    public OrganizationHelperResponse getOrganization(Long organizationId) throws GoTaxException {
        Organization organization = organizationRepository.findById(organizationId).orElseThrow(
                () -> new GoTaxException(MessageFormat.format("Organization With Id {0} Does Not Exist", organizationId))
        );
        return MapperModel.mapOrganizationToHelper(organization);
    }
}
