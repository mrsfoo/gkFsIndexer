package com.zwb.geekology.fsindexer.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zwb.config.api.ConfigurationFactory;
import com.zwb.config.api.IConfiguration;

public class Config
{
    private static final String CONFIG_NAME = "filesystem.indexer";
    private static final String CONFIG_KEY_FLAT_FOLDERS = "pathes.flat";
    private static final String CONFIG_KEY_SUB_FOLDERS = "pathes.with_subdirs";
    private static final String CONFIG_KEY_SUBST = "pathes.subst";
    
    private static IConfiguration config = ConfigurationFactory.getBufferedConfiguration(CONFIG_NAME);
    
    public static List<String> getListOfFlatFolderPathes()
    {
	return config.getListOfStrings(CONFIG_KEY_FLAT_FOLDERS, new ArrayList<String>());
    }
    
    public static List<String> getListOfSubFolderPathes()
    {
	return config.getListOfStrings(CONFIG_KEY_SUB_FOLDERS, new ArrayList<String>());
    }
    
    public static Map<String, String> getSubst()
    {
	Map<String, String> m = new HashMap<String, String>();

	List<String> list = config.getListOfStrings(CONFIG_KEY_SUBST, new ArrayList<String>());
	if(list.isEmpty())
	{
	    return m;
	}
	for(int i=0; i<list.size(); i=i+2)
	{
	    if (i>=list.size())
	    {
		break;
	    }
	    m.put(list.get(i), list.get(i+1));
	}
	return m;
    }
}
