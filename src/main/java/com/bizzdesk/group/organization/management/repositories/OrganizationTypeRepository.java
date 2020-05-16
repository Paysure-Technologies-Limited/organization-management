package com.bizzdesk.group.organization.management.repositories;

import com.bizzdesk.group.organization.management.entities.OrganizationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganizationTypeRepository extends JpaRepository<OrganizationType, String> {
}
