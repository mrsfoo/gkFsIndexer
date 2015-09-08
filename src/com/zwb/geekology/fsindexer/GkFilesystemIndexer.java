package com.zwb.geekology.fsindexer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;

import com.zwb.fsparser.api.GkFsParserFactory;
import com.zwb.fsparser.api.IGkFsEntry;
import com.zwb.fsparser.api.IGkFsParser;
import com.zwb.fsparser.api.IGkFsParserError;
import com.zwb.fsparser.api.IGkFsParserResult;
import com.zwb.fsparser.api.IGkFsParserSearchLocation;
import com.zwb.geekology.fsindexer.util.Config;
import com.zwb.geekology.fsindexer.util.MyLogger;
import com.zwb.tab.Tab;

public class GkFilesystemIndexer
{
    private MyLogger log = new MyLogger(this.getClass());
    
    public void index()
    {
	List<IGkFsParserSearchLocation> sLocs = getAllSearchLocsToIndex();
	log.info("search locs:");
	for (IGkFsParserSearchLocation l : sLocs)
	{
	    log.info("* " + l.getLocationName() + ": " + l.getPath());
	}
	
	IGkFsParser parser = GkFsParserFactory.createParser();
	
	log.debug("parsing...");
	IGkFsParserResult result = parser.parseFolders(sLocs);
	log.debug("formatting...");
	putOut(result);
    }
    
    public void putOut(IGkFsParserResult result)
    {
	/** print results */
	List<IGkFsEntry> entries = result.getEntries();
	log.debug("sorting ("+entries.size()+") results...");
	Collections.sort(entries);
	Tab resultTab = new Tab("GkFileSystemIndexer Search Result", "FOLDERNAME", "PATH", "ARTIST", "RELEASE");
	for (IGkFsEntry e : entries)
	{
	    resultTab.addRow(e.getFilename(), doSubst(e.getPath()), e.getArtistName(), e.getReleaseName());
	}
	log.debug("\n" + resultTab.printFormatted());
	
	/** print errors */
	List<IGkFsParserError> errors = result.getErrors();
	log.debug("sorting errors...");
	Collections.sort(errors);
	Tab errorTab = new Tab("GkFileSystemIndexer Errors", "FOLDERNAME", "PATH", "REASON");
	for (IGkFsParserError e : errors)
	{
	    errorTab.addRow(e.getErrorFilename(), doSubst(e.getErrorPath()), e.getErrorReason());
	}
	log.debug("\n" + errorTab.printFormatted());
    }
    
    public List<IGkFsParserSearchLocation> getAllSearchLocsToIndex()
    {
	List<IGkFsParserSearchLocation> ret = new ArrayList<IGkFsParserSearchLocation>();
	
	List<String> depth1folders = Config.getListOfFlatFolderPathes();
	for (String s : depth1folders)
	{
	    ret.add(GkFsParserFactory.createSearchLocation(s + "[" + 1 + "]", s, 1));
	}
	
	List<String> depth2folders = Config.getListOfSubFolderPathes();
	for (String s : depth2folders)
	{
	    ret.add(GkFsParserFactory.createSearchLocation(s + "[" + 2 + "]", s, 2));
	}
	
	return ret;
    }
    
    public String doSubst(String s)
    {
	String ret = new String(s);
	for (Entry<String, String> e : Config.getSubst().entrySet())
	{
	    ret = ret.replaceAll(e.getKey(), e.getValue());
	}
	return ret;
    }
}
