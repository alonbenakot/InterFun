package com.alon.InterFun.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String firstName;
    private String lastName;
    @Column(unique=true)
    private String email;
    private String password;
    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private List<JobApplication> jobApplications;

    public void addJobApplication(JobApplication jobApplication) {
        if (jobApplications == null) {
            jobApplications = new ArrayList<>();
        }
        jobApplication.setUser(this);
        jobApplications.add(jobApplication);
    }

}
