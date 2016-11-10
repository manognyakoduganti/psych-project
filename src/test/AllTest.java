package test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import authentication.AdminAuthenticationServletTest;
import autoFill.FetchCommonFieldServletTest;
import fieldValidation.LocationFieldsTest;
import fieldValidation.UserProfileFieldsTest;
import location.LocationServletTest;
import profile.UserProfileServletTest;
import targetGroup.TargetGroupServletTest;
import training.TrainingServletTest;

@RunWith(Suite.class)
@SuiteClasses({
	AdminAuthenticationServletTest.class,
	UserProfileServletTest.class,
	UserProfileFieldsTest.class,
	FetchCommonFieldServletTest.class,
	LocationFieldsTest.class,
	LocationServletTest.class,
	TargetGroupServletTest.class,
	TrainingServletTest.class
})
public class AllTest {

}
