package abc.red1.dao;

import abc.red1.entity.Notice;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface NoticeDao extends BaseMapper<Notice> {





    @Select("select * from notice order by notice_id DESC  limit 1")
    Notice getLastone();

    @Select("select * from notice order by notice_id DESC")
    List<Notice> getAllDesc();

    @Select("select * from notice order by notice_id DESC limit 3")
    List<Notice> getAllDescLimit3();


    @Select("delete from notice where notice_id=#{id} ")
    void deleteById(Long id);
}
