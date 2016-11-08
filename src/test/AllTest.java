package test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import authentication.AdminAuthenticationServletTest;
import fieldValidation.UserProfileFieldsTest;
import profile.UserProfileServletTest;

@RunWith(Suite.class)
@SuiteClasses({
	AdminAuthenticationServletTest.class,
	UserProfileServletTest.class,
	UserProfileFieldsTest.class
})
public class AllTest {

}
