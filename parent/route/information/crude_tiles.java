    public void read_tiles(int i, int j, byte byte0, byte abyte0[], int k, int l)
    {
        try
        {
            Buffer buffer = new Buffer(abyte0);
            for(int i1 = 0; i1 < 4; i1++)
            {
                for(int j1 = 0; j1 < 64; j1++)
                {
                    for(int l1 = 0; l1 < 64; l1++)
                    {
                        int i2 = j1 + l;
                        int j2 = l1 + k;
                        if(i2 >= 0 && i2 < 104 && j2 >= 0 && j2 < 104)
                        {
                            aByteArrayArrayArray69[i1][i2][j2] = 0;
                            do
                            {
                                int k2 = buffer.getUnsignedByte();
                                if(k2 == 0)
                                {
                                    if(i1 == 0)
                                    {
                                        anIntArrayArrayArray68[0][i2][j2] = -method165(0xe3b7b + i2 + j, 0x87cce + j2 + i) * 8;
                                    } else
                                    {
                                        anIntArrayArrayArray68[i1][i2][j2] = anIntArrayArrayArray68[i1 - 1][i2][j2] - 240;
                                    }
                                    break;
                                }
                                if(k2 == 1)
                                {
                                    int i3 = buffer.getUnsignedByte();
                                    if(i3 == 1)
                                    {
                                        i3 = 0;
                                    }
                                    if(i1 == 0)
                                    {
                                        anIntArrayArrayArray68[0][i2][j2] = -i3 * 8;
                                    } else
                                    {
                                        anIntArrayArrayArray68[i1][i2][j2] = anIntArrayArrayArray68[i1 - 1][i2][j2] - i3 * 8;
                                    }
                                    break;
                                }
                                if(k2 <= 49)
                                {
                                    aByteArrayArrayArray71[i1][i2][j2] = buffer.getSignedByte();
                                    aByteArrayArrayArray72[i1][i2][j2] = (byte)((k2 - 2) / 4);
                                    aByteArrayArrayArray73[i1][i2][j2] = (byte)(k2 - 2 & 3);
                                } else
                                if(k2 <= 81)
                                {
                                    aByteArrayArrayArray69[i1][i2][j2] = (byte)(k2 - 49);
                                } else
                                {
                                    aByteArrayArrayArray70[i1][i2][j2] = (byte)(k2 - 81);
                                }
                            } while(true);
                        } else
                        {
                            do
                            {
                                int l2 = buffer.getUnsignedByte();
                                if(l2 == 0)
                                {
                                    break;
                                }
                                if(l2 == 1)
                                {
                                    buffer.getUnsignedByte();
                                    break;
                                }
                                if(l2 <= 49)
                                {
                                    buffer.getUnsignedByte();
                                }
                            } while(true);
                        }
                        
                    }

                }
            }
            if(byte0 != -54)
            {
                for(int k1 = 1; k1 > 0; k1++)
                {
                }
                return;
            }
        }
        catch(RuntimeException runtimeexception)
        {
            signlink.reporterror("95853, " + i + ", " + j + ", " + byte0 + ", " + abyte0 + ", " + k + ", " + l + ", " + runtimeexception.toString());
            throw new RuntimeException();
        }
    }
