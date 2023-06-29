package engine

import engine.entities.UserEntity
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class UserDetailsImpl(user: UserEntity) : UserDetails {


    private var username: String? = null
    private var password: String? = null
    private var rolesAndAuthorities: MutableCollection<GrantedAuthority>? = null

    private var user: UserEntity? = null

    init {
        username = user.email
        password = user.password
        rolesAndAuthorities = ArrayList()
        this.user = user
        rolesAndAuthorities = null
    }

    override fun getAuthorities() = rolesAndAuthorities
    override fun getPassword() = password
    override fun getUsername() = username
    override fun isAccountNonExpired() = true
    override fun isAccountNonLocked() = true
    override fun isCredentialsNonExpired() = true
    override fun isEnabled() = true

}