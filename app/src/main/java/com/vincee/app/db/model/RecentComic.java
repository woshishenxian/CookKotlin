package com.vincee.app.db.model;

import com.vincee.app.utils.StringConverter;import com.vincee.app.utils.StringConverter;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.util.List;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by DE10035 on 2017/12/20.
 */
@Entity
public class RecentComic {
    @Id
    private Long id;
    private String title;
    private String authorName;
    private String picUrl;
    private Integer titleId = 0;
    @Convert(columnType = String.class,converter = StringConverter.class)
    private List<Integer> episodeIds;
    private String episodeTitle;
    private Integer episodeId = 0;
    @Generated(hash = 1760468330)
    public RecentComic(Long id, String title, String authorName, String picUrl,
            Integer titleId, List<Integer> episodeIds, String episodeTitle,
            Integer episodeId) {
        this.id = id;
        this.title = title;
        this.authorName = authorName;
        this.picUrl = picUrl;
        this.titleId = titleId;
        this.episodeIds = episodeIds;
        this.episodeTitle = episodeTitle;
        this.episodeId = episodeId;
    }
    @Generated(hash = 1095805166)
    public RecentComic() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getTitle() {
        return this.title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getAuthorName() {
        return this.authorName;
    }
    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }
    public String getPicUrl() {
        return this.picUrl;
    }
    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }
    public Integer getTitleId() {
        return this.titleId;
    }
    public void setTitleId(Integer titleId) {
        this.titleId = titleId;
    }
    public List<Integer> getEpisodeIds() {
        return this.episodeIds;
    }
    public void setEpisodeIds(List<Integer> episodeIds) {
        this.episodeIds = episodeIds;
    }
    public String getEpisodeTitle() {
        return this.episodeTitle;
    }
    public void setEpisodeTitle(String episodeTitle) {
        this.episodeTitle = episodeTitle;
    }
    public Integer getEpisodeId() {
        return this.episodeId;
    }
    public void setEpisodeId(Integer episodeId) {
        this.episodeId = episodeId;
    }


}
