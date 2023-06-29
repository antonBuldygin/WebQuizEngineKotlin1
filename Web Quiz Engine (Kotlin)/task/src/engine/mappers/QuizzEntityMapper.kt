package engine.mappers

import engine.dtos.QuizzDto
import engine.entities.QuizzEntity
import org.mapstruct.InjectionStrategy
import org.springframework.stereotype.Component

@org.mapstruct.Mapper(unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE, componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
@Component("engine.mappers.QuizzEntityMapper")
abstract class QuizzEntityMapper {

    abstract fun toEntity(quizzDto: QuizzDto): QuizzEntity

    abstract fun toDto(quizzEntity: QuizzEntity): QuizzDto

    @org.mapstruct.BeanMapping(nullValuePropertyMappingStrategy = org.mapstruct.NullValuePropertyMappingStrategy.IGNORE)
    abstract fun partialUpdate(quizzDto: QuizzDto, @org.mapstruct.MappingTarget quizzEntity: QuizzEntity): QuizzEntity
}