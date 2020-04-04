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
public class Organization {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long organizationId;
    private String organizationName;
    private String organizationCode;
    @OneToOne
    @JoinColumn
    private OrganizationType organizationType;
    private String physicalAddress;
    private String contactEmail;
    private String contactPhoneNumber;
    private boolean retention;
    private BigDecimal percentageRetention;

}
