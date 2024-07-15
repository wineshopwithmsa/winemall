package org.wine.userservice.user.entity

import jakarta.persistence.*
import lombok.*
import org.springframework.data.jpa.domain.support.AuditingEntityListener

@Entity
@Table(name = "member_roles")
@Getter
@EntityListeners(AuditingEntityListener::class)
class MemberRole(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long = 0,

    @Column(name = "name")
    var name: String? = null
) {
    constructor() : this(0, null)
}