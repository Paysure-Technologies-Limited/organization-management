package com.bizzdesk.group.organization.management.mapper;

import com.bizzdesk.group.organization.management.entities.Organization;
import com.bizzdesk.group.organization.management.entities.OrganizationType;
import com.bizzdesk.group.organization.management.entities.SubOrganization;
import com.gotax.framework.library.entity.helpers.OrganizationHelperResponse;
import com.gotax.framework.library.entity.helpers.OrganizationTypeHelper;
import com.gotax.framework.library.entity.helpers.SubOrganizationHelperResponse;

public class MapperModel {

    public static OrganizationHelperResponse mapOrganizationToHelper(Organization organization) {
        return new OrganizationHelperResponse().setContactEmail(organization.getContactEmail())
                .setContactPhoneNumber(organization.getContactPhoneNumber())
                .setOrganizationCode(organization.getOrganizationCode())
                .setOrganizationName(organization.getOrganizationName())
                .setPercentageRetention(organization.getPercentageRetention())
                .setPhysicalAddress(organization.getPhysicalAddress())
                .setOrganizationId(organization.getOrganizationId())
                .setRetention(organization.isRetention());
    }

    public static OrganizationTypeHelper mapOrganizationTypeToHelper(OrganizationType organizationType) {
        return new OrganizationTypeHelper().setOrganizationTypeId(organizationType.getOrganizationType())
                .setOrganizationTypeDescription(organizationType.getOrganizationDescription());
    }

    public static SubOrganizationHelperResponse mapSubOrganizationToResponse(SubOrganization subOrganization) {
        return new SubOrganizationHelperResponse().setContactEmail(subOrganization.getContactEmail())
                .setContactPhoneNumber(subOrganization.getContactPhoneNumber())
                .setOrganizationHelperResponse(mapOrganizationToHelper(subOrganization.getOrganization()))
                .setPhysicalAddress(subOrganization.getPhysicalAddress())
                .setSubOrganizationId(subOrganization.getSubOrganizationId())
                .setSubOrganizationCode(subOrganization.getSubOrganizationCode())
                .setSubOrganizationName(subOrganization.getSubOrganizationName());
    }
}
