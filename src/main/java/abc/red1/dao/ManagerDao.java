package abc.red1.dao;

import abc.red1.entity.Manager;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ManagerDao extends BaseMapper<Manager> {

    @Select("select * from manager  order by manager_id desc  limit 1")
    Manager getLastOne();
}
