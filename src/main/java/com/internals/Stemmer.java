package com.internals;

import net.sf.extjwnl.JWNLException;
import net.sf.extjwnl.data.IndexWord;
import net.sf.extjwnl.data.POS;
import net.sf.extjwnl.dictionary.Dictionary;
import net.sf.extjwnl.dictionary.MorphologicalProcessor;

import java.util.HashMap;

public class Stemmer {

    private int MaxWordLength = 50;
    private Dictionary dic;
    private MorphologicalProcessor morph;
    private boolean IsInitialized;
    public HashMap AllWords = null;

    public Stemmer() throws JWNLException {
        dic = Dictionary.getDefaultResourceInstance();
        morph = dic.getMorphologicalProcessor();
        IsInitialized = true;
    }

    public String StemWordWithWordNet ( String word )
    {
        if(!IsInitialized) return null;
        if ( word == null ) return null;
        if ( morph == null ) morph = dic.getMorphologicalProcessor();

        IndexWord w;
        try
        {
            w = morph.lookupBaseForm( POS.VERB, word );
            if ( w != null )
                return w.getLemma();
            w = morph.lookupBaseForm( POS.NOUN, word );
            if ( w != null )
                return w.getLemma();
            w = morph.lookupBaseForm( POS.ADJECTIVE, word );
            if ( w != null )
                return w.getLemma();
            w = morph.lookupBaseForm( POS.ADVERB, word );
            if ( w != null )
                return w.getLemma();
        }
        catch ( JWNLException ignored)
        {
        }
        return null;
    }
}
