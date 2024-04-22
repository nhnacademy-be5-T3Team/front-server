package com.t3t.frontserver.auth.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.*;

/**
 * Access Token을 Cookie에서 꺼내 HttpServletRequest에 header로 넣기 위한 wrapper 클래스
 * @author joohyun1996(이주현)
 */
public class RequestWrapper extends HttpServletRequestWrapper {
    private final Map<String, String> header;
    public RequestWrapper(HttpServletRequest request) {
        super(request);
        this.header = new HashMap<>();
    }

    /**
     * 헤더에 값을 넣는 메소드
     * @param key,value
     * @author joohyun1996(이주현)
     */
    public void addHeader(String key, String value){
        this.header.put(key,value);
    }

    @Override
    public String getHeader(String name) {
        String headerValue = this.header.get(name);
        if(Objects.nonNull(name)){
            return headerValue;
        }
        return ((HttpServletRequest) getRequest()).getHeader(name);
    }

    @Override
    public Enumeration<String> getHeaderNames() {
        Set<String> headerNameSet = new HashSet<>(header.keySet());
        Enumeration<String> headerNames = ((HttpServletRequest) getRequest()).getHeaderNames();

        while(headerNames.hasMoreElements()){
            headerNameSet.add(headerNames.nextElement());
        }
        return Collections.enumeration(headerNameSet);
    }
}
