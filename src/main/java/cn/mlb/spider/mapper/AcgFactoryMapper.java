package cn.mlb.spider.mapper;

import cn.mlb.spider.entity.AcgFactory;
import cn.mlb.spider.entity.AcgItem;

import java.util.List;

public interface AcgFactoryMapper {

	void save(AcgFactory acgFactory);

    AcgFactory getFirstIncompleteFactory();

    void update(AcgFactory factory);

    List<AcgFactory> queryAcgFactory();
}
