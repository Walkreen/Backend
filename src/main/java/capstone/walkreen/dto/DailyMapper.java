package capstone.walkreen.dto;

import capstone.walkreen.entity.Daily;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface DailyMapper {

    DailyMapper INSTANCE = Mappers.getMapper(DailyMapper.class);

    DailyResponse dailyToResponse(Daily daily);
}
