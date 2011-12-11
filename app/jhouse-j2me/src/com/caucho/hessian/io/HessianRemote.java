// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) lnc 
// Source File Name:   HessianRemote.java

package com.caucho.hessian.io;


public class HessianRemote
{

            public HessianRemote(String type, String url)
            {
/*  67*/        this.type = type;
/*  68*/        this.url = url;
            }

            public HessianRemote()
            {
            }

            public String getType()
            {
/*  83*/        return type;
            }

            public String getURL()
            {
/*  91*/        return url;
            }

            public void setURL(String url)
            {
/*  99*/        this.url = url;
            }

            public int hashCode()
            {
/* 107*/        return url.hashCode();
            }

            public boolean equals(Object obj)
            {
/* 115*/        if(!(obj instanceof HessianRemote))
                {
/* 116*/            return false;
                } else
                {
/* 118*/            HessianRemote remote = (HessianRemote)obj;
/* 120*/            return url.equals(remote.url);
                }
            }

            public String toString()
            {
/* 128*/        return "[HessianRemote " + url + "]";
            }

            private String type;
            private String url;
}
