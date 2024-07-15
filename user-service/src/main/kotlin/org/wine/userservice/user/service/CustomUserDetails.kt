package org.wine.userservice.user.service

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.wine.userservice.user.entity.Member

class CustomUserDetails(byUsername: Member) : UserDetails {
    private val username: String = byUsername.getEmail()
    private val memberPassword: String = byUsername.getPassword()
    private val grantedAuthorities: Collection<GrantedAuthority>

    init {
        val auths: MutableList<GrantedAuthority> = ArrayList()
        for (role in byUsername.getRoles()) {
            auths.add(SimpleGrantedAuthority(role.name?.toUpperCase() ?: ""))
        }
        this.grantedAuthorities = auths
    }

    override fun getAuthorities(): Collection<GrantedAuthority> {
        return grantedAuthorities
    }

    override fun getPassword(): String {
        return memberPassword
    }

    override fun getUsername(): String {
        return username
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }
}