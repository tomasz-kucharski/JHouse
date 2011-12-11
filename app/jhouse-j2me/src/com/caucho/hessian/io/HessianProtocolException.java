// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) lnc 
// Source File Name:   HessianProtocolException.java

package com.caucho.hessian.io;

import java.io.IOException;

public class HessianProtocolException extends IOException
{

            public HessianProtocolException()
            {
            }

            public HessianProtocolException(String message)
            {
/*  72*/        super(message);
            }

            public HessianProtocolException(String message, Throwable rootCause)
            {
/*  80*/        super(message);
/*  82*/        this.rootCause = rootCause;
            }

            public HessianProtocolException(Throwable rootCause)
            {
/*  90*/        super(String.valueOf(rootCause));
/*  92*/        this.rootCause = rootCause;
            }

            public Throwable getRootCause()
            {
/* 100*/        return rootCause;
            }

            public Throwable getCause()
            {
/* 108*/        return getRootCause();
            }

            private Throwable rootCause;
}
