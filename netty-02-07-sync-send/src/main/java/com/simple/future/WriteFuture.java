package com.simple.future;

import com.simple.msg.Response;

import java.util.concurrent.Future;

/**
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2021-12-18 11:10
 **/
public interface WriteFuture<T> extends Future<T> {

    Throwable cause();

    void setCause(Throwable cause);

    boolean isWriteSuccess();

    void setWriteResult(boolean result);

    String requestId();

    T response();

    void setResponse(Response response);

    boolean isTimeout();
}
