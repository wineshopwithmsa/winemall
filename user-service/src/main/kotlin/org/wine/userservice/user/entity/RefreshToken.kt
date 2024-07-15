package org.wine.userservice.user.entity

import jakarta.persistence.*
import lombok.AllArgsConstructor
import lombok.Builder
import lombok.Getter
import lombok.NoArgsConstructor
import java.time.Instant

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "refresh_token")
class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id = 0
    private val token: String? = null

    private val expiryDate: Instant? = null //    @OneToOne
    //    @JoinColumn(name = "id", referencedColumnName = "user_id")
    //    private User user;
}