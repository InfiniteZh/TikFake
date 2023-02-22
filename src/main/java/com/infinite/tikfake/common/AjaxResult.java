package com.infinite.tikfake.common;

import java.util.HashMap;

public class AjaxResult extends HashMap<String, Object> {

    /** 状态码
     * 状态码，0-成功，其他值-失败
     * */
    public static final String CODE_TAG = "status_code";

    /** 返回状态描述 */
    public static final String MSG_TAG = "status_msg";

    /** 本次返回的视频中，发布最早的时间，作为下次请求时的latest_time
     * 视频流接口
     * 发布列表
     * */
    public static final String NEXT_TIME = "next_time";

    /**
     * 初始化一个新创建的 AjaxResult 对象，使其表示一个空消息。
     */
    public AjaxResult()
    {
    }

    /**
     * 初始化一个新创建的 AjaxResult 对象
     *
     * @param code 状态码
     * @param msg 返回内容
     */
    public AjaxResult(int code, String msg)
    {
        super.put(CODE_TAG, code);
        super.put(MSG_TAG, msg);
    }

    /**
     * 初始化一个新创建的 AjaxResult 对象
     *
     * @param code 状态码
     * @param msg 返回内容
     * @param next_time 发布最早的时间
     */
    public AjaxResult(int code, String msg, int next_time)
    {
        super.put(CODE_TAG, code);
        super.put(MSG_TAG, msg);
        super.put(NEXT_TIME, next_time);
    }

    /**
     * 返回成功消息
     *
     * @return 成功消息
     */
    public static AjaxResult success()
    {
        return new AjaxResult(HttpStatus.SUCCESS, null);
    }

    /**
     *
     * @param msg
     * @return
     */
    public static AjaxResult success(String msg)
    {
        return new AjaxResult(HttpStatus.SUCCESS, msg);
    }


    /**
     * 返回错误消息
     * @param msg 错误简述
     * @return 错误消息
     */
    public static AjaxResult error(String msg)
    {
        return new AjaxResult(HttpStatus.ERROR, msg);
    }

    /**
     * 方便链式调用
     *
     * @param key 键
     * @param value 值
     * @return 数据对象
     */
    @Override
    public AjaxResult put(String key, Object value)
    {
        super.put(key, value);
        return this;
    }
}