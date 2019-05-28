package com.astefanski.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    @NotNull(message = "{spring.messages.emptyDisplayName}")
    private String displayName;

    @Builder.Default
    @OneToMany(mappedBy = "role")
    private List<User> users = new ArrayList<>();

    @Override
    public String toString() {
        return "\nName: " + name;
    }

}
