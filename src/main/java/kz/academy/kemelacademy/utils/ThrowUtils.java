package kz.academy.kemelacademy.utils;

import kz.academy.kemelacademy.exceptions.ServiceException;
import kz.academy.kemelacademy.ui.enums.ErrorMessages;

/**
 * @author Omarbek.Dinassil
 * on 2020-08-01
 * @project kemelacademy
 */
public class ThrowUtils {
    
    public static void throwMissingRequiredFieldException(String[] fields) {
        for (String field: fields) {
            if (field == null || field.isEmpty()) {
                throw new ServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());
            }
        }
    }
    
    public static void throwMissingRequiredFieldException(Object[] fields) {
        for (Object field: fields) {
            if (field == null) {
                throw new ServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());
            }
            if (field instanceof String) {
                String val = (String) field;
                if (val.isEmpty()) {
                    throw new ServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());
                }
            }
        }
    }
    
}
