package engine.mappers

import engine.User
import engine.entities.UserEntity
import org.mapstruct.*

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
abstract class UserEntityMapper {

    @Mapping(source = "", target = "id")
    abstract fun toEntity(user: User): UserEntity

    @Mapping(source = "id", target = "")
    abstract fun toDto(userEntity: UserEntity): User

    @Mapping(source = "", target = "id")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    abstract fun partialUpdate(user: User, @MappingTarget userEntity: UserEntity): UserEntity
}