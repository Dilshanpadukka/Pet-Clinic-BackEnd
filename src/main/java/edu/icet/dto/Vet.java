package edu.icet.dto;

import edu.icet.entity.VetEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Vet {
    private Integer vetId;
    private String vetName;
    private String regNo;
    private String email;
    private String contactNumber;
    private byte[] profilePictureData;
    private String profilePictureType;

    public Vet(VetEntity vetEntity) {
        this.vetId = vetEntity.getVetId();
        this.vetName = vetEntity.getVetName();
        this.regNo = vetEntity.getRegNo();
        this.email = vetEntity.getEmail();
        this.contactNumber = vetEntity.getContactNumber();
        this.profilePictureData = vetEntity.getProfilePictureData();
        this.profilePictureType = vetEntity.getProfilePictureType();
    }
}
