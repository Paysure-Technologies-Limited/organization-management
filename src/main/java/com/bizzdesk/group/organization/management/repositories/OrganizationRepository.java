package com.bizzdesk.group.organization.management.repositories;

import com.bizzdesk.group.organization.management.entities.Organization;
import com.bizzdesk.group.organization.management.entities.OrganizationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization, Long> {

    List<Organization> findByRetention(boolean retention);
    List<Organization> findByOrganizationType(OrganizationType organizationType);
    List<Organization> findByOrganizationCode(String organizationCode);
}
