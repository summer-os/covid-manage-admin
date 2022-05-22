package com.summer.manage.dto.request;


import com.summer.common.core.BaseDTO;
import com.summer.common.core.IRequest;

/**
 * @Description：
 * @Author：sacher
 * @Create：2021/1/9 9:49 下午
 **/
public class ConfigListRequest extends BaseDTO implements IRequest {
    /**
     * 当前页
     **/
    public Integer pager = 1;

    /**
     * 每页条数
     **/
    public Integer size = 20;

    /**
     * 开始时间
     */
    public String beginTime;

    /**
     * 结束时间
     */
    public String endTime;

    /**
     * 参数名称
     */
    public String configName;

    /**
     * 参数键名
     */
    public String configKey;

    /**
     * 系统内置（Y是 N否）
     */
    public String configType;


    @Override
    public void verify() {

    }
}
