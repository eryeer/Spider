package cn.mlb.spider.mapper;

import java.util.List;

import cn.mlb.spider.entity.Media;

public interface MediaMapper {

	void save(Media media);

	List<Media> queryAllOrderByRatingDesc();

}
