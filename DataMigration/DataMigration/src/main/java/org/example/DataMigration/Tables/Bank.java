package org.example.DataMigration.Tables;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "mst_bank")
public class Bank {

    @Id
    @Column(name = "fbankid", nullable = false)
    private String bankId;

    @Column(name = "fcompanyid")
    private String companyId;

    @Column(name = "fname")
    private String name;

    @Column(name = "factive_flag")
    private Boolean activeFlag;

    @Column(name = "fmemo")
    private String memo;

    @Column(name = "fcreated_by")
    private String createdBy;

    @Column(name = "fcreated_date")
    private String createdDate;

    @Column(name = "fupdated_by")
    private String updatedBy;

    @Column(name = "fupdated_date")
    private String updatedDate;


}
