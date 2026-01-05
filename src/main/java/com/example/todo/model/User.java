package com.example.todo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "user")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private Long id;

    @Column
    private String userName;

    @Column(unique = true)
    private String emailAddress;

    @Column
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @OneToOne(
            cascade = CascadeType.ALL, fetch = FetchType.LAZY
    )
    @JoinColumn(name = "profile_id",referencedColumnName = "id")
    private UserProfile userProfile;

    @JsonIgnore
    public String getPassword(){
        return password;
    }

    @OneToMany(mappedBy = "user",fetch = FetchType.LAZY)
    private List<Item> itemList;

    @OneToMany(mappedBy = "user",fetch = FetchType.LAZY)
    private List<Category> categoryList;

}
