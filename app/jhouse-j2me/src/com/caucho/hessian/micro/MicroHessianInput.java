// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) lnc
// Source File Name:   MicroHessianInput.java

package com.caucho.hessian.micro;

import java.io.*;
import java.util.Date;

public class MicroHessianInput
{

            public MicroHessianInput(InputStream is)
            {
/*  87*/        init(is);
            }

            public MicroHessianInput()
            {
            }

            public void init(InputStream is)
            {
/* 102*/        this.is = is;
            }

            public void startReply()
                throws IOException
            {
/* 117*/        int tag = is.read();
/* 119*/        if(tag != 114)
/* 120*/            protocolException("expected hessian reply");
/* 122*/        int major = is.read();
/* 123*/        int minor = is.read();
            }

            public void completeReply()
                throws IOException
            {
/* 138*/        int tag = is.read();
/* 140*/        if(tag != 122)
/* 141*/            protocolException("expected end of reply");
            }

            public boolean readBoolean()
                throws IOException
            {
/* 155*/        int tag = is.read();
/* 157*/        switch(tag)
                {
/* 158*/        case 84: // 'T'
/* 158*/            return true;

/* 159*/        case 70: // 'F'
/* 159*/            return false;
                }
/* 161*/        throw expect("boolean", tag);
            }

            public int readInt()
                throws IOException
            {
/* 175*/        int tag = is.read();
/* 177*/        if(tag != 73)
                {
/* 178*/            throw expect("integer", tag);
                } else
                {
/* 180*/            int b32 = is.read();
/* 181*/            int b24 = is.read();
/* 182*/            int b16 = is.read();
/* 183*/            int b8 = is.read();
/* 185*/            return (b32 << 24) + (b24 << 16) + (b16 << 8) + b8;
                }
            }

            public long readLong()
                throws IOException
            {
/* 198*/        int tag = is.read();
/* 200*/        if(tag != 76)
                {
/* 201*/            throw protocolException("expected long");
                } else
                {
/* 203*/            long b64 = is.read();
/* 204*/            long b56 = is.read();
/* 205*/            long b48 = is.read();
/* 206*/            long b40 = is.read();
/* 207*/            long b32 = is.read();
/* 208*/            long b24 = is.read();
/* 209*/            long b16 = is.read();
/* 210*/            long b8 = is.read();
/* 212*/            return (b64 << 56) + (b56 << 48) + (b48 << 40) + (b40 << 32) + (b32 << 24) + (b24 << 16) + (b16 << 8) + b8;
                }
            }

            public long readUTCDate()
                throws IOException
            {
/* 232*/        int tag = is.read();
/* 234*/        if(tag != 100)
                {
/* 235*/            throw protocolException("expected date");
                } else
                {
/* 237*/            long b64 = is.read();
/* 238*/            long b56 = is.read();
/* 239*/            long b48 = is.read();
/* 240*/            long b40 = is.read();
/* 241*/            long b32 = is.read();
/* 242*/            long b24 = is.read();
/* 243*/            long b16 = is.read();
/* 244*/            long b8 = is.read();
/* 246*/            return (b64 << 56) + (b56 << 48) + (b48 << 40) + (b40 << 32) + (b32 << 24) + (b24 << 16) + (b16 << 8) + b8;
                }
            }

            public String readString()
                throws IOException
            {
/* 266*/        int tag = is.read();
/* 268*/        if(tag == 78)
/* 269*/            return null;
/* 271*/        if(tag != 83)
                {
/* 272*/            throw expect("string", tag);
                } else
                {
/* 274*/            int b16 = is.read();
/* 275*/            int b8 = is.read();
/* 277*/            int len = (b16 << 8) + b8;
/* 279*/            return readStringImpl(len);
                }
            }

            public byte[] readBytes()
                throws IOException
            {
/* 292*/        int tag = is.read();
/* 294*/        if(tag == 78)
/* 295*/            return null;
/* 297*/        if(tag != 66)
/* 298*/            throw expect("bytes", tag);
/* 300*/        int b16 = is.read();
/* 301*/        int b8 = is.read();
/* 303*/        int len = (b16 << 8) + b8;
/* 305*/        ByteArrayOutputStream bos = new ByteArrayOutputStream();
/* 307*/        for(int i = 0; i < len; i++)
/* 308*/            bos.write(is.read());

/* 310*/        return bos.toByteArray();
            }

            public Object readObject(Class expectedClass)
                throws IOException
            {
/* 319*/        int tag = is.read();
/* 321*/        switch(tag)
                {
/* 323*/        case 78: // 'N'
                {
/* 323*/            return null;
                }

/* 326*/        case 84: // 'T'
                {
/* 326*/            return new Boolean(true);
                }

/* 329*/        case 70: // 'F'
                {
/* 329*/            return new Boolean(false);
                }

/* 332*/        case 73: // 'I'
                {
/* 332*/            int b32 = is.read();
/* 333*/            int b24 = is.read();
/* 334*/            int b16 = is.read();
/* 335*/            int b8 = is.read();
/* 337*/            return new Integer((b32 << 24) + (b24 << 16) + (b16 << 8) + b8);
                }

/* 341*/        case 76: // 'L'
                {
/* 341*/            long b64 = is.read();
/* 342*/            long b56 = is.read();
/* 343*/            long b48 = is.read();
/* 344*/            long b40 = is.read();
/* 345*/            long b32 = is.read();
/* 346*/            long b24 = is.read();
/* 347*/            long b16 = is.read();
/* 348*/            long b8 = is.read();
/* 350*/            return new Long((b64 << 56) + (b56 << 48) + (b48 << 40) + (b40 << 32) + (b32 << 24) + (b24 << 16) + (b16 << 8) + b8);
                }

/* 361*/        case 100: // 'd'
                {
/* 361*/            long b64 = is.read();
/* 362*/            long b56 = is.read();
/* 363*/            long b48 = is.read();
/* 364*/            long b40 = is.read();
/* 365*/            long b32 = is.read();
/* 366*/            long b24 = is.read();
/* 367*/            long b16 = is.read();
/* 368*/            long b8 = is.read();
/* 370*/            return new Date((b64 << 56) + (b56 << 48) + (b48 << 40) + (b40 << 32) + (b32 << 24) + (b24 << 16) + (b16 << 8) + b8);
                }

/* 382*/        case 83: // 'S'
/* 382*/        case 88: // 'X'
                {
/* 382*/            int b16 = is.read();
/* 383*/            int b8 = is.read();
/* 385*/            int len = (b16 << 8) + b8;
/* 387*/            return readStringImpl(len);
                }

/* 391*/        case 66: // 'B'
                {
/* 391*/            if(tag != 66)
/* 392*/                throw expect("bytes", tag);
/* 394*/            int b16 = is.read();
/* 395*/            int b8 = is.read();
/* 397*/            int len = (b16 << 8) + b8;
/* 399*/            ByteArrayOutputStream bos = new ByteArrayOutputStream();
/* 401*/            for(int i = 0; i < len; i++)
/* 402*/                bos.write(is.read());

/* 404*/            return bos.toByteArray();
                }
                }
/* 407*/        throw new IOException("unknown code:" + (char)tag);
            }

            protected String readStringImpl(int length)
                throws IOException
            {
/* 417*/        StringBuffer sb = new StringBuffer();
/* 419*/        for(int i = 0; i < length; i++)
                {
/* 420*/            int ch = is.read();
/* 422*/            if(ch < 128)
/* 423*/                sb.append((char)ch);
/* 424*/            else
/* 424*/            if((ch & 0xe0) == 192)
                    {
/* 425*/                int ch1 = is.read();
/* 426*/                int v = ((ch & 0x1f) << 6) + (ch1 & 0x3f);
/* 428*/                sb.append((char)v);
                    } else
/* 430*/            if((ch & 0xf0) == 224)
                    {
/* 431*/                int ch1 = is.read();
/* 432*/                int ch2 = is.read();
/* 433*/                int v = ((ch & 0xf) << 12) + ((ch1 & 0x3f) << 6) + (ch2 & 0x3f);
/* 435*/                sb.append((char)v);
                    } else
                    {
/* 438*/                throw new IOException("bad utf-8 encoding");
                    }
                }

/* 441*/        return sb.toString();
            }

            protected IOException expect(String expect, int ch)
            {
/* 446*/        if(ch < 0)
/* 447*/            return protocolException("expected " + expect + " at end of file");
/* 449*/        else
/* 449*/            return protocolException("expected " + expect + " at " + (char)ch);
            }

            protected IOException protocolException(String message)
            {
/* 454*/        return new IOException(message);
            }

            protected InputStream is;
}
