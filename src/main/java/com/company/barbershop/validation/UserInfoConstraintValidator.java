package com.company.barbershop.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UserInfoConstraintValidator implements ConstraintValidator<UserInfo,String> {

    private String coursePrefix;

    @Override
    public void initialize(UserInfo courseCode) {
        coursePrefix = courseCode.value();
    }


    // first arg : String s -> data entered by the user in the html form
    // constraintValidatorContext -> we can place additional error messages here
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {

        // test if the form data starts with our course prefix
        boolean result;

        if (s != null){
            result = s.startsWith(coursePrefix);
        }else {
            result = true;
        }

        return result;
    }


}
