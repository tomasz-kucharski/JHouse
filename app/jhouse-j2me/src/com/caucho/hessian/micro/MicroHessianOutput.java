package com.caucho.hessian.micro;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

/**
 * @author tomekk
 * @since 2009-11-27 00:15:15
 */
public class MicroHessianOutput
{

	            public MicroHessianOutput(OutputStream os)
	            {
/*  90*/        init(os);
	            }

	            public MicroHessianOutput()
	            {
	            }

	            public void init(OutputStream os)
	            {
/* 102*/        this.os = os;
	            }

	            public void startCall(String method)
	                throws IOException
	            {
/* 118*/        os.write(99);
/* 119*/        os.write(0);
/* 120*/        os.write(1);
/* 122*/        os.write(109);
/* 123*/        int len = method.length();
/* 124*/        os.write(len >> 8);
/* 125*/        os.write(len);
/* 126*/        printString(method, 0, len);
	            }

	            public void completeCall()
	                throws IOException
	            {
/* 139*/        os.write(122);
	            }

	            public void writeBoolean(boolean value)
	                throws IOException
	            {
/* 156*/        if(value)
/* 157*/            os.write(84);
/* 159*/        else
/* 159*/            os.write(70);
	            }

	            public void writeInt(int value)
	                throws IOException
	            {
/* 175*/        os.write(73);
/* 176*/        os.write(value >> 24);
/* 177*/        os.write(value >> 16);
/* 178*/        os.write(value >> 8);
/* 179*/        os.write(value);
	            }

	            public void writeLong(long value)
	                throws IOException
	            {
/* 195*/        os.write(76);
/* 196*/        os.write((byte)(int)(value >> 56));
/* 197*/        os.write((byte)(int)(value >> 48));
/* 198*/        os.write((byte)(int)(value >> 40));
/* 199*/        os.write((byte)(int)(value >> 32));
/* 200*/        os.write((byte)(int)(value >> 24));
/* 201*/        os.write((byte)(int)(value >> 16));
/* 202*/        os.write((byte)(int)(value >> 8));
/* 203*/        os.write((byte)(int)value);
	            }

	            public void writeUTCDate(long time)
	                throws IOException
	            {
/* 218*/        os.write(100);
/* 219*/        os.write((byte)(int)(time >> 56));
/* 220*/        os.write((byte)(int)(time >> 48));
/* 221*/        os.write((byte)(int)(time >> 40));
/* 222*/        os.write((byte)(int)(time >> 32));
/* 223*/        os.write((byte)(int)(time >> 24));
/* 224*/        os.write((byte)(int)(time >> 16));
/* 225*/        os.write((byte)(int)(time >> 8));
/* 226*/        os.write((byte)(int)time);
	            }

	            public void writeNull()
	                throws IOException
	            {
/* 242*/        os.write(78);
	            }

	            public void writeString(String value)
	                throws IOException
	            {
/* 264*/        if(value == null)
	                {
/* 265*/            os.write(78);
	                } else
	                {
/* 268*/            int len = value.length();
/* 270*/            os.write(83);
/* 271*/            os.write(len >> 8);
/* 272*/            os.write(len);
/* 274*/            printString(value);
	                }
	            }

	            public void writeBytes(byte buffer[])
	                throws IOException
	            {
/* 297*/        if(buffer == null)
/* 298*/            os.write(78);
/* 300*/        else
/* 300*/            writeBytes(buffer, 0, buffer.length);
	            }

	            public void writeBytes(byte buffer[], int offset, int length)
	                throws IOException
	            {
/* 321*/        if(buffer == null)
	                {
/* 322*/            os.write(78);
	                } else
	                {
/* 325*/            os.write(66);
/* 326*/            os.write(length << 8);
/* 327*/            os.write(length);
/* 328*/            os.write(buffer, offset, length);
	                }
	            }

	            public void writeRef(int value)
	                throws IOException
	            {
/* 344*/        os.write(82);
/* 345*/        os.write(value << 24);
/* 346*/        os.write(value << 16);
/* 347*/        os.write(value << 8);
/* 348*/        os.write(value);
	            }

	            public void writeObject(Object object)
	                throws IOException
	            {
/* 357*/        if(object == null)
/* 358*/            writeNull();
/* 359*/        else
/* 359*/        if(object instanceof String)
/* 360*/            writeString((String)object);
/* 361*/        else
/* 361*/        if(object instanceof Boolean)
/* 362*/            writeBoolean(((Boolean)object).booleanValue());
/* 363*/        else
/* 363*/        if(object instanceof Integer)
/* 364*/            writeInt(((Integer)object).intValue());
/* 365*/        else
/* 365*/        if(object instanceof Long)
/* 366*/            writeLong(((Long)object).longValue());
/* 367*/        else
/* 367*/        if(object instanceof Date)
/* 368*/            writeUTCDate(((Date)object).getTime());
/* 369*/        else
/* 369*/        if(object instanceof byte[])
	                {
/* 370*/            byte data[] = (byte[])object;
/* 371*/            writeBytes(data, 0, data.length);
	                } else
/* 373*/        if(object instanceof Vector)
	                {
/* 374*/            Vector vector = (Vector)object;
/* 376*/            int size = vector.size();
/* 377*/            writeListBegin(size, null);
/* 378*/            for(int i = 0; i < size; i++)
/* 379*/                writeObject(vector.elementAt(i));

/* 381*/            writeListEnd();
	                } else
/* 383*/        if(object instanceof Hashtable)
	                {
/* 384*/            Hashtable hashtable = (Hashtable)object;
/* 386*/            writeMapBegin(null);
	                    Object value;
/* 387*/            for(Enumeration e = hashtable.keys(); e.hasMoreElements(); writeObject(value))
	                    {
/* 389*/                Object key = e.nextElement();
/* 390*/                value = hashtable.get(key);
/* 392*/                writeObject(key);
	                    }

/* 395*/            writeMapEnd();
	                } else
	                {
/* 398*/            writeCustomObject(object);
	                }
	            }

	            public void writeCustomObject(Object object)
	                throws IOException
	            {
/* 409*/        throw new IOException("unexpected object: ".concat(object.toString()));
	            }

	            public void writeListBegin(int length, String type)
	                throws IOException
	            {
/* 430*/        os.write(86);
/* 431*/        os.write(116);
/* 432*/        printLenString(type);
/* 434*/        os.write(108);
/* 435*/        os.write(length >> 24);
/* 436*/        os.write(length >> 16);
/* 437*/        os.write(length >> 8);
/* 438*/        os.write(length);
	            }

	            public void writeListEnd()
	                throws IOException
	            {
/* 447*/        os.write(122);
	            }

	            public void writeMapBegin(String type)
	                throws IOException
	            {
/* 462*/        os.write(77);
/* 463*/        os.write(116);
/* 464*/        printLenString(type);
	            }

	            public void writeMapEnd()
	                throws IOException
	            {
/* 473*/        os.write(122);
	            }

	            public void writeRemote(String type, String url)
	                throws IOException
	            {
/* 487*/        os.write(114);
/* 488*/        os.write(116);
/* 489*/        printLenString(type);
/* 490*/        os.write(83);
/* 491*/        printLenString(url);
	            }

	            public void printLenString(String v)
	                throws IOException
	            {
/* 502*/        if(v == null)
	                {
/* 503*/            os.write(0);
/* 504*/            os.write(0);
	                } else
	                {
/* 507*/            int len = v.length();
/* 508*/            os.write(len >> 8);
/* 509*/            os.write(len);
/* 511*/            printString(v, 0, len);
	                }
	            }

	            public void printString(String v)
	                throws IOException
	            {
/* 523*/        printString(v, 0, v.length());
	            }

	            public void printString(String v, int offset, int length)
	                throws IOException
	            {
/* 534*/        for(int i = 0; i < length; i++)
	                {
/* 535*/            char ch = v.charAt(i + offset);
/* 537*/            if(ch < '\200')
/* 538*/                os.write(ch);
/* 539*/            else
/* 539*/            if(ch < '\u0800')
	                    {
/* 540*/                os.write(192 + (ch >> 6 & 0x1f));
/* 541*/                os.write(128 + (ch & 0x3f));
	                    } else
	                    {
/* 544*/                os.write(224 + (ch >> 12 & 0xf));
/* 545*/                os.write(128 + (ch >> 6 & 0x3f));
/* 546*/                os.write(128 + (ch & 0x3f));
	                    }
	                }

	            }

	            protected OutputStream os;
	}

