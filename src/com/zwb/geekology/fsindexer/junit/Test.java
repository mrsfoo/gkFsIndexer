package com.zwb.geekology.fsindexer.junit;

import junit.framework.TestCase;

import com.zwb.geekology.fsindexer.GkFilesystemIndexer;

public class Test extends TestCase
{
    public void testTest()
    {
	GkFilesystemIndexer i = new GkFilesystemIndexer();
	i.index();
    }
}
