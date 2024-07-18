package org.wine.productservice.wine.entity


import jakarta.persistence.*
import lombok.AllArgsConstructor
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.Instant

@Entity
@Table(name = "category")
@AllArgsConstructor
class Category(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id", nullable = false)
    val categoryId: Long = 0L,

    @Column(name = "name", nullable = false)
    var name: String,

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    var createdAt: Instant = Instant.now(),

    @LastModifiedDate
    @Column(name = "updated_at", nullable = true)
    var updatedAt: Instant? = null,
) {
    fun name(name: String) = apply { this.name = name }
    fun createdAt(createdAt: Instant) = apply { /* createdAt은 val이므로 변경 불가 */ }
}