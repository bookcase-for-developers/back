package kong.user.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Table(
        name = "users",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_user_email_loginType",
                        columnNames = {"email", "login_type"}
                )
        }
)
@Entity
@Getter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar(50)")
    private LoginType loginType;
}
