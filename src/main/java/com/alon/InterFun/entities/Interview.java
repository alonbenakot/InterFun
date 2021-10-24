package com.alon.InterFun.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table
public class Interview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private boolean reminder;

    private boolean success;

    @Column(nullable = false)
    private String address;

    private String interviewerName;

    @Column(nullable = false)
    private LocalDateTime interviewTime;

    private String otherComments;

    @OneToOne(mappedBy = "interview", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private JobApplication jobApplication;

}
