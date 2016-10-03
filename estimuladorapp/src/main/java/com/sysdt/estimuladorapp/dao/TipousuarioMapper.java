package com.sysdt.estimuladorapp.dao;

import com.sysdt.estimuladorapp.model.Tipousuario;
import com.sysdt.estimuladorapp.model.TipousuarioExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TipousuarioMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tipousuario
     *
     * @mbggenerated Sat Aug 27 14:10:49 CDT 2016
     */
    int countByExample(TipousuarioExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tipousuario
     *
     * @mbggenerated Sat Aug 27 14:10:49 CDT 2016
     */
    int deleteByExample(TipousuarioExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tipousuario
     *
     * @mbggenerated Sat Aug 27 14:10:49 CDT 2016
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tipousuario
     *
     * @mbggenerated Sat Aug 27 14:10:49 CDT 2016
     */
    int insert(Tipousuario record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tipousuario
     *
     * @mbggenerated Sat Aug 27 14:10:49 CDT 2016
     */
    int insertSelective(Tipousuario record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tipousuario
     *
     * @mbggenerated Sat Aug 27 14:10:49 CDT 2016
     */
    List<Tipousuario> selectByExample(TipousuarioExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tipousuario
     *
     * @mbggenerated Sat Aug 27 14:10:49 CDT 2016
     */
    Tipousuario selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tipousuario
     *
     * @mbggenerated Sat Aug 27 14:10:49 CDT 2016
     */
    int updateByExampleSelective(@Param("record") Tipousuario record, @Param("example") TipousuarioExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tipousuario
     *
     * @mbggenerated Sat Aug 27 14:10:49 CDT 2016
     */
    int updateByExample(@Param("record") Tipousuario record, @Param("example") TipousuarioExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tipousuario
     *
     * @mbggenerated Sat Aug 27 14:10:49 CDT 2016
     */
    int updateByPrimaryKeySelective(Tipousuario record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tipousuario
     *
     * @mbggenerated Sat Aug 27 14:10:49 CDT 2016
     */
    int updateByPrimaryKey(Tipousuario record);
}