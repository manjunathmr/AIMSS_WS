package com.passionnambition.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class BaHashFn
{

    public String apply(
            byte[] data)
    {
        if (data == null)
            return null;

        MessageDigest md;

        try
        {
            md =
                    MessageDigest
                            .getInstance("MD5");
        }
        catch (NoSuchAlgorithmException e)
        {
            throw new RuntimeException(
                    e);
        }

        md
                .update(data);

        byte[] mdbytes =
                md
                        .digest();

        // convert the byte to hex format

        StringBuffer sb =
                new StringBuffer();

        for (int i =
                0; i < mdbytes.length; i++)
        {
            sb
                    .append(Integer
                            .toString(
                                    (mdbytes[i] & 0xff) + 0x100,
                                    16)
                            .substring(
                                    1));
        }

        return sb
                .toString();

    }

}
