package tn.esprit.se.pispring.entities;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.*;

import java.util.Date;

@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Consultant {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long consultant_id;
    private String consultant_firstname;
    private String consultant_lastname;
    private String consultant_address;
    private String consultant_email;
    private Long consultant_phonenumber;
    @Temporal(TemporalType.DATE)
    private Date date_birth;
    @Temporal(TemporalType.DATE)
    private Date hireDate;
    @Temporal(TemporalType.DATE)
    private Date date_last_meet ;
    private String date_last_meeet ;
    private Long clientnumber;
    private String image;
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    private Skill skill;
    @OneToOne
    private Portfolio portfolio;
}
