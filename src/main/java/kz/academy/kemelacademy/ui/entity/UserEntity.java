package kz.academy.kemelacademy.ui.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * @author Omarbek.Dinassil
 * on 2020-07-20
 * @project kemelacademy
 */
@Setter
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class UserEntity implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, length = 120)
    private String email;
    
    @Column(nullable = false)
    private Boolean emailVerificationStatus = false;
    
    private String emailVerificationToken;
    
    @Column(nullable = false)
    private String encryptedPassword;
    
    @Column(nullable = false)
    private String fullName;
    
    @Column(nullable = false)
    private String userId;
    
    @Column(name = "agreed_with_agreement")
    private Boolean agreedWithAgreement;
    
    private String videoCallUrl;
    
    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name = "user_roles",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")}
    )
    private Set<RoleEntity> roles = new HashSet<>();
    
    @OneToMany(
            mappedBy = "author",
            cascade = CascadeType.ALL
    )
    private Set<CourseEntity> courses = new HashSet<>();
    
    @OneToMany(mappedBy = "user")
    private Set<UserHomeWorkEntity> userHomeWorks = new HashSet<>();
    
    @OneToMany(mappedBy = "user")
    private Set<UserCourseEntity> pupils = new HashSet<>();
    
    @Override
    public String toString() {
        return fullName;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }
        UserEntity that = (UserEntity) o;
        return id.equals(that.id) &&
                Objects.equals(email, that.email) &&
                Objects.equals(emailVerificationStatus, that.emailVerificationStatus) &&
                Objects.equals(emailVerificationToken, that.emailVerificationToken) &&
                Objects.equals(encryptedPassword, that.encryptedPassword) &&
                Objects.equals(fullName, that.fullName) &&
                Objects.equals(userId, that.userId) &&
                Objects.equals(roles, that.roles) &&
                Objects.equals(courses, that.courses) &&
                Objects.equals(userHomeWorks, that.userHomeWorks) &&
                Objects.equals(pupils, that.pupils) &&
                Objects.equals(videoCallUrl, that.videoCallUrl);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id, email, emailVerificationStatus, emailVerificationToken, encryptedPassword, fullName,
                userId, videoCallUrl);
    }
    
}
