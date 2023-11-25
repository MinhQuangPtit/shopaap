package com.example.shopapp.models;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import javax.lang.model.element.Name;

@Data
@Setter
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table( name = "social_accounts")
@Builder
public class SocialAccount {
    @Id
    private int id;

    @NotBlank
    private String provider;

    @Column(name = "provider_id", length = 50, nullable = false)
    private String providerId;

    @NotBlank
    private String email;

    @NotBlank
    private String name;

}
