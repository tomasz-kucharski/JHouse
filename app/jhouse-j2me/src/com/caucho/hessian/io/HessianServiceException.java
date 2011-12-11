// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) lnc 
// Source File Name:   HessianServiceException.java

package com.caucho.hessian.io;


public class HessianServiceException extends Exception
{

            public HessianServiceException()
            {
            }

            public HessianServiceException(String message, String code, Object detail)
            {
/*  71*/        super(message);
/*  72*/        this.code = code;
/*  73*/        this.detail = detail;
            }

            public String getCode()
            {
/*  81*/        return code;
            }

            public Object getDetail()
            {
/*  89*/        return detail;
            }

            private String code;
            private Object detail;
}
