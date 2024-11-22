package edu.icet.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "vet")
public class VetEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer vetId;
    private String vetName;
    private String regNo;
    private String email;
    private String contactNumber;
    @Lob
    @Column(name = "profile_picture_data", columnDefinition = "LONGBLOB")
    private byte[] profilePictureData;

    private String profilePictureType;
}
