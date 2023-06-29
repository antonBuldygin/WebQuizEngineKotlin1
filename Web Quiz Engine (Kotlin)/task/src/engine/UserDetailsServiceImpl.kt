package engine

import engine.entities.UserEntity
import engine.repositories.UserEntityRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UserDetailsServiceImpl(
    val userRepo: UserEntityRepository
) : UserDetailsService {

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(username: String): UserDetails {
        val user: UserEntity = userRepo.findByEmail(username)
            ?: throw UsernameNotFoundException("Not found: $username")
        return UserDetailsImpl(user)
    }
}