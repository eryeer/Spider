package cn.mlb.spider.mapper;

import cn.mlb.spider.entity.AcgItem;
import cn.mlb.spider.entity.Media;

import java.util.List;

public interface AcgItemMapper {

	void save(AcgItem acgItem);

	List<AcgItem> queryAllOrderByRatingDesc();

}
