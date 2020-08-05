package com.test.mobdev.utils;

public final class ProductUtils {
    
    private ProductUtils() {
        super();
     
    }

    /**
     * Method to check if the parameter is numeric or is string
     * @param info posible Id para buscar
     * @return true or false
     */
    public static boolean isInteger(String info){
        try{
            Integer.parseInt(info);
            return true;
        }catch(NumberFormatException e){
            return false;
        }
    }

}
