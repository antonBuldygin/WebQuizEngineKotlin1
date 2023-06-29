package engine

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain


@EnableWebSecurity
@Configuration
class SecurityConfiguration {
    @Autowired
    var userDetailsService: UserDetailsServiceImpl? = null



    @Throws(java.lang.Exception::class)
    protected fun configure(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService<UserDetailsService?>(userDetailsService).passwordEncoder(getEncoder())
    }

    @Bean
    @Throws(Exception::class)
    fun filterChain(http: HttpSecurity): SecurityFilterChain? {
        http.httpBasic()
            .and()
            .csrf().disable().headers().frameOptions().disable()
            .and()
            .authorizeRequests()
            .requestMatchers(HttpMethod.GET, "/api/quizzes").authenticated()
            .requestMatchers(HttpMethod.GET, "/api/quizzes/{id}").authenticated()
            .requestMatchers(HttpMethod.GET, "/api/quizzes/completed").authenticated()
            .requestMatchers(HttpMethod.POST, "/api/quizzes").authenticated()
            .requestMatchers(HttpMethod.POST, "/api/quizzes/{id}/solve").authenticated()
            .requestMatchers(HttpMethod.DELETE, "/api/quizzes/{id}").authenticated()
            .requestMatchers(HttpMethod.POST, "/api/register").permitAll()
            .requestMatchers(HttpMethod.POST, "/actuator/shutdown").permitAll()
            .and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .exceptionHandling()

        return http.build()
    }

    @Bean
    fun getEncoder(): PasswordEncoder? {
        return BCryptPasswordEncoder()
    }
}