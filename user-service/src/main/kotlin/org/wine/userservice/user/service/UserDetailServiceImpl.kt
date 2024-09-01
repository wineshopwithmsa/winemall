package org.wine.userservice.user.service

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import org.wine.userservice.user.entity.Member
import org.wine.userservice.user.repository.MemberRepository

@Service
class UserDetailsServiceImpl : UserDetailsService {
    @Autowired
    private val memberRepository: MemberRepository? = null

//    @Throws(UsernameNotFoundException::class)
//    override fun loadUserByUsername(username: String): UserDetails {
//        logger.debug("Entering in loadUserByUsername Method...")
////        val member: Member? = memberRepository?.findByEmail(username)
//        val member: Member? = memberRepository?.findByEmail(username)
//        if (member == null) {
//            logger.error("Username not found: $username")
//            throw UsernameNotFoundException("could not found user..!!")
//        }
//        logger.info("User Authenticated Successfully..!!!")
//        return CustomUserDetails(member)
//    }
        @Throws(UsernameNotFoundException::class)
        override fun loadUserByUsername(userKey: String): CustomUserDetails {
            logger.debug("Entering in loadUserByUsername Method...")
        //        val member: Member? = memberRepository?.findByEmail(username)
            val member: Member? = memberRepository?.findById(userKey.toLong())?.get()
            if (member == null) {
                logger.error("Username not found: $userKey")
                throw UsernameNotFoundException("Username not found")
            }
            logger.info("User Authenticated Successfully..!!!")
            return CustomUserDetails(member)
        }

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(UserDetailsServiceImpl::class.java)
    }
}
