import authentication.AuthenticatingUserTest;
import imageData.ImageDataServletTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import parameter.FetchInstructionTest;
import parameter.InitialParameterTest;
import questionnaire.QuestionCategoryServletTest;
import questionnaire.QuestionServletTest;
import questionnaire.QuestionnaireTest;
import registration.RegisterTest;

@RunWith(Suite.class)
@SuiteClasses({
	AuthenticatingUserTest.class,
	ImageDataServletTest.class,
	FetchInstructionTest.class,
	InitialParameterTest.class,
	QuestionnaireTest.class,
	RegisterTest.class,
	QuestionCategoryServletTest.class,
	QuestionServletTest.class
})
public class AllTests {

}
