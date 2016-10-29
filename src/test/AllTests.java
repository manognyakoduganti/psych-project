package test;
import authentication.AdminAuthenticationTest;
import authentication.AuthenticatingUserTest;
import imageData.ImageDataServletTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import parameter.FetchInstructionTest;
import parameter.InitialParameterTest;
import questionnaire.QuestionnaireTest;
import registration.RegisterTest;

@RunWith(Suite.class)
@SuiteClasses({
	AdminAuthenticationTest.class,
})
public class AllTests {

}
