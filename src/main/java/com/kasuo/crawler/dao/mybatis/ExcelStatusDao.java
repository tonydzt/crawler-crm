package com.kasuo.crawler.dao.mybatis;

import com.kasuo.crawler.domain.ExcelStatus;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author douzhitong
 * @date 2018/12/20
 */
@Repository
public interface ExcelStatusDao {

    ExcelStatus find(Long id);

    ExcelStatus findFile(@Param("path") String path, @Param("fileName") String fileName);

    List<ExcelStatus> findByPath(@Param("path") String path);

    void insert(ExcelStatus excelStatus);

    int update(ExcelStatus excelStatus);
}
