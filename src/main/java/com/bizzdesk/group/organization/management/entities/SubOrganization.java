package com.bizzdesk.group.organization.management.entities;

import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Accessors(chain = true)
public class SubOrganization {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long subOrganizationId;
    @OneToOne
    @JoinColumn
    private Organization organization;
    private String subOrganizationName;
    private String subOrganizationCode;
    private String physicalAddress;
    private String contactEmail;
    private String contactPhoneNumber;
}
