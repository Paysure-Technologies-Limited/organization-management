package com.bizzdesk.group.organization.management.repositories;

import com.bizzdesk.group.organization.management.entities.Organization;
import com.bizzdesk.group.organization.management.entities.SubOrganization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubOrganizationRepository extends JpaRepository<SubOrganization, Long> {

    List<SubOrganization> findByOrganization(Organization organization);
    //List<SubOrganization> findByRe(Organization organization);

}
