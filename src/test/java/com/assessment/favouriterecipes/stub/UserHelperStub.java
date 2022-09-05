package com.assessment.favouriterecipes.stub;

import com.assessment.favouriterecipes.util.UserHelper;

import static org.mockito.Mockito.when;

public class UserHelperStub {


    public static final String TEST_EMAIL = "test@test.com";

    public static void stubLoggedInUsernameValue(UserHelper userHelper) {

        when(userHelper.getLoggedInUsernameValue()).thenReturn(TEST_EMAIL);
    }


    private UserHelperStub() {

        throw new IllegalStateException("Utility class");
    }

}
