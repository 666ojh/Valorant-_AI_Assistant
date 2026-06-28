package com.valorantassistant.match.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.valorantassistant.match.domain.MatchRecord;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MatchRecordMapper extends BaseMapper<MatchRecord> {
}
