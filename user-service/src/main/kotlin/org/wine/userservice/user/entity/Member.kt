package org.wine.userservice.user.entity

import jakarta.persistence.*
import lombok.Builder
import lombok.Getter
import lombok.NoArgsConstructor
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import org.wine.userservice.address.entity.Address
import org.wine.userservice.membercoupon.entity.MemberCoupon

@Entity
@Table(name = "member")
@Getter
@Builder
@NoArgsConstructor
@EntityListeners(AuditingEntityListener::class)
class Member(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private val userId: Long = 0L,

    @Column(name = "email", nullable = false)
    private val email: String = "",

    @Column(name = "password", nullable = false)
    private val password: String = "",

    @Column(name = "nickname")
    private val nickName: String? = null,

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "members_roles_connector",
        joinColumns = [JoinColumn(name = "member_id", referencedColumnName = "user_id")],
        inverseJoinColumns = [JoinColumn(name = "role_id", referencedColumnName = "id")]
    )
    private val roles: Set<MemberRole> = HashSet(),

    @OneToMany(mappedBy = "member", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    val memberCoupons: List<MemberCoupon> = mutableListOf(),

    @OneToMany(mappedBy = "member", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    val address: List<Address> = mutableListOf()
) {

    fun getUserId(): Long = userId
    fun getEmail(): String = email
    fun getPassword(): String = password
    fun getNickName(): String? = nickName
    fun getRoles(): Set<MemberRole> = roles
}