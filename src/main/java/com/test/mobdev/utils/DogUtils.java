package com.test.mobdev.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class DogUtils {
    
    private DogUtils() {
        super();
     
    }

    /**
     * Method to check if the parameter is numeric
     * @param input value to verify
     * @return true or false
     */
    public static boolean onlyLetters(String info){
        Pattern letter = Pattern.compile("^[a-zA-Z\\s]*$");
        
        Matcher verify = letter.matcher(info);
        
        return verify.matches();
    }
    
    /**
     * Method looking for race specific information
     * Used when all races are obtained9
     * @param responseApi
     * @param breedName
     * @return
     */
    public static String[] getSubBreedArray (String responseApi, String breedName){

        String separatorBracketsOpen = Pattern.quote("[");
        String separatorBracketClose = Pattern.quote("]");
        String separatorComma = Pattern.quote(",");
        
        String[] firstSplitArray = responseApi.split(breedName);
        
        String breadNameSplit = firstSplitArray[1];
        
        String[] secondSplitArray = breadNameSplit.split(separatorBracketsOpen);
        
        String subBreedsListSplit = secondSplitArray[1];
       
        String[] thirdSplitArray = subBreedsListSplit.split(separatorBracketClose);
        
       return thirdSplitArray[0].split(separatorComma);
        
    }

}
