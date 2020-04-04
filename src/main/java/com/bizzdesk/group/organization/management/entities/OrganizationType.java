package com.bizzdesk.group.organization.management.entities;

import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Accessors(chain = true)
@Entity
public class OrganizationType {

    @Id
    private String organizationType;
    private String organizationDescription;
}
