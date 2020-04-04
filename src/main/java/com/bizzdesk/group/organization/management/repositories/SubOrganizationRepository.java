package com.bizzdesk.group.organization.management.repositories;

import com.bizzdesk.group.organization.management.entities.Organization;
import com.bizzdesk.group.organization.management.entities.SubOrganization;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubOrganizationRepository extends JpaRepository<SubOrganization, Long> {

    List<SubOrganization> findByOrganization(Organization organization);
    List<SubOrganization> findByRe(Organization organization);

}
